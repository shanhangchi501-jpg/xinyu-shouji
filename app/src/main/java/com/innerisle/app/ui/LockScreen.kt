package com.innerisle.app.ui

import com.innerisle.app.data.tr

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.innerisle.app.ui.theme.IsleMotion
import com.innerisle.app.ui.theme.LocalReduceMotion
import com.innerisle.app.ui.theme.pressScale
import kotlin.math.roundToInt

@Composable
fun LockScreen(
    onUnlock: (String) -> Boolean,
    onQuickExit: () -> Unit
) {
    var pin by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }
    val reduceMotion = LocalReduceMotion.current
    val shake = remember { Animatable(0f) }

    BackHandler { onQuickExit() }

    LaunchedEffect(pin) {
        if (pin.length == 4) {
            val ok = onUnlock(pin)
            if (!ok) {
                error = true
                pin = ""
            }
        }
    }

    LaunchedEffect(error) {
        if (error && !reduceMotion) {
            shake.snapTo(0f)
            shake.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 300
                    0f at 0
                    -10f at 50
                    10f at 110
                    -6f at 170
                    6f at 230
                    0f at 300
                }
            )
        } else {
            // Typing while the shake is running cancels the effect; reset the offset.
            shake.snapTo(0f)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 30.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        AppMark(72.dp)
        Spacer(Modifier.height(20.dp))
        Text(text = tr("心屿手记已锁定"), style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(6.dp))
        Text(
            text = tr("输入 4 位 PIN"),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(22.dp))
        Row(
            modifier = Modifier.offset { IntOffset(shake.value.roundToInt(), 0) },
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            repeat(4) { index ->
                val filled = index < pin.length
                val dotScale by animateFloatAsState(
                    targetValue = if (filled) 1.25f else 1f,
                    animationSpec = if (reduceMotion) snap() else IsleMotion.spatialFast(),
                    label = "pinDotScale"
                )
                val dotColor by animateColorAsState(
                    targetValue = if (filled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                    animationSpec = if (reduceMotion) snap() else IsleMotion.effectsFast(),
                    label = "pinDotColor"
                )
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .graphicsLayer {
                            scaleX = dotScale
                            scaleY = dotScale
                        }
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(
            text = if (error) tr("PIN 不正确，再试一次。") else " ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(18.dp))
        NumberPad(
            onDigit = { digit ->
                if (pin.length < 4) {
                    error = false
                    pin += digit
                }
            },
            onDelete = {
                error = false
                pin = pin.dropLast(1)
            },
            onClear = {
                error = false
                pin = ""
            }
        )
        Spacer(Modifier.weight(1f))
        TextButton(onClick = onQuickExit) {
            Text(tr("快速离开"))
        }
    }
}

@Composable
private fun NumberPad(
    onDigit: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit
) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(tr("清空"), "0", "⌫")
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                row.forEach { key ->
                    val isAction = key == tr("清空") || key == "⌫"
                    KeypadKey(
                        label = key,
                        isAction = isAction,
                        onClick = {
                            when (key) {
                                tr("清空") -> onClear()
                                "⌫" -> onDelete()
                                else -> onDigit(key)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadKey(
    label: String,
    isAction: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val haptic = LocalHapticFeedback.current
    Box(
        modifier = modifier
            // Scale the whole key, including its background, on press.
            .pressScale(interactionSource)
            .height(58.dp)
            .clip(CircleShape)
            .background(
                if (isAction) {
                    Color.Transparent
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = if (isAction) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}
