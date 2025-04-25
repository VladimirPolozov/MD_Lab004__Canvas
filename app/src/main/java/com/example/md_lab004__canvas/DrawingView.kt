package com.example.md_lab004__canvas

import android.annotation.SuppressLint
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
    private val paintTemplate = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paths.forEach { (path, paint) ->
            canvas.drawPath(path, paint)
        }
        currentPath?.let {
            canvas.drawPath(it, paintTemplate)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPath = Path().apply {
                    moveTo(event.x, event.y)
                }
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath?.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                currentPath?.let {
                    paths.add(it to Paint(paintTemplate))
                    currentPath = null
                }
            }
        }
        return true
    }

    fun setColor(color: Int) {
        paintTemplate.color = color
    }

    fun setBrushSize(size: Float) {
        paintTemplate.strokeWidth = size
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    fun setBackgroundBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        val canvas = Canvas(bitmap)
        draw(canvas)
        invalidate()
    }
}