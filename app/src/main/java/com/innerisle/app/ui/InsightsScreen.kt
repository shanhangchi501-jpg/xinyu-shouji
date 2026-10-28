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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innerisle.app.data.Achievement
import com.innerisle.app.data.AppData
import com.innerisle.app.data.SessionType
import com.innerisle.app.data.computeAchievements
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.max

@Composable
fun InsightsScreen(
    data: AppData,
    animateBars: Boolean = true,
    onBarsAnimationPlayed: () -> Unit = {}
) {
    val reduceMotion = LocalReduceMotion.current
    var barsVisible by remember { mutableStateOf(!animateBars) }
    LaunchedEffect(Unit) {
        if (animateBars) {
            barsVisible = true
            onBarsAnimationPlayed()
        }
    }
    val last7 = (0..6).map { offset ->
        LocalDate.now().minusDays((6 - offset).toLong())
    }
    val values = last7.map { data.minutesOn(it) }
    val maxValue = max(1, values.maxOrNull() ?: 0)
    val achievements = computeAchievements(data)
    val typeCounts = SessionType.entries.map { type ->
        type to data.sessions.count { it.type == type }
    }.filter { it.second > 0 }
    val totalSessions = data.sessions.size

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            SectionTitle(
                title = tr("年轮"),
                subtitle = tr("看见自己的节奏，而不是别人的进度。")
            )
        }

        item {
            SoftCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatTile(
                        value = "${data.daysTogether()}",
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
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatTile(
                        value = "$totalSessions",
                        label = tr("相处次数"),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    StatTile(
                        value = "${data.journals.size}",
                        label = L.t("手记篇数", "Journals"),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }

        item {
            SoftCard {
                Text(
                    text = tr("最近七天"),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    last7.forEachIndexed { index, date ->
                        val minutes = values[index]
                        val ratio = minutes.toFloat() / maxValue.toFloat()
                        val animatedRatio by animateFloatAsState(
                            targetValue = if (barsVisible) ratio else 0f,
                            animationSpec = if (reduceMotion) {
                                snap()
                            } else {
                                tween(
                                    durationMillis = 520,
                                    delayMillis = index * 35,
                                    easing = IsleMotion.emphasizedDecelerate
                                )
                            },
                            label = "weekBar"
                        )
                        val barHeight = max(3f, 86f * animatedRatio)
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (minutes > 0) "$minutes" else "",
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(barHeight.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (minutes > 0) {
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
                                        } else {
                                            MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    )
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = date.format(
                                    DateTimeFormatter.ofPattern("EE", if (L.isEn) Locale.ENGLISH else Locale.CHINA)
                                ),
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        item {
            SectionTitle(title = tr("练习分布"))
        }

        item {
            SoftCard {
                if (typeCounts.isEmpty()) {
                    Text(
                        text = tr("完成第一次相处后，这里会出现你的练习分布。"),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    typeCounts.forEachIndexed { index, (type, count) ->
                        if (index > 0) Spacer(Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = type.label,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.width(44.dp)
                            )
                            LinearProgressIndicator(
                                progress = { count.toFloat() / totalSessions.toFloat() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(7.dp),
                                color = type.tint(),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                strokeCap = StrokeCap.Round
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = L.t("$count 次", "$count times"),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        item {
            SectionTitle(
                title = tr("成长印记"),
                subtitle = tr("不是任务清单，只是你们走过的一些节点。")
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                achievements.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        row.forEach { achievement ->
                            AchievementCard(
                                achievement = achievement,
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
            Text(
                text = tr("年轮只记录你愿意留下的部分。\n中断不会扣分，回来就好。"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    modifier: Modifier = Modifier
) {
    val unlocked = achievement.unlocked
    SoftCard(
        modifier = modifier,
        containerColor = if (unlocked) {
            MaterialTheme.colorScheme.tertiaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (unlocked) Icons.Outlined.Star else Icons.Outlined.Lock,
                contentDescription = null,
                tint = if (unlocked) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                },
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.titleMedium,
                color = if (unlocked) {
                    MaterialTheme.colorScheme.onTertiaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)
                }
            )
        }
        Spacer(Modifier.height(5.dp))
        Text(
            text = achievement.description,
            style = MaterialTheme.typography.bodyMedium,
            color = if (unlocked) {
                MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            },
            minLines = 3,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
