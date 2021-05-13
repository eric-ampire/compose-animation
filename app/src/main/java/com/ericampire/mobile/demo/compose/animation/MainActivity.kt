package com.ericampire.mobile.demo.compose.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ericampire.mobile.demo.compose.animation.ui.theme.ComposeAnimationTheme
import com.ericampire.mobile.demo.compose.animation.ui.view.LoadingAnimation

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeAnimationTheme {
        LoadingAnimation()
      }
    }
  }
}