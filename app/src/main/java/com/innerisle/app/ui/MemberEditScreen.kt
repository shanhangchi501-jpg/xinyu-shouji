package com.innerisle.app.ui

import com.innerisle.app.data.tr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.innerisle.app.data.AppData
import com.innerisle.app.data.Member
import com.innerisle.app.data.MemberRole

@Composable
fun MemberEditScreen(
    data: AppData,
    memberId: String?,
    onBack: () -> Unit,
    onSave: (Member) -> Unit,
    onDelete: (String) -> Unit
) {
    val existing = data.members.firstOrNull { it.id == memberId }
    val isHost = existing?.role == MemberRole.HOST
    var name by rememberSaveable { mutableStateOf(existing?.name.orEmpty()) }
    var emoji by rememberSaveable { mutableStateOf(existing?.emoji ?: "🌙") }
    var color by rememberSaveable { mutableStateOf(existing?.color ?: "#7E8CA0") }
    var form by rememberSaveable { mutableStateOf(existing?.form.orEmpty()) }
    var personality by rememberSaveable { mutableStateOf(existing?.personality.orEmpty()) }
    var voice by rememberSaveable { mutableStateOf(existing?.voice.orEmpty()) }
    var likes by rememberSaveable { mutableStateOf(existing?.likes.orEmpty()) }
    var notes by rememberSaveable { mutableStateOf(existing?.notes.orEmpty()) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = when {
                    existing == null -> tr("新建档案")
                    isHost -> tr("我的档案")
                    else -> tr("编辑档案")
                },
                onBack = onBack,
                actions = {
                    if (existing != null && !isHost) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Outlined.Delete, contentDescription = tr("删除档案"))
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
            SoftCard(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
            ) {
                Text(
                    text = if (isHost) {
                        tr("这是你的档案。名字和记号会出现在手记与首页里。")
                    } else {
                        tr("TA 会随着相处慢慢变化，不需要一次设定完整。留下的空白也可以是一种邀请。")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(tr("名字")) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = tr("记号"), style = MaterialTheme.typography.titleMedium)
            EmojiSelector(selected = emoji, onSelect = { emoji = it })
            ColorSelector(selected = color, onSelect = { color = it })

            OutlinedTextField(
                value = form,
                onValueChange = { form = it },
                label = { Text(tr("形象")) },
                placeholder = { Text(tr("TA 看起来是什么样？也可以留空，让 TA 自己决定。")) },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = personality,
                onValueChange = { personality = it },
                label = { Text(tr("性格与价值观")) },
                placeholder = { Text(tr("温柔、直接、爱吐槽……或者只写一两个词。")) },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = voice,
                onValueChange = { voice = it },
                label = { Text(tr("声音与说话方式")) },
                placeholder = { Text(tr("语速、用词、口头禅，或者“还没确定”。")) },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = likes,
                onValueChange = { likes = it },
                label = { Text(tr("喜欢的事物")) },
                placeholder = { Text(tr("音乐、天气、食物、某个小动作……")) },
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text(tr("备注")) },
                placeholder = { Text(tr("任何想留给以后看的细节。")) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val base = existing ?: Member(role = MemberRole.TULPA)
                    onSave(
                        base.copy(
                            name = name.trim(),
                            emoji = emoji,
                            color = color,
                            form = form.trim(),
                            personality = personality.trim(),
                            voice = voice.trim(),
                            likes = likes.trim(),
                            notes = notes.trim()
                        )
                    )
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(tr("保存档案"))
            }
            Spacer(Modifier.height(24.dp))
        }
    }

    if (showDeleteDialog && existing != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(tr("删除这个档案？")) },
            text = {
                Text(
                    tr("档案会被删除。已有的相处记录和手记会保留，但会显示为未知成员。")
                )
            },
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
