package com.example.md_lab004__canvas

import android.content.Context
import android.graphics.Path
import android.graphics.Paint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView : View {
    private val paths = mutableListOf<Pair<Path, Paint>>()
    private var currentPath: Path? = null
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach { (path, paint) ->
            canvas.drawPath(path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path().apply {
                    moveTo(event.x, event.y)
                }
                paths.add(currentPath!! to Paint(paint))
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath?.lineTo(event.x, event.y)
                invalidate()
            }
        }
        return true
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setBrushSize(size: Float) {
        paint.strokeWidth = size
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    fun setBackgroundBitmap(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        draw(canvas)
        invalidate()
    }
}