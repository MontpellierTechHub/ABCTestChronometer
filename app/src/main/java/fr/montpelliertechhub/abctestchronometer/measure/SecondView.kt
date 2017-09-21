package fr.montpelliertechhub.abctestchronometer.measure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout

const val STROKE_WIDTH: Float = 10f
const val FPS: Float = 60f
const val DEGREE_PER_SECOND: Float = 360 * (1 / FPS)

class SecondView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    enum class State { IDLE, RUNNING, PAUSE}

    val mCirclePaint: Paint = Paint()
    val mTextPaint: Paint = Paint()
    var mDegreeRotation: Float = 0f
    var mStarRotation: Float = -90f
    val mRectCircle: RectF = RectF()
    var mSecondTurn: Boolean = false
    var mState: State = State.IDLE

    override fun onFinishInflate() {
        super.onFinishInflate()

        mCirclePaint.color = Color.BLACK
        mCirclePaint.style = Paint.Style.STROKE
        mCirclePaint.strokeWidth = STROKE_WIDTH * 2
        mCirclePaint.isAntiAlias()

        mTextPaint.color = Color.BLACK
        mTextPaint.textSize = 30f
        mTextPaint.isAntiAlias()

        setWillNotDraw(false)
    }

    fun start(){
        mState = State.RUNNING
        invalidate()
    }

    fun pause(){
        mState = State.PAUSE
        invalidate()
    }

    fun updateValues() {
        if (mDegreeRotation > 360) {
            mSecondTurn = !mSecondTurn
            mDegreeRotation = 0f
        }

        mDegreeRotation += DEGREE_PER_SECOND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(mState == State.IDLE){
           return
        }

        mRectCircle.set(
                0f + STROKE_WIDTH,
                0f + STROKE_WIDTH,
                width.toFloat() - STROKE_WIDTH,
                height.toFloat() - STROKE_WIDTH)

        if(mState != State.PAUSE){
            updateValues()
        }

        if(mSecondTurn){
            canvas?.drawArc(mRectCircle, mStarRotation + mDegreeRotation, 360 - mDegreeRotation, false, mCirclePaint)
        } else {
            canvas?.drawArc(mRectCircle, mStarRotation, mDegreeRotation, false, mCirclePaint)
        }

        canvas?.drawText(mDegreeRotation.toString(), 0f, 100f, mTextPaint)

        postInvalidateDelayed((1000 / FPS).toLong())
    }

}