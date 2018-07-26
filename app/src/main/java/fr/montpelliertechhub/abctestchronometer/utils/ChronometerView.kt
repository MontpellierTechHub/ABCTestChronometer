package fr.montpelliertechhub.abctestchronometer.utils

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout


/**
 * Display each second that pass
 *
 * Created by Hugo Gresse on 20/09/2017.
 */

private const val STROKE_WIDTH: Float = 30f
private const val REVOLUTIONDURATION = 1000 // ms
private const val PROPERTY_DEG = "PROPERTY_DEG"

class ChronometerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    enum class State {
        IDLE, RUNNING, PAUSED
    }

    var mRect: RectF = RectF()
    var mCirclePaint: Paint = Paint()
    var mTextPaint: Paint = Paint()
    var mProgressAngle: Float = 0f
    var mSecondTurn: Boolean = false
    var mState: State = State.IDLE
    var mAnimator: ValueAnimator = ValueAnimator()

    override fun onFinishInflate() {
        super.onFinishInflate()

        mCirclePaint.color = Color.BLACK
        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.strokeWidth = 10f
        mCirclePaint.textSize = 30f
        mCirclePaint.isAntiAlias = true

        mTextPaint.color = Color.BLACK
        mTextPaint.textSize = 30f
        mTextPaint.isAntiAlias = true

        setWillNotDraw(false)
    }

    private var mStartChronometer: Long = 0

    fun start(startChronometer: Long) {
        mStartChronometer = startChronometer
        mState = State.RUNNING
        mAnimator = ValueAnimator()
        updateStart()
    }

    fun pause() {
        mState = State.PAUSED
        mAnimator.cancel()
        updateProgressManual()
        invalidate()
    }

    fun stop() {
        mState = State.IDLE
        invalidate()
    }

    private fun updateProgressManual() {
        val currentRelativeToStart = (SystemClock.elapsedRealtime() - mStartChronometer)
        mProgressAngle = (currentRelativeToStart * 360 / REVOLUTIONDURATION) % 360.toFloat()
        invalidate()
    }

    private fun updateStart() {
        updateProgressManual()
        startLoop()
    }

    private fun startLoop() {
        val propertyRotate = PropertyValuesHolder.ofFloat(PROPERTY_DEG, mProgressAngle, 360f)
        mAnimator.setValues(propertyRotate)
        mAnimator.duration = getTimeLeftToFinishLoop()
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.addUpdateListener { animation ->
            mProgressAngle = animation.getAnimatedValue(PROPERTY_DEG) as Float
            invalidate()
        }
        mAnimator.start()
    }

    private fun getTimeLeftToFinishLoop(): Long {
        val current = SystemClock.elapsedRealtime()
        if (current <= (mStartChronometer + 1000)) {
            return 1000 - Math.abs((mStartChronometer + 1000 - current) % 1000)
        } else {
            return 1000 - Math.abs(current - mStartChronometer + 1000) % 1000
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mState == State.IDLE) {
            return
        }

        mRect.set(
                STROKE_WIDTH,
                STROKE_WIDTH,
                width.toFloat() - STROKE_WIDTH,
                height.toFloat() - STROKE_WIDTH)

        if (mState == State.RUNNING && mProgressAngle >= 360) {
            mSecondTurn = !mSecondTurn
            mProgressAngle = 0f
            startLoop()
        }

        if (mSecondTurn) {
            canvas?.drawArc(mRect, -90 + mProgressAngle, 360 - mProgressAngle, false, mCirclePaint)
        } else {
            canvas?.drawArc(mRect, -90f, mProgressAngle, false, mCirclePaint)
        }
    }

}