package com.danamansour.engineerhub

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class PomodoroMode(val title: String, val durationSeconds: Int) {
    FOCUS("Focus Session", 25 * 60),
    SHORT_BREAK("Short Break", 5 * 60),
    LONG_BREAK("Long Break", 15 * 60)
}

@Composable
fun PomodoroScreen() {
    var currentMode by remember { mutableStateOf(PomodoroMode.FOCUS) }
    var timeLeftSeconds by remember { mutableStateOf(currentMode.durationSeconds) }
    var isRunning by remember { mutableStateOf(false) }


    LaunchedEffect(isRunning, currentMode) {
        if (isRunning) {
            while (timeLeftSeconds > 0) {
                delay(1000L)
                timeLeftSeconds--
            }
            if (timeLeftSeconds == 0) {
                isRunning = false
            }
        }
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val gradientStart = Color(0xFF6DD5FA)
    val gradientEnd = Color(0xFF2980B9)

    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    val progress = timeLeftSeconds.toFloat() / currentMode.durationSeconds.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 800),
        label = "Timer Progress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        Text(
            text = "Pomodoro Study Hub",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )


        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PomodoroMode.values().forEach { mode ->
                val isSelected = currentMode == mode
                OutlinedButton(
                    onClick = {
                        isRunning = false
                        currentMode = mode
                        timeLeftSeconds = mode.durationSeconds
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) GradientEnd else Color.Transparent,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(GradientEnd, primaryColor))
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = when (mode) {
                            PomodoroMode.FOCUS -> "Focus"
                            PomodoroMode.SHORT_BREAK -> "Short Break"
                            PomodoroMode.LONG_BREAK -> "Long Break"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(280.dp)
        ) {
            Canvas(modifier = Modifier.size(260.dp)) {
                val strokeWidth = 20.dp.toPx()


                drawArc(
                    color = trackColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )


                val arcGradient = Brush.sweepGradient(
                    colors = listOf( primaryColor, gradientEnd , primaryColor , GradientEnd))


                drawArc(
                    brush = arcGradient,
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formatTime(timeLeftSeconds),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 44.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isRunning) currentMode.title else "Paused",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    isRunning = false
                    timeLeftSeconds = currentMode.durationSeconds
                },
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Reset Timer",
                    tint = primaryColor,
                    modifier = Modifier.size(28.dp)
                )
            }


            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier
                    .height(56.dp)
                    .width(160.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GradientEnd)
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Outlined.Pause else Icons.Outlined.PlayArrow,
                    contentDescription = "Play/Pause",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isRunning) "PAUSE" else "START",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", mins, secs)
}