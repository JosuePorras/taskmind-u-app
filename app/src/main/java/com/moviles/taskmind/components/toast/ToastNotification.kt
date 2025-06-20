package com.moviles.taskmind.components.toast

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.taskmind.viewmodel.toast.ToastViewModel
import kotlinx.coroutines.delay

@SuppressLint("ModifierParameter")
@Composable
fun CustomToast(
    message: String,
    toastType: ToastViewModel.ToastType = ToastViewModel.ToastType.INFO,
    duration: ToastViewModel.ToastDuration = ToastViewModel.ToastDuration.SHORT,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier.fillMaxSize()
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
            modifier = modifier.padding(bottom = 32.dp),
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
                color = getToastBackgroundColor(toastType),
                border = BorderStroke(1.dp, getToastBorderColor(toastType))
            ) {
                Column {
                    // Más contraste para la barra de progreso
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = getToastBorderColor(toastType).copy(alpha = 0.6f),
                        trackColor = Color.Transparent,
                    )

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = getIconBackground(toastType),
                                    shape = RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getToastIcon(toastType),
                                contentDescription = null,
                                tint = getTextColor(toastType),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Text(
                            text = message,
                            color = getTextColor(toastType),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = getTextColor(toastType).copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { triggerDismissal = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = getTextColor(toastType),
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
private fun getToastBackgroundColor(type: ToastViewModel.ToastType): Color {
    return when (type) {
        ToastViewModel.ToastType.SUCCESS -> Color(0xFFABECBE)
        ToastViewModel.ToastType.ERROR -> Color(0xFFFF7B6F)
        ToastViewModel.ToastType.INFO -> Color(0xFFA0C6FD)
        ToastViewModel.ToastType.WARNING -> Color(0xFFF6EBA0)
    }
}

@Composable
private fun getToastBorderColor(type: ToastViewModel.ToastType): Color {
    return when (type) {
        ToastViewModel.ToastType.SUCCESS -> Color(0xFF4CAF50)
        ToastViewModel.ToastType.ERROR -> Color(0xFFE53935)
        ToastViewModel.ToastType.INFO -> Color(0xFF2196F3)
        ToastViewModel.ToastType.WARNING -> Color(0xFFFFC107)
    }
}

@Composable
private fun getTextColor(type: ToastViewModel.ToastType): Color {
    return when (type) {
        ToastViewModel.ToastType.WARNING -> Color(0xFF333333)
        else -> Color.Black
    }
}

@Composable
private fun getIconBackground(type: ToastViewModel.ToastType): Color {
    return when (type) {
        ToastViewModel.ToastType.SUCCESS -> Color.White
        ToastViewModel.ToastType.ERROR -> Color.White
        else -> Color.Transparent
    }
}

private fun getToastIcon(type: ToastViewModel.ToastType): ImageVector {
    return when (type) {
        ToastViewModel.ToastType.SUCCESS -> Icons.Default.CheckCircle
        ToastViewModel.ToastType.ERROR -> Icons.Default.Clear
        ToastViewModel.ToastType.INFO -> Icons.Default.Info
        ToastViewModel.ToastType.WARNING -> Icons.Default.Warning
    }
}