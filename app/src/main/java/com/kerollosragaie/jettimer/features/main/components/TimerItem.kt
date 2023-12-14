package com.kerollosragaie.jettimer.features.main.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerollosragaie.jettimer.core.theme.blue200
import com.kerollosragaie.jettimer.core.theme.blue400
import com.kerollosragaie.jettimer.core.theme.blue500
import com.kerollosragaie.jettimer.core.theme.cardColor
import com.kerollosragaie.jettimer.core.utils.getFormattedTime
import com.kerollosragaie.jettimer.features.main.MainViewModel.Companion.totalTime


const val TIMER_RADIUS = 300f

@Composable
fun TimerItem(
    currentTimer: Long,
    isRunning: Boolean,
    onStart: () -> Unit,
    onReStart: () -> Unit,
) {

    val transition = updateTransition(targetState = currentTimer, label = null)

    val tran by transition.animateFloat(
        transitionSpec = { tween(1000, easing = FastOutLinearInEasing) },
        label = ""
    ) { timeLeft ->
        if (timeLeft < 0) {
            360f
        } else {
            360f - ((360f / totalTime) * (totalTime - timeLeft))
        }
    }

    val progress by animateFloatAsState(targetValue = if (isRunning) tran else 0f, label = "")


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
        ) {
            Button(onClick = onStart) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = onReStart) {
                Text(text = "Restart")
            }
        }
        TimerProgressIndicator(
            progress,
            currentTimer,
        )

    }


}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun TimerProgressIndicator(progress: Float, currentTime: Long) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        CircularIndicator(progress = progress)
        AnimatedContent(
            targetState = currentTime,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                } else {
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                }.using(
                    sizeTransform = SizeTransform(clip = false)
                )
            }
        ) { time ->
            Text(
                text = getFormattedTime(time),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun CircularIndicator(progress: Float) {
    Surface(color = MaterialTheme.colorScheme.background) {
        val stroke = with(LocalDensity.current) {
            Stroke(
                width = 30.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            inset(
                horizontal = (size.width / 2) - TIMER_RADIUS,
                vertical = (size.height / 2) - TIMER_RADIUS
            ) {
                val gradient = Brush.linearGradient(
                    listOf(blue500, blue200, blue400)
                )
                drawCircle(
                    color = cardColor,
                    radius = TIMER_RADIUS,
                    center = center
                )
                drawProgressIndicator(
                    brush = gradient,
                    progress,
                    stroke
                )
            }
        }
    }

}

private fun DrawScope.drawProgressIndicator(
    brush: Brush,
    progress: Float,
    stroke: Stroke,
) {
    val innerRadius = (size.minDimension - stroke.width) / 2
    val halfSize = size / 2.0f
    val topLeft = Offset(
        x = halfSize.width - innerRadius,
        y = halfSize.height - innerRadius
    )
    val size = Size(innerRadius * 2, innerRadius * 2)
    drawArc(
        brush = brush,
        startAngle = 270f,
        sweepAngle = progress,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = stroke,
        blendMode = BlendMode.SrcIn
    )
}

@Preview(showBackground = true)
@Composable
fun PrevTimer() {
    TimerItem(
        currentTimer = 2000L,
        isRunning = false,
        onStart = {},
        onReStart = {},
    )
}