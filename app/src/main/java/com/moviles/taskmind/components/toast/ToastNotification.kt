package com.moviles.taskmind.components.toast

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import kotlinx.coroutines.delay


@Composable
fun CustomToast(
    message: String,
    toastType: ToastViewModel.ToastType = ToastViewModel.ToastType.INFO,
    duration: ToastViewModel.ToastDuration = ToastViewModel.ToastDuration.SHORT,
    onDismiss: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(true) }
    var triggerDismissal by remember { mutableStateOf(false) }
    val density = LocalDensity.current


    var progress by remember { mutableFloatStateOf(1f) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 400)
    )

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.6f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val offsetY by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 30.dp,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )


    LaunchedEffect(Unit) {
        if (duration != ToastViewModel.ToastDuration.INDEFINITE) {
            val totalTime = duration.timeMillis
            val frameTime = 50L

            var elapsedTime = 0L
            while (elapsedTime < totalTime) {
                delay(frameTime)
                elapsedTime += frameTime
                progress = 1f - (elapsedTime.toFloat() / totalTime.toFloat())
            }

            isVisible = false
            delay(300)
            onDismiss()
        }
    }


    LaunchedEffect(triggerDismissal) {
        if (triggerDismissal) {
            isVisible = false
            delay(300)
            onDismiss()
            triggerDismissal = false
        }
    }

    if (alpha > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .alpha(alpha)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationY = with(density) { -offsetY.toPx() }
                    },
                shape = RoundedCornerShape(8.dp),
                color = getToastColor(toastType).copy(alpha = 0.95f),
                border = BorderStroke(1.dp, getToastBorderColor(toastType))
            ) {
                Column {

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = Color.White.copy(alpha = 0.8f),
                        trackColor = Color.Transparent,
                    )

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = getToastIcon(toastType),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = message,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { triggerDismissal = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                    }
                }
            }
        }
    }
}



@Composable
private fun getToastColor(toastType: ToastViewModel.ToastType): Color {
    return when (toastType) {
        ToastViewModel.ToastType.SUCCESS -> Color(0xFF4CAF50)
        ToastViewModel.ToastType.ERROR -> Color(0xFFF44336)
        ToastViewModel.ToastType.INFO -> Color(0xFF2196F3)
        ToastViewModel.ToastType.WARNING -> Color(0xFFFF9800)
    }
}

@Composable
private fun getToastBorderColor(toastType: ToastViewModel.ToastType): Color {
    return when (toastType) {
        ToastViewModel.ToastType.SUCCESS -> Color(0xFF388E3C)
        ToastViewModel.ToastType.ERROR -> Color(0xFFD32F2F)
        ToastViewModel.ToastType.INFO -> Color(0xFF1976D2)
        ToastViewModel.ToastType.WARNING -> Color(0xFFF57C00)
    }
}

private fun getToastIcon(toastType: ToastViewModel.ToastType): ImageVector {
    return when (toastType) {
        ToastViewModel.ToastType.SUCCESS -> Icons.Default.CheckCircle
        ToastViewModel.ToastType.ERROR -> Icons.Default.Clear
        ToastViewModel.ToastType.INFO -> Icons.Default.Info
        ToastViewModel.ToastType.WARNING -> Icons.Default.Warning
    }
}

