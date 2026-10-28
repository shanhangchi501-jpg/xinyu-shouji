package com.innerisle.app.ui

import com.innerisle.app.data.tr

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.innerisle.app.data.AppData
import com.innerisle.app.data.DailyPrompts
import com.innerisle.app.data.JournalEntry
import com.innerisle.app.ui.theme.LocalReduceMotion
import java.util.UUID

@Composable
fun JournalScreen(
    data: AppData,
    onAdd: () -> Unit,
    onOpen: (String) -> Unit
) {
    val reduceMotion = LocalReduceMotion.current
    var selectedAuthor by rememberSaveable { mutableStateOf("__all__") }
    val entries = data.journalsFor(
        if (selectedAuthor == "__all__") null else selectedAuthor
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionTitle(
                    title = tr("手记"),
                    subtitle = tr("写下你们之间的细小变化。"),
                    modifier = Modifier.weight(1f)
                )
                FilledTonalButton(onClick = onAdd) {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text(tr("写一篇"))
                }
            }
        }

        item {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AuthorChip(
                    label = tr("全部"),
                    selected = selectedAuthor == "__all__",
                    onClick = { selectedAuthor = "__all__" }
                )
                data.members.forEach { member ->
                    AuthorChip(
                        label = "${member.emoji} ${member.name}",
                        selected = selectedAuthor == member.id,
                        onClick = { selectedAuthor = member.id }
                    )
                }
            }
        }

        if (entries.isEmpty()) {
            item {
                EmptyState(
                    emoji = "📖",
                    title = tr("还没有手记"),
                    body = tr("不用等到想清楚了才写。一句话、一个瞬间，都可以留在这里。"),
                    actionLabel = tr("写第一篇"),
                    onAction = onAdd
                )
            }
        } else {
            items(entries, key = { it.id }) { entry ->
                Box(if (reduceMotion) Modifier else Modifier.animateItem()) {
                    JournalCard(
                        entry = entry,
                        author = data.memberById(entry.authorId),
                        onClick = { onOpen(entry.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthorChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun JournalCard(
    entry: JournalEntry,
    author: com.innerisle.app.data.Member?,
    onClick: () -> Unit
) {
    SoftCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = author?.emoji ?: "🌙",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = author?.name ?: tr("未知"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = formatFullDate(entry.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            if (entry.mood.isNotBlank()) {
                Pill(entry.mood)
            }
        }
        if (entry.title.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = entry.body.ifBlank { tr("（没有正文）") },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
        if (entry.tags.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                entry.tags.take(4).forEach { tag ->
                    Pill(text = "#$tag")
                }
            }
        }
    }
}

@Composable
fun JournalEditScreen(
    data: AppData,
    entryId: String?,
    onBack: () -> Unit,
    onSave: (JournalEntry) -> Unit,
    onDelete: (String) -> Unit
) {
    val existing = data.journals.firstOrNull { it.id == entryId }
    var authorId by rememberSaveable {
        mutableStateOf(existing?.authorId ?: data.host?.id.orEmpty())
    }
    var title by rememberSaveable { mutableStateOf(existing?.title.orEmpty()) }
    var body by rememberSaveable { mutableStateOf(existing?.body.orEmpty()) }
    var mood by rememberSaveable { mutableStateOf(existing?.mood.orEmpty()) }
    var tags by rememberSaveable { mutableStateOf(existing?.tags?.joinToString("，").orEmpty()) }
    var promptIndex by rememberSaveable {
        mutableIntStateOf(
            ((System.currentTimeMillis() / 86_400_000L) % DailyPrompts.size).toInt()
        )
    }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = if (existing == null) tr("写手记") else tr("编辑手记"),
                onBack = onBack,
                actions = {
                    if (existing != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Outlined.Delete, contentDescription = tr("删除"))
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(text = tr("这是谁的手记"), style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                data.members.forEach { member ->
                    AuthorChip(
                        label = "${member.emoji} ${member.name}",
                        selected = authorId == member.id,
                        onClick = { authorId = member.id }
                    )
                }
            }

            if (existing == null) {
                SoftCard(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = tr("不知道写什么？"),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = DailyPrompts[promptIndex],
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    TextButton(
                        onClick = {
                            promptIndex = (promptIndex + 1) % DailyPrompts.size
                        }
                    ) {
                        Text(tr("换一个"))
                    }
                }
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(tr("标题（可选）")) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text(tr("想记住什么")) },
                minLines = 7,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = tr("此刻的心情"), style = MaterialTheme.typography.titleMedium)
            MoodSelector(selected = mood, onSelect = { mood = it })

            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text(tr("标签（用逗号分隔）")) },
                placeholder = { Text(tr("日常，第一次对话，幻境")) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onSave(
                        JournalEntry(
                            id = existing?.id ?: UUID.randomUUID().toString(),
                            authorId = authorId,
                            title = title.trim(),
                            body = body.trim(),
                            mood = mood,
                            tags = tags.split(",", "，")
                                .map { it.trim() }
                                .filter { it.isNotBlank() },
                            createdAt = existing?.createdAt ?: System.currentTimeMillis(),
                            updatedAt = System.currentTimeMillis()
                        )
                    )
                },
                enabled = authorId.isNotBlank() && (title.isNotBlank() || body.isNotBlank()),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(tr("保存"))
            }
            Spacer(Modifier.height(24.dp))
        }
    }

    if (showDeleteDialog && existing != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(tr("删除这篇手记？")) },
            text = { Text(tr("删除后无法恢复。")) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(existing.id)
                    }
                ) {
                    Text(tr("删除"), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(tr("取消"))
                }
            }
        )
    }
}
