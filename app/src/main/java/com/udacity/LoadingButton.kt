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
    private var buttonBackground: Int = 0
    private var progress: Float = 0f
    private var widthSize = 0
    private var heightSize = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {


        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    private var valueAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if(new  == ButtonState.Loading){
            Log.d(TAG, "Button State is :clicked ")
            animatedButton()
        } 

    }


    init {
        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonBackground = getColor(R.styleable.LoadingButton_rectColor,0)


        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawRect((width-widthSize).toFloat(),(height-heightSize).toFloat(),widthSize.toFloat(),heightSize.toFloat(),paint)
        drawButton(canvas)
        if(buttonState == ButtonState.Loading){
            drawButtonFill(canvas)
        }




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
        valueAnimator = ValueAnimator.ofFloat(0F, widthSize.toFloat()).apply {
            duration = 5000
            addUpdateListener { valueAnimator ->
                progress = valueAnimator.animatedValue as Float
                valueAnimator.repeatCount = ValueAnimator.INFINITE
                valueAnimator.repeatMode = ValueAnimator.REVERSE
                valueAnimator.interpolator = LinearInterpolator()
                this@LoadingButton.invalidate() // -> Important
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
}