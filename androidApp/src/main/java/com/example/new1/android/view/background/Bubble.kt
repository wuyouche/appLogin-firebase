package com.example.new1.android.view.background

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.new1.android.model.Bubble
import kotlin.math.sin

@Composable
fun LinearGradientBackgroundWithBubbles(modifier: Modifier = Modifier, bubbles: List<Bubble>) {
    val colors = listOf(
        Color(0xFF1605FC),
        Color(0xFF417FF0),
        Color(0xFFB9D0FC),
        Color(0xFF00C6FF)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                colors = colors,
                start = Offset(0f, 0f),
                end = Offset(1000f, 1000f)
            )
        )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawBubbles(bubbles, time)
        }
    }
}

fun DrawScope.drawBubbles(bubbles: List<Bubble>, time: Float) {
    bubbles.forEach { bubble ->
        val x = bubble.x + sin(time * 2 * Math.PI.toFloat() + bubble.phase) * bubble.amplitude
        var y = (bubble.y - time * bubble.speed * size.height) % size.height
        if (y < 0) {
            y += size.height
        }

        drawCircle(
            color = Color.Black.copy(alpha = 0.2f),
            radius = bubble.radius,
            center = Offset(x + 5f, y + 5f)
        )

        val bubbleGradient = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.8f),
                Color.White.copy(alpha = 0.2f)
            ),
            center = Offset(x - bubble.radius * 0.3f, y - bubble.radius * 0.3f),
            radius = bubble.radius * 1.2f
        )
        drawCircle(
            brush = bubbleGradient,
            radius = bubble.radius,
            center = Offset(x, y)
        )

        val highlightPath = Path().apply {
            addArc(
                oval = Rect(
                    left = x - bubble.radius * 0.7f,
                    top = y - bubble.radius * 0.7f,
                    right = x + bubble.radius * 0.7f,
                    bottom = y + bubble.radius * 0.7f
                ),
                startAngleDegrees = -45f,
                sweepAngleDegrees = 90f
            )
        }
        drawPath(
            path = highlightPath,
            color = Color.White.copy(alpha = 0.6f),
            style = Stroke(width = bubble.radius * 0.2f, cap = StrokeCap.Round)
        )
    }
}





