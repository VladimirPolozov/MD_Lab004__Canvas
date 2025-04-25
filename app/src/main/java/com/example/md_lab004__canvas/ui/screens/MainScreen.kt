package com.example.md_lab004__canvas.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.md_lab004__canvas.DrawingView

@SuppressLint("RememberReturnType")
@Composable
fun DrawingApp() {
    val context = LocalContext.current
    val drawingView = remember { DrawingView(context) }
    var brushSize by remember { mutableFloatStateOf(10f) }
    var selectedColor by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { drawingView },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White)
        )

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf(Color.Red, Color.Blue, Color.Green, Color.Black).forEach { color ->
                ColorButton(color = color) {
                    selectedColor = color
                    drawingView.setColor(color.toArgb())
                }
            }
        }

        Slider(
            value = brushSize,
            onValueChange = { newSize ->
                brushSize = newSize
                drawingView.setBrushSize(newSize)
            },
            valueRange = 5f..50f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        ImageButtons(drawingView = drawingView, context = context)
    }
}

@Composable
fun ImageButtons(drawingView: DrawingView, context: Context) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                drawingView.setBackgroundBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Row(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Load Image")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            val bitmap = drawingView.getBitmap()
            saveBitmapToGallery(context, bitmap)
        }) {
            Text("Save Image")
        }
    }
}

@Composable
fun ColorButton(
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp) // Size of the button
            .clip(CircleShape) // Make it circular
            .background(color) // Set the background color
            .clickable(onClick = onClick) // Handle clicks
            .border(2.dp, Color.DarkGray, CircleShape) // Add a border for better visibility
    )
}