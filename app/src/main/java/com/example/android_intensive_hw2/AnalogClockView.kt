package com.example.android_intensive_hw2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView : View {

    private val paint = Paint()
    private var calendar = Calendar.getInstance()

    private val romanNumerals = arrayOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII")

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = centerX.coerceAtMost(centerY) - 2

        calendar = Calendar.getInstance()

        paint.color = Color.BLACK
        canvas.drawCircle(centerX, centerY, radius, paint)

        paint.color = Color.WHITE
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER

        for (i in romanNumerals.indices) {
            val angle = Math.toRadians(((i + 1) * 30).toDouble())
            val x = centerX + 0.8f * radius * sin(angle).toFloat()
            val y = centerY - 0.8f * radius * cos(angle).toFloat()

            canvas.drawText(romanNumerals[i], x, y, paint)
        }

        drawHand(canvas, centerX, centerY, calendar.get(Calendar.SECOND) * 6f, radius * 0.9f, 10f, Color.parseColor("#DFE8F2"))
        drawHand(canvas, centerX, centerY, calendar.get(Calendar.MINUTE) * 6f, radius * 0.7f, 20f, Color.parseColor("#DFE8F2"))
        drawHand(canvas, centerX, centerY, (calendar.get(Calendar.HOUR) % 12 + calendar.get(Calendar.MINUTE) / 60f) * 30f, radius * 0.5f, 30f, Color.parseColor("#DFE8F2"))
    }

    private fun drawHand(canvas: Canvas, centerX: Float, centerY: Float, angle: Float, length: Float, with: Float, color: Int) {
        val handX = centerX + length * sin(Math.toRadians(angle.toDouble())).toFloat()
        val handY = centerY - length * cos(Math.toRadians(angle.toDouble())).toFloat()

        paint.color = color
        paint.strokeWidth = with
        canvas.drawLine(centerX, centerY, handX, handY, paint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                postInvalidate()
            }
        }, 0, 1000)
    }
}
