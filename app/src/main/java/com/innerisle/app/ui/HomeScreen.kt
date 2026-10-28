package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innerisle.app.data.AppData
import com.innerisle.app.data.DailyPrompts
import com.innerisle.app.data.GuideChapters
import com.innerisle.app.data.JournalEntry
import com.innerisle.app.data.SessionType
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun HomeScreen(
    data: AppData,
    settings: UiSettings,
    onOpenSettings: () -> Unit,
    onOpenCompanion: () -> Unit,
    onQuickSession: (SessionType, Int) -> Unit,
    onWriteJournal: () -> Unit,
    onOpenJournal: (String) -> Unit,
    onAddMember: () -> Unit,
    onLock: () -> Unit,
    onOpenGuide: () -> Unit,
    guideReadCount: Int
) {
    val tulpa = data.tulpas.firstOrNull()
    val today = LocalDate.now()
    val todayMinutes = data.minutesOn(today)
    val recentJournals = data.journals.sortedByDescending { it.createdAt }.take(2)
    val promptIndex = ((today.toEpochDay() % DailyPrompts.size + DailyPrompts.size) % DailyPrompts.size).toInt()
    val prompt = DailyPrompts[promptIndex]
    val hour = LocalTime.now().hour

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tr("心屿手记"),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = greetingForHour(hour),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = todayLabel(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (settings.appLockEnabled) {
                    IconButton(onClick = onLock) {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = tr("锁定"),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = tr("设置"),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            if (tulpa == null) {
                SoftCard(onClick = onAddMember) {
                    Text(text = tr("还没有 TA 的档案"), style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = tr("先创建一位 Tulpa，再开始记录你们的相处。"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = tr("创建档案 →"),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                HeroCard(
                    tulpaName = tulpa.name,
                    tulpaEmoji = tulpa.emoji,
                    todayMinutes = todayMinutes,
                    daysTogether = tulpa.daysTogether(),
                    onStart = onOpenCompanion
                )
            }
        }

        if (guideReadCount < GuideChapters.size) {
            item {
                SoftCard(
                    onClick = onOpenGuide,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
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
                                text = tr("从零开始"),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = L.t("已读 $guideReadCount / ${GuideChapters.size} 章 · 先读准备与安全，再开始练习", "Read $guideReadCount / ${GuideChapters.size} chapters · Start with preparation and safety, then practice"),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatTile(
                    value = "${tulpa?.daysTogether() ?: 0}",
                    label = tr("相伴天数"),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                StatTile(
                    value = formatMinutes(data.totalMinutes()),
                    label = tr("累计相处"),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    valueSize = 18.sp
                )
                StatTile(
                    value = "${data.journals.size}",
                    label = L.t("手记", "Journals"),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    title = tr("叙述 10 分钟"),
                    subtitle = tr("把今天讲给 TA"),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (tulpa != null) {
                            onQuickSession(SessionType.NARRATION, 10)
                        }
                    }
                )
                QuickActionCard(
                    icon = Icons.Outlined.Edit,
                    title = tr("写一篇手记"),
                    subtitle = tr("留住一个小细节"),
                    modifier = Modifier.weight(1f),
                    onClick = onWriteJournal
                )
            }
        }

        item {
            SoftCard(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✦", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = tr("今日一问"),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = prompt,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.height(12.dp))
                TextButton(
                    onClick = onWriteJournal,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(tr("写下来 →"))
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionTitle(
                    title = tr("最近的手记"),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (recentJournals.isEmpty()) {
            item {
                SoftCard {
                    Text(
                        text = tr("还没有手记。第一次不用写得完整，一句话也可以。"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(recentJournals.size) { index ->
                val entry = recentJournals[index]
                JournalPreview(
                    entry = entry,
                    authorName = data.memberById(entry.authorId)?.name ?: tr("未知"),
                    authorEmoji = data.memberById(entry.authorId)?.emoji ?: "🌙",
                    onClick = { onOpenJournal(entry.id) }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = tr("所有内容仅保存在本机"),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun HeroCard(
    tulpaName: String,
    tulpaEmoji: String,
    todayMinutes: Int,
    daysTogether: Int,
    onStart: () -> Unit
) {
    val reduceMotion = LocalReduceMotion.current
    val progress = (todayMinutes / 20f).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = if (reduceMotion) snap() else IsleMotion.spatialSlow(),
        label = "todayProgress"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.85f)
                    )
                )
            )
            .padding(22.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.65f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = tulpaEmoji, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = L.t("与 $tulpaName 的第 $daysTogether 天", "Day $daysTogether with $tulpaName"),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )
        }
        Spacer(Modifier.height(18.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tr("今天，想和 TA\n怎么相处？"),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (todayMinutes > 0) {
                        L.t("今天已经留出了 $todayMinutes 分钟。", "You've set aside $todayMinutes minutes today.")
                    } else {
                        tr("不用做很多，十分钟也算数。")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                )
            }
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(74.dp),
                    strokeWidth = 7.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
                )
                Text(
                    text = "$todayMinutes",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(Modifier.height(18.dp))
        Button(
            onClick = onStart,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(tr("开始相处"))
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    SoftCard(modifier = modifier, onClick = onClick, pressScale = true) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(12.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(3.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun JournalPreview(
    entry: JournalEntry,
    authorName: String,
    authorEmoji: String,
    onClick: () -> Unit
) {
    SoftCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = authorEmoji, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.width(6.dp))
            Text(
                text = authorName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = formatDate(entry.createdAt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            if (entry.mood.isNotBlank()) {
                Pill(text = entry.mood)
            }
        }
        if (entry.title.isNotBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = entry.body.ifBlank { tr("（没有正文）") },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
