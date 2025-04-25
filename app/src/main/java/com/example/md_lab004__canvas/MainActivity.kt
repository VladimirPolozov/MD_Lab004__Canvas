package com.example.md_lab004__canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.md_lab004__canvas.ui.screens.DrawingApp
import com.example.md_lab004__canvas.ui.screens.ImageButtons
import com.example.md_lab004__canvas.ui.theme.MD_Lab004__CanvasTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drawingView = remember { DrawingView(this) }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Drawing App") }
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    DrawingApp()

                    ImageButtons(drawingView, this@MainActivity)
                }
            }
        }
    }
}