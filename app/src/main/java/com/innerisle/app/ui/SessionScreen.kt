package com.innerisle.app.ui

import com.innerisle.app.data.L

import com.innerisle.app.data.tr

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.innerisle.app.data.AppData
import com.innerisle.app.data.SessionType
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion
import com.innerisle.app.work.ReminderScheduler
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.max

@Composable
fun SessionScreen(
    data: AppData,
    tulpaId: String,
    type: SessionType,
    plannedMinutes: Int,
    onBack: () -> Unit,
    onSave: (startedAt: Long, minutes: Int, mood: String, note: String) -> Unit
) {
    val tulpa = data.memberById(tulpaId)
    val context = LocalContext.current
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()
    val reduceMotion = LocalReduceMotion.current
    val safeMinutes = plannedMinutes.coerceAtLeast(1)
    val totalSeconds = safeMinutes * 60

    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        val controller = window?.let { WindowCompat.getInsetsController(it, view) }
        controller?.isAppearanceLightStatusBars = false
        controller?.isAppearanceLightNavigationBars = false
        onDispose {
            controller?.isAppearanceLightStatusBars = !darkTheme
            controller?.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    var startedAt by rememberSaveable { mutableLongStateOf(System.currentTimeMillis()) }
    var remaining by rememberSaveable { mutableIntStateOf(totalSeconds) }
    var running by rememberSaveable { mutableStateOf(true) }
    var endAt by rememberSaveable {
        mutableLongStateOf(System.currentTimeMillis() + totalSeconds * 1000L)
    }
    var finished by rememberSaveable { mutableStateOf(false) }
    var showExitDialog by rememberSaveable { mutableStateOf(false) }
    var mood by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    DisposableEffect(Unit) {
        ReminderScheduler.scheduleSessionEnd(context, safeMinutes)
        onDispose {
            ReminderScheduler.cancelSessionEnd(context)
        }
    }

    LaunchedEffect(running, endAt) {
        if (running && !finished) {
            while (true) {
                val left = ((endAt - System.currentTimeMillis()) / 1000L)
                    .toInt()
                    .coerceAtLeast(0)
                remaining = left
                if (left <= 0) {
                    running = false
                    finished = true
                    ReminderScheduler.cancelSessionEnd(context)
                    break
                }
                delay(200)
            }
        }
    }

    BackHandler {
        if (finished) {
            onBack()
        } else {
            showExitDialog = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = !finished,
            exit = if (reduceMotion) {
                ExitTransition.None
            } else {
                fadeOut(tween(180, easing = IsleMotion.emphasizedAccelerate))
            }
        ) {
    ImmersiveBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 22.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showExitDialog = true }) {
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = tr("结束"),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                }
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${tulpa?.emoji ?: "🌙"} ${tulpa?.name ?: "TA"}",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Text(
                        text = type.label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.55f)
                    )
                }
            }

            Spacer(Modifier.weight(0.7f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                BreathingCircle()
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formatClock(remaining),
                        fontSize = 58.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                    Text(
                        text = if (running) tr("在场") else tr("已暂停"),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.55f)
                    )
                }
            }

            Spacer(Modifier.weight(0.7f))

            val elapsed = totalSeconds - remaining
            val guidanceIndex = ((elapsed / 40) % type.guidance.size + type.guidance.size) %
                type.guidance.size
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .background(Color.White.copy(alpha = 0.07f))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.12f),
                        MaterialTheme.shapes.large
                    )
                    .padding(18.dp)
            ) {
                AnimatedContent(
                    targetState = guidanceIndex,
                    transitionSpec = {
                        if (reduceMotion) {
                            EnterTransition.None togetherWith ExitTransition.None
                        } else {
                            fadeIn(tween(400, easing = IsleMotion.emphasizedDecelerate))
                                .togetherWith(
                                    fadeOut(tween(200, easing = IsleMotion.emphasizedAccelerate))
                                )
                        }
                    },
                    label = "guidance"
                ) { index ->
                    Text(
                        text = type.guidance[index],
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.82f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { showExitDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.12f),
                        contentColor = Color.White
                    )
                ) {
                    Text(tr("结束"))
                }
                Button(
                    onClick = {
                        if (running) {
                            remaining = ((endAt - System.currentTimeMillis()) / 1000L)
                                .toInt()
                                .coerceAtLeast(0)
                            running = false
                            ReminderScheduler.cancelSessionEnd(context)
                        } else {
                            endAt = System.currentTimeMillis() + remaining * 1000L
                            running = true
                            ReminderScheduler.scheduleSessionEnd(
                                context,
                                max(1, ceil(remaining / 60.0).toInt())
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.9f),
                        contentColor = Color(0xFF1B2430)
                    )
                ) {
                    Text(if (running) tr("暂停") else tr("继续"))
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
        }
        AnimatedVisibility(
            visible = finished,
            enter = if (reduceMotion) {
                EnterTransition.None
            } else {
                fadeIn(tween(280, easing = IsleMotion.emphasizedDecelerate)) +
                    slideInVertically(
                        animationSpec = tween(280, easing = IsleMotion.emphasizedDecelerate)
                    ) { it / 14 }
            }
        ) {
            RecordContent(
                tulpaName = tulpa?.name ?: "TA",
                tulpaEmoji = tulpa?.emoji ?: "🌙",
                type = type,
                actualMinutes = max(
                    1,
                    ceil((totalSeconds - remaining.coerceAtLeast(0)) / 60.0).toInt()
                ),
                mood = mood,
                onMoodChange = { mood = it },
                note = note,
                onNoteChange = { note = it },
                onSave = {
                    ReminderScheduler.cancelSessionEnd(context)
                    onSave(
                        startedAt,
                        max(
                            1,
                            ceil((totalSeconds - remaining.coerceAtLeast(0)) / 60.0).toInt()
                        ),
                        mood,
                        note
                    )
                },
                onDiscard = {
                    ReminderScheduler.cancelSessionEnd(context)
                    onBack()
                }
            )
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(tr("结束这次相处？")) },
            text = { Text(tr("现在结束也可以。已经度过的时间会被记录下来。")) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        remaining = ((endAt - System.currentTimeMillis()) / 1000L)
                            .toInt()
                            .coerceIn(0, totalSeconds)
                        running = false
                        finished = true
                        ReminderScheduler.cancelSessionEnd(context)
                    }
                ) {
                    Text(tr("结束"))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(tr("继续相处"))
                }
            }
        )
    }
}

@Composable
private fun ImmersiveBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF18222E),
                        Color(0xFF233444),
                        Color(0xFF18222E)
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stars = listOf(
                0.12f to 0.18f,
                0.28f to 0.09f,
                0.44f to 0.22f,
                0.66f to 0.13f,
                0.82f to 0.26f,
                0.91f to 0.11f,
                0.18f to 0.72f,
                0.37f to 0.84f,
                0.72f to 0.78f,
                0.86f to 0.88f
            )
            stars.forEachIndexed { index, (x, y) ->
                drawCircle(
                    color = Color.White.copy(alpha = if (index % 2 == 0) 0.22f else 0.12f),
                    radius = if (index % 3 == 0) 2.2f else 1.4f,
                    center = androidx.compose.ui.geometry.Offset(
                        x * size.width,
                        y * size.height
                    )
                )
            }
        }
        content()
    }
}

@Composable
private fun BreathingCircle() {
    val reduceMotion = LocalReduceMotion.current
    val scale: State<Float>
    val alpha: State<Float>
    if (reduceMotion) {
        scale = remember { mutableStateOf(1f) }
        alpha = remember { mutableStateOf(0.18f) }
    } else {
        val transition = rememberInfiniteTransition(label = "breath")
        scale = transition.animateFloat(
            initialValue = 0.86f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(4200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
        alpha = transition.animateFloat(
            initialValue = 0.12f,
            targetValue = 0.26f,
            animationSpec = infiniteRepeatable(
                animation = tween(4200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha"
        )
    }
    Box(
        modifier = Modifier
            .size(250.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .clip(CircleShape)
            .drawBehind {
                drawCircle(
                    color = Color(0xFFB8C6E6),
                    alpha = alpha.value,
                    radius = size.minDimension / 2f
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(186.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.18f), CircleShape)
        )
    }
}

@Composable
private fun RecordContent(
    tulpaName: String,
    tulpaEmoji: String,
    type: SessionType,
    actualMinutes: Int,
    mood: String,
    onMoodChange: (String) -> Unit,
    note: String,
    onNoteChange: (String) -> Unit,
    onSave: () -> Unit,
    onDiscard: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = type.icon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            text = tr("这次相处结束了"),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = L.t("$tulpaEmoji $tulpaName · ${type.label} · $actualMinutes 分钟", "$tulpaEmoji $tulpaName · ${type.label} · $actualMinutes min"),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SoftCard {
            Text(
                text = tr("此刻的感觉"),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(10.dp))
            MoodSelector(selected = mood, onSelect = onMoodChange)
        }

        OutlinedTextField(
            value = note,
            onValueChange = onNoteChange,
            label = { Text(tr("想留一句话吗？（可选）")) },
            placeholder = { Text(tr("一个画面、一种感觉，或者什么都不写。")) },
            minLines = 4,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors()
        )

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(tr("保存这次记录"))
        }
        TextButton(
            onClick = onDiscard,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(tr("不记录，直接返回"))
        }
        Spacer(Modifier.height(24.dp))
    }
}

private fun formatClock(seconds: Int): String {
    val safe = seconds.coerceAtLeast(0)
    return String.format(Locale.US, "%02d:%02d", safe / 60, safe % 60)
}
