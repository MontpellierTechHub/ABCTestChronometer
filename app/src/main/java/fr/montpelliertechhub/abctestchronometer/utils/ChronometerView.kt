package fr.montpelliertechhub.abctestchronometer.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Display each second that pass
 *
 * Created by Hugo Gresse on 20/09/2017.
 */
class ChronometerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    val STROKE_WIDTH: Float = 30f
    val FPS: Float = 60f
    val DEGREE_PER_SECOND: Float = 360 * (1/FPS)

    enum class State {
        IDLE, RUNNING, PAUSED
    }

    var mRect: RectF = RectF()
    var mCirclePaint: Paint = Paint()
    var mTextPaint: Paint = Paint()

    var mProgressAngle: Float = 0f
    var mSecondTurn: Boolean = false

    var mState: State = State.IDLE

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

    fun start() {
        mState = State.RUNNING
        this.invalidate()
    }

    fun pause() {
        mState = State.PAUSED
        this.invalidate()
    }

    fun stop() {
        mState = State.IDLE
        this.invalidate()
    }

    fun updateValues() {
        if (mProgressAngle >= 360) {
            mSecondTurn = !mSecondTurn
            mProgressAngle = 0f
        }

        mProgressAngle += DEGREE_PER_SECOND
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

        if(mState == State.RUNNING){
            updateValues()
        }

        if (mSecondTurn) {
            canvas?.drawArc(mRect, -90 + mProgressAngle, 360 - mProgressAngle, false, mCirclePaint)
        } else {
            canvas?.drawArc(mRect, -90f, mProgressAngle, false, mCirclePaint)
        }

        canvas?.drawText(mProgressAngle.toString(), 0f, 100f, mTextPaint)

        if (mState == State.RUNNING) {
            this.postInvalidateDelayed((1000 / FPS).toLong())
        }
    }

}