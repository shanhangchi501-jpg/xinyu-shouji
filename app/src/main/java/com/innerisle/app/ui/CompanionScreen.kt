package com.innerisle.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innerisle.app.data.AppData
import com.innerisle.app.data.SessionType

@Composable
fun CompanionScreen(
    data: AppData,
    onStart: (SessionType, String, Int) -> Unit,
    onAddMember: () -> Unit
) {
    val tulpas = data.tulpas
    var selectedTulpaId by rememberSaveable {
        mutableStateOf(tulpas.firstOrNull()?.id.orEmpty())
    }
    var selectedTypeName by rememberSaveable {
        mutableStateOf(SessionType.NARRATION.name)
    }
    var selectedMinutes by rememberSaveable { mutableIntStateOf(15) }

    val selectedTulpa = tulpas.firstOrNull { it.id == selectedTulpaId } ?: tulpas.firstOrNull()
    val selectedType = runCatching { SessionType.valueOf(selectedTypeName) }
        .getOrDefault(SessionType.NARRATION)
    val recent = selectedTulpa?.let { data.sessionsFor(it.id).take(3) }.orEmpty()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SectionTitle(
                title = "相处",
                subtitle = "不追求时长，只在意在场。计时只是容器，走神也没关系。"
            )
        }

        if (selectedTulpa == null) {
            item {
                EmptyState(
                    emoji = "🌙",
                    title = "还没有可以相处的 TA",
                    body = "先创建一位 Tulpa 的档案，所有练习与手记都会和 TA 关联。",
                    actionLabel = "创建档案",
                    onAction = onAddMember
                )
            }
            return@LazyColumn
        }

        if (tulpas.size > 1) {
            item {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tulpas.forEach { tulpa ->
                        FilterChip(
                            selected = selectedTulpa.id == tulpa.id,
                            onClick = { selectedTulpaId = tulpa.id },
                            label = { Text("${tulpa.emoji} ${tulpa.name}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        }

        item {
            SectionTitle(title = "今天想做什么")
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                SessionType.entries.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { type ->
                            TechniqueCard(
                                type = type,
                                selected = type == selectedType,
                                onClick = { selectedTypeName = type.name },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                        if (row.size == 1) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        item {
            SoftCard {
                Text(
                    text = "这次留多久",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(5, 10, 15, 20, 30, 45).forEach { minutes ->
                        FilterChip(
                            selected = selectedMinutes == minutes,
                            onClick = { selectedMinutes = minutes },
                            label = { Text("$minutes 分") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { onStart(selectedType, selectedTulpa.id, selectedMinutes) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("开始这次相处")
                }
            }
        }

        item {
            SectionTitle(
                title = "最近的相处",
                subtitle = "只和自己比较，不追赶任何人。"
            )
        }

        if (recent.isEmpty()) {
            item {
                SoftCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(SessionType.NARRATION.tint().copy(alpha = 0.14f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                SessionType.NARRATION.icon(),
                                contentDescription = null,
                                tint = SessionType.NARRATION.tint()
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "还没有记录",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "第一次可以从五分钟开始。",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    TextButton(
                        onClick = {
                            onStart(SessionType.NARRATION, selectedTulpa.id, 5)
                        }
                    ) {
                        Text("先相处五分钟 →")
                    }
                }
            }
        } else {
            recent.forEach { session ->
                item {
                    SoftCard {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(session.type.tint().copy(alpha = 0.14f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    session.type.icon(),
                                    contentDescription = null,
                                    tint = session.type.tint()
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = session.type.label,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = formatFullDate(session.startedAt),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${session.minutes} 分钟",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                if (session.mood.isNotBlank()) {
                                    Text(
                                        text = session.mood,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        if (session.note.isNotBlank()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = session.note,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TechniqueCard(
    type: SessionType,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = MaterialTheme.shapes.large
    Column(
        modifier = modifier
            .clip(shape)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)
                },
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(15.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                type.icon(),
                contentDescription = null,
                tint = type.tint(),
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.weight(1f))
            if (selected) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = type.label,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 15.sp,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = type.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.72f)
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
