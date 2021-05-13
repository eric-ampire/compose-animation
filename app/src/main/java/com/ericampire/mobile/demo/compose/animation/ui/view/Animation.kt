package com.ericampire.mobile.demo.compose.animation.ui.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ericampire.mobile.demo.compose.animation.ui.theme.Purple500
import com.ericampire.mobile.demo.compose.animation.ui.theme.WhiteTransparent

@Composable
fun LoadingAnimation() {
  var currentRotation by remember { mutableStateOf(0f) }
  val rotation = remember { Animatable(currentRotation) }

  LaunchedEffect(true) {
    rotation.animateTo(
      targetValue = currentRotation + 360f,
      animationSpec = infiniteRepeatable(
        animation = tween(2000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
      ),
      block = {
        currentRotation = value
      }
    )
  }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = Purple500,
    content = {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
          Canvas(modifier = Modifier.size(300.dp)) {
            drawCircle(
              style = Stroke(width = 20f),
              color = WhiteTransparent
            )

            drawArc(
              color = Color.White,
              startAngle = rotation.value,
              sweepAngle = 30f,
              useCenter = false,
              style = Stroke(width = 20f),
            )
          }

          Surface(
            modifier = Modifier.size(278.dp),
            shape = CircleShape,
            color = Color.Transparent,
            content = {
              WaveView(
                timeSpec = 10000,
                init = true,
                initValue = 1f,
                targetValue = 0f
              )
              WaveView(
                timeSpec = 10000,
                init = true,
                initValue = 0f,
                targetValue = 1f,
                waveWidth = 800
              )
              WaveView(
                timeSpec = 4000,
                init = true,
                initValue = 0f,
                targetValue = 1f,
                waveWidth = 700,
                dxTimeSpec = 2000
              )
            }
          )
        }
      )
    }
  )
}

@Composable
fun WaveView(
  modifier: Modifier = Modifier,
  timeSpec: Long,
  initValue: Float,
  targetValue: Float,
  init: Boolean,
  waveWidth: Int = 600,
  dxTimeSpec: Int = 4000
) {

  val deltaXAnim = rememberInfiniteTransition()
  val dx by deltaXAnim.animateFloat(
    initialValue = initValue,
    targetValue = targetValue,
    animationSpec = infiniteRepeatable(
      animation = tween(dxTimeSpec, easing = LinearEasing)
    )
  )

  val dy by deltaXAnim.animateFloat(
    initialValue = 300f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(5000, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    )
  )

  val screenWidthPx = with(LocalDensity.current) {
    (LocalConfiguration.current.screenHeightDp * density) - 150.dp.toPx()
  }
  val animTranslate by animateFloatAsState(
    targetValue = if (init) 0f else screenWidthPx,
    animationSpec = TweenSpec(if (init) 0 else timeSpec.toInt(), easing = LinearEasing)
  )

  val waveHeight by animateFloatAsState(
    targetValue = if (init) 125f else 0f,
    animationSpec = TweenSpec(if (init) 0 else timeSpec.toInt(), easing = LinearEasing)
  )

  val path = Path()

  Canvas(
    modifier = modifier.fillMaxSize(),
    onDraw = {
      translate(top = animTranslate) {
        drawPath(path = path, color = WhiteTransparent)
        path.reset()
        val halfWaveWidth = waveWidth / 2
        path.moveTo(-waveWidth + (waveWidth * dx), dy.dp.toPx())

        for (i in -waveWidth..(size.width.toInt() + waveWidth) step waveWidth) {
          path.relativeQuadraticBezierTo(
            halfWaveWidth.toFloat() / 2,
            -waveHeight,
            halfWaveWidth.toFloat(),
            0f
          )
          path.relativeQuadraticBezierTo(
            halfWaveWidth.toFloat() / 2,
            waveHeight,
            halfWaveWidth.toFloat(),
            0f
          )
        }

        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        path.close()
      }
    }
  )
}