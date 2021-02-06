package com.udacity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

private const val TAG = "LoadingButton"
class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var angle: Float = 0f
    private var buttonBackground: Int = 0
    private var progress: Float = 0f
    private var widthSize = 0
    private var heightSize = 0
    private var buttonText = "Download"
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
    }

    private var buttonAnimator = ValueAnimator()
    private var circleAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when(new){
            ButtonState.Loading -> {
                buttonText = "We are loading"
                animatedButton()
                animatedCircle()
            }
            ButtonState.Completed -> {
                stopAnimation()
            }

        } 

    }

    private fun stopAnimation() {
        progress = 0f
        angle = 0f
        buttonText = "DownLoad"
        invalidate()
    }


    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonBackground = getColor(R.styleable.LoadingButton_rectColor,0)


        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawButton(canvas)
        if(buttonState == ButtonState.Loading){
            drawButtonFill(canvas)
            drawCircle(canvas)
        }
        drawText(canvas)



    }
    private fun drawButton(canvas: Canvas?) {
        paint.color = buttonBackground
        canvas?.drawRect(0F, 0F, widthSize.toFloat(), heightSize.toFloat(), paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            View.MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
    private fun animatedButton() {
        buttonAnimator = ValueAnimator.ofFloat(0F, widthSize.toFloat()).apply {
            duration = 1000
            addUpdateListener { valueAnimator ->
                progress = valueAnimator.animatedValue as Float
                valueAnimator.interpolator = LinearInterpolator()
                this@LoadingButton.invalidate()
            }

            start()
        }
    }
    private fun animatedCircle() {
        circleAnimator = ValueAnimator.ofFloat(0F, 360F).apply {
            duration = 1000
            addUpdateListener { valueAnimator ->
                angle = valueAnimator.animatedValue as Float
                this@LoadingButton.invalidate()
            }

            start()
        }
    }
    private fun drawButtonFill(canvas: Canvas?) {
        paint.color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
        canvas?.drawRect(
                0f,
                0f,
                progress,
                heightSize.toFloat(), paint)
    }
    private fun drawText(canvas: Canvas?) {
        paint.color = ResourcesCompat.getColor(resources, R.color.white, null)
        canvas?.drawText(buttonText, widthSize / 2F, (heightSize / 2F) , paint)
    }
    private fun drawCircle(canvas: Canvas?) {
        paint.color= Color.YELLOW
        canvas?.drawArc((widthSize.toFloat() - 300f),(heightSize.toFloat() / 2) - 25f, (widthSize.toFloat()-250f),
                (heightSize.toFloat() / 2) + 25f, 0F,angle, true,paint)
    }
}