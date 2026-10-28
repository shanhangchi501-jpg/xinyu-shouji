package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Forest
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.innerisle.app.data.MoodOptions
import com.innerisle.app.data.SessionType
import com.innerisle.app.ui.theme.pressScale
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Context.findActivity(): Activity? {
    var current: Context = this
    while (current is ContextWrapper) {
        if (current is Activity) return current
        current = current.baseContext
    }
    return null
}

fun formatDate(millis: Long, pattern: String? = null): String {
    val resolved = pattern ?: if (L.isEn) "MMM d" else "M月d日"
    val locale = if (L.isEn) Locale.ENGLISH else Locale.CHINA
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(resolved, locale))
}

fun formatFullDate(millis: Long): String =
    formatDate(millis, if (L.isEn) "yyyy-MM-dd HH:mm" else "yyyy年M月d日 HH:mm")

fun formatMinutes(minutes: Int): String =
    if (minutes >= 60) {
        val hours = minutes / 60
        val rest = minutes % 60
        if (rest == 0) L.t("$hours 小时", "$hours hr") else L.t("$hours 小时 $rest 分", "$hours hr $rest min")
    } else {
        L.t("$minutes 分钟", "$minutes min")
    }

fun formatReminderTime(hour: Int, minute: Int): String =
    String.format(Locale.US, "%02d:%02d", hour, minute)

fun greetingForHour(hour: Int): String = when (hour) {
    in 5..10 -> tr("早上好")
    in 11..13 -> tr("中午好")
    in 14..17 -> tr("下午好")
    in 18..22 -> tr("晚上好")
    else -> tr("夜深了")
}

fun todayLabel(): String = LocalDate.now().format(
    DateTimeFormatter.ofPattern(
        if (L.isEn) "MMM d, EEEE" else "M月d日 EEEE",
        if (L.isEn) Locale.ENGLISH else Locale.CHINA
    )
)

fun String.asColor(fallback: Color = Color(0xFF8A9A7B)): Color =
    runCatching { Color(android.graphics.Color.parseColor(this)) }.getOrDefault(fallback)

fun SessionType.icon(): ImageVector = when (this) {
    SessionType.NARRATION -> Icons.Outlined.ChatBubbleOutline
    SessionType.CONVERSATION -> Icons.Outlined.RecordVoiceOver
    SessionType.VISUALIZATION -> Icons.Outlined.Visibility
    SessionType.WONDERLAND -> Icons.Outlined.Forest
    SessionType.IMPOSITION -> Icons.Outlined.Air
    SessionType.MINDFULNESS -> Icons.Outlined.SelfImprovement
}

fun SessionType.tint(): Color = when (this) {
    SessionType.NARRATION -> Color(0xFF6C7A9C)
    SessionType.CONVERSATION -> Color(0xFFA7805C)
    SessionType.VISUALIZATION -> Color(0xFF7E8CA0)
    SessionType.WONDERLAND -> Color(0xFF6F8A7A)
    SessionType.IMPOSITION -> Color(0xFF9A7E9E)
    SessionType.MINDFULNESS -> Color(0xFF8A9A7B)
}

@Composable
fun WarmBackground(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colorScheme.onBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.22f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            content()
        }
    }
}

@Composable
fun AppMark(size: Dp = 64.dp, showText: Boolean = true) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size * 0.64f)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f), CircleShape)
        )
        if (showText) {
            Text(
                text = "屿",
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                fontSize = (size.value * 0.26f).sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SoftCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    pressScale: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = MaterialTheme.shapes.large
    // Pre-blend translucent card colours with the page background.
    // A translucent container lets the card's own elevation shadow show through,
    // which paints a darker rectangle inside the padding on some devices.
    val opaqueContainer = if (containerColor.alpha < 1f) {
        containerColor.compositeOver(MaterialTheme.colorScheme.background)
    } else {
        containerColor
    }
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = modifier
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = LocalIndication.current,
                            onClick = onClick
                        )
                        .then(
                            if (pressScale) {
                                Modifier.pressScale(interactionSource)
                            } else {
                                Modifier
                            }
                        )
                } else {
                    Modifier
                }
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = opaqueContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            content = content
        )
    }
}

@Composable
fun SectionTitle(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (subtitle != null) {
            Spacer(Modifier.height(3.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun MoodSelector(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MoodOptions.forEach { mood ->
            FilterChip(
                selected = selected == mood.label,
                onClick = { onSelect(mood.label) },
                label = { Text("${mood.emoji} ${mood.label}") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Composable
fun EmojiSelector(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val emojis = listOf("🌙", "✨", "🌿", "🫧", "🌊", "🕯️", "🪶", "🌻", "🌧️", "🦋", "☕", "🔥")
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        emojis.forEach { emoji ->
            val isSelected = selected == emoji
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        }
                    )
                    .border(
                        width = if (isSelected) 1.5.dp else 0.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onSelect(emoji) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 22.sp)
            }
        }
    }
}

@Composable
fun ColorSelector(
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        "#7E8CA0", "#A7805C", "#6F8A7A", "#9A7E9E",
        "#B26D6D", "#5F8F8A", "#8A9A7B", "#B08A5A"
    )
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        colors.forEach { hex ->
            val isSelected = selected == hex
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(hex.asColor())
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onSelect(hex) }
            )
        }
    }
}

@Composable
fun StatTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueSize: TextUnit = 24.sp
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontSize = valueSize,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Pill(text: String, color: Color? = null) {
    Surface(
        shape = CircleShape,
        color = color ?: MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EmptyState(
    emoji: String,
    title: String,
    body: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 42.sp)
        Spacer(Modifier.height(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(16.dp))
            androidx.compose.material3.Button(onClick = onAction) {
                Text(actionLabel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = tr("返回"))
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}
