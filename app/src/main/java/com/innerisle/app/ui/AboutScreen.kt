package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.innerisle.app.BuildConfig

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { AppTopBar(title = tr("关于心屿手记"), onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppMark(76.dp)
            Text(text = tr("心屿手记"), style = MaterialTheme.typography.headlineMedium)
            Text(
                text = L.t("版本 ${BuildConfig.VERSION_NAME}", "Version ${BuildConfig.VERSION_NAME}"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            AboutSection(
                title = tr("我们想做的"),
                body = tr("心屿手记是一个本地优先的 Tulpa 相处工具。它不催促你打卡，也不把你和别人的进度放在一起比较。\n\n") +
                    tr("你可以在里面记录相处、写下对话、保存那些只有你们懂的细节。关系是慢慢长出来的。")
            )

            AboutSection(
                title = tr("隐私"),
                body = tr("所有档案、相处记录和手记都通过 Android Keystore 派生的密钥加密，保存在本机。\n\n") +
                    tr("没有账号，没有云同步，没有分析统计，也没有广告。导出文件由你主动选择保存位置，导出内容未加密，请妥善保管。")
            )

            SoftCard(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
            ) {
                Text(
                    text = tr("安全边界"),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = tr("Tulpa 是一种主观心理实践。心屿手记不提供医疗建议、诊断或疗效承诺，也不能替代专业心理支持或治疗。\n\n") +
                        tr("如果你正经历显著痛苦、功能受损，或听到无法控制的声音，请优先联系当地专业心理服务或紧急服务。"),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            AboutSection(
                title = tr("你的数据"),
                body = tr("你可以随时在设置中导出 JSON 备份，或永久删除全部数据。\n\n") +
                    tr("应用锁只保护界面；真正的数据保护来自 Android 系统沙盒与 Keystore 加密。请同时使用安全的设备锁屏。")
            )

            AboutSection(
                title = tr("致谢"),
                body = tr("这个项目受到 Tulpa.info、tulpa.guide、Tulpa之家、PluralityCN 等公开社区资料的启发。\n\n") +
                    tr("谢谢每一个认真对待内部关系的人。")
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = tr("心屿手记 · 本地优先 · 无账号 · 无云同步"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun AboutSection(title: String, body: String) {
    SoftCard(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
