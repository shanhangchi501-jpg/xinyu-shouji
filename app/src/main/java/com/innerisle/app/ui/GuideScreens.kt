package com.innerisle.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.innerisle.app.data.GuideChapters
import com.innerisle.app.data.GuideParts
import com.innerisle.app.data.SessionType

@Composable
fun GuideListScreen(
    readIds: Set<String>,
    onOpenChapter: (String) -> Unit,
    onBack: () -> Unit
) {
    val readCount = GuideChapters.count { it.id in readIds }
    val total = GuideChapters.size
    val progress = if (total == 0) 0f else readCount.toFloat() / total
    val nextChapter = GuideChapters.firstOrNull { it.id !in readIds } ?: GuideChapters.first()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { AppTopBar(title = "从零开始", onBack = onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(start = 20.dp, top = 8.dp, end = 20.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                SoftCard(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tulpa 完全创造指南",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "从准备到进阶，一步一步来",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "已读 $readCount / $total 章 · 不必赶进度，按自己的节奏",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { onOpenChapter(nextChapter.id) }) {
                        Text(if (readCount == 0) "开始阅读" else "继续阅读")
                    }
                }
            }

            item {
                Text(
                    text = "这份指南整理自社群实践资料，覆盖创造前的决定、基础训练、进阶练习与安全边界。你可以按顺序阅读，也可以直接跳到需要的那一章。",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            GuideParts.forEach { (part, subtitle) ->
                item {
                    SectionTitle(title = part, subtitle = subtitle)
                }
                items(
                    items = GuideChapters.filter { it.part == part },
                    key = { it.id }
                ) { chapter ->
                    val index = GuideChapters.indexOfFirst { it.id == chapter.id }
                    GuideChapterCard(
                        index = index,
                        chapter = chapter,
                        read = chapter.id in readIds,
                        onClick = { onOpenChapter(chapter.id) }
                    )
                }
            }

            item {
                SoftCard(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "阅读之前",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "这些内容来自社群经验与公开资料，不是医学结论，也不构成诊断或治疗建议。若练习让你持续痛苦、影响现实功能，或开始分不清现实与想象，请优先寻求专业帮助。",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

@Composable
private fun GuideChapterCard(
    index: Int,
    chapter: com.innerisle.app.data.GuideChapter,
    read: Boolean,
    onClick: () -> Unit
) {
    SoftCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (read) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (read) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = "已读",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "${index + 1}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = chapter.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${chapter.minutes} 分钟",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    chapter.practice?.let { practice ->
                        Text(
                            text = "可配合${practice.label}",
                            style = MaterialTheme.typography.labelLarge,
                            color = practice.tint()
                        )
                    }
                    if (chapter.safety) {
                        Pill("安全")
                    }
                }
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun GuideChapterScreen(
    chapterId: String,
    readIds: Set<String>,
    onToggleRead: (String) -> Unit,
    onOpenChapter: (String) -> Unit,
    onPractice: (SessionType) -> Unit,
    onBack: () -> Unit
) {
    val chapter = GuideChapters.firstOrNull { it.id == chapterId }
    val index = GuideChapters.indexOfFirst { it.id == chapterId }
    val listState = rememberLazyListState()

    LaunchedEffect(chapterId) {
        listState.scrollToItem(0)
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AppTopBar(
                title = chapter?.part ?: "指南",
                onBack = onBack
            )
        }
    ) { padding ->
        if (chapter == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                EmptyState(
                    emoji = "📘",
                    title = "没有找到这一章",
                    body = "它可能已经被移动或更新。",
                    actionLabel = "返回",
                    onAction = onBack
                )
            }
            return@Scaffold
        }

        val read = chapter.id in readIds
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(start = 22.dp, top = 8.dp, end = 22.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Pill(chapter.part)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "第 ${index + 1} / ${GuideChapters.size} 章 · ${chapter.minutes} 分钟",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.weight(1f))
                    if (read) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = "已读",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            item {
                Text(
                    text = chapter.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (chapter.safety) {
                item {
                    SoftCard(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.55f)
                    ) {
                        Text(
                            text = "安全提示",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "这一章涉及进阶或风险内容。请先确保状态稳定，约定停机词，并在出现不适时立即暂停。任何练习都不应以现实功能为代价。",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            chapter.sections.forEach { section ->
                item {
                    Column {
                        if (section.heading != null) {
                            Row(
                                modifier = Modifier.padding(top = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = section.heading,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                if (section.communityView) {
                                    Pill("社区经验")
                                }
                                if (section.researchView) {
                                    Pill("研究视角")
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                        section.paragraphs.forEach { paragraph ->
                            Text(
                                text = paragraph,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(10.dp))
                        }
                    }
                }
            }

            if (chapter.checklist.isNotEmpty()) {
                item {
                    SoftCard(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "本章自检",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(Modifier.height(8.dp))
                        chapter.checklist.forEach { line ->
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Outlined.RadioButtonUnchecked,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier
                                        .padding(top = 2.dp)
                                        .size(16.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = line,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f)
                                )
                            }
                            Spacer(Modifier.height(6.dp))
                        }
                    }
                }
            }

            chapter.practice?.let { practice ->
                item {
                    SoftCard(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.55f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = practice.icon(),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "把这一章放进一次相处",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "建议尝试：${practice.label}。不用追求时长，五分钟也算数。",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { onPractice(practice) }) {
                            Text("去相处")
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { onToggleRead(chapter.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (read) Icons.Outlined.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (read) "已完成 · 点此取消" else "标记为已读")
                }
            }

            item {
                val previous = GuideChapters.getOrNull(index - 1)
                val next = GuideChapters.getOrNull(index + 1)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (previous != null) {
                        OutlinedButton(
                            onClick = { onOpenChapter(previous.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("上一章")
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                    if (next != null) {
                        Button(
                            onClick = { onOpenChapter(next.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("下一章")
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            item {
                Text(
                    text = "本指南依据《Tulpa 完全创造指南》基础篇 / 实践篇 / 提高篇与《图帕（Tulpa）》整理，并结合了心屿手记的安全边界说明。它是社群经验，不构成医疗建议，也不能替代专业心理支持。",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
