package com.innerisle.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onComplete: (String, String, String) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }
    var emoji by rememberSaveable { mutableStateOf("🌙") }
    var color by rememberSaveable { mutableStateOf("#7E8CA0") }
    var accepted by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        AppMark(84.dp)
        Text(text = "心屿", style = MaterialTheme.typography.displaySmall)
        Text(
            text = "一个只属于你和 TA 的离线空间。\n把相处慢慢练成陪伴。",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SoftCard {
            FeatureRow("🌿", "慢慢相处", "用计时、对话和手记，把相处变成日常。")
            Spacer(Modifier.height(14.dp))
            FeatureRow("🔒", "只留在这里", "数据加密保存在本机；没有账号，没有云同步。")
            Spacer(Modifier.height(14.dp))
            FeatureRow("🫧", "不评判，不催促", "没有排行榜，也不会因为中断而扣分。")
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "先给 TA 一个名字",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("TA 的名字") },
                placeholder = { Text("之后可以改") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "选一个记号",
                style = MaterialTheme.typography.titleMedium
            )
            EmojiSelector(selected = emoji, onSelect = { emoji = it })
            ColorSelector(selected = color, onSelect = { color = it })
        }

        SoftCard(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)) {
            Text(
                text = "在进入之前",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Top) {
                Checkbox(checked = accepted, onCheckedChange = { accepted = it })
                Text(
                    text = "我已年满 18 岁，并理解心屿不是医疗或心理治疗工具，也不提供诊断或疗效承诺。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Text(
                text = "如果你正经历显著痛苦、功能受损，或听到无法控制的声音，请优先寻求专业帮助。",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
            )
        }

        Button(
            onClick = { onComplete(name.trim(), emoji, color) },
            enabled = name.isNotBlank() && accepted,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("进入心屿")
        }

        Text(
            text = "你的手记、对话与练习记录不会被上传。\n导出文件由你主动选择保存位置，请注意妥善保管。",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun FeatureRow(emoji: String, title: String, body: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = emoji, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
