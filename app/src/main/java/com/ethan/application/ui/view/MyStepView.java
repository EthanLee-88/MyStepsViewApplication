package com.ethan.application.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ethan.application.R;

/*******
 * 步数显示控件
 * created by Ethan Lee
 * on 2021/1/3
 *******/
public class MyStepView extends View {
    /**
     * 字体颜色
     */
    private int mStepTextColor = Color.BLACK;
    /**
     * 字体尺寸
     */
    private int mStepTextSize = 18;
    /**
     * 边界宽度
     */
    private int mStepBorderWidth = 8;
    /**
     * 边界颜色
     */
    private int mInnerBorderColor = Color.GRAY;
    private int mOuterBorderColor = Color.BLUE;
    /**
     * 当前步数
     */
    private int mStepCurrentSteps = 0;
    /**
     * 总步数
     */
    private int mStepTargetSteps = 5000;

    private Paint outPaint;
    private RectF rectF;
    private Paint textPaint;

    private Paint innerPaint;

    private float percentage;

    public MyStepView(Context context) {
        this(context, null);
    }

    public MyStepView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyStepView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.MyStepView);
        mStepTextSize = (int) array.getDimension(R.styleable.MyStepView_StepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.MyStepView_StepTextColor, mStepTextColor);
        mStepBorderWidth = (int) array.getDimension(R.styleable.MyStepView_StepBorderWidth, mStepBorderWidth);
        mInnerBorderColor = array.getColor(R.styleable.MyStepView_InnerBorderColor, mInnerBorderColor);
        mOuterBorderColor = array.getColor(R.styleable.MyStepView_OuterBorderColor, mOuterBorderColor);
        mStepCurrentSteps = array.getInteger(R.styleable.MyStepView_StepCurrentSteps, mStepCurrentSteps);
        mStepTargetSteps = array.getInteger(R.styleable.MyStepView_StepTargetSteps, mStepTargetSteps);
        array.recycle();

        outPaint = new Paint();
        outPaint.setColor(mOuterBorderColor);
        outPaint.setAntiAlias(true);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setStrokeWidth(mStepBorderWidth);
        rectF = new RectF();

        innerPaint = new Paint();
        innerPaint.setColor(mInnerBorderColor);
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);
        innerPaint.setStrokeWidth(mStepBorderWidth);

        textPaint = new Paint();
        textPaint.setColor(mStepTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(mStepTextSize);
    }

    public void setSteps(int steps){
        if (steps < 0) {
            this.mStepCurrentSteps = 0;
        }else {
            this.mStepCurrentSteps = steps;
        }
        if (steps > mStepTargetSteps){
            this.mStepCurrentSteps = this.mStepTargetSteps;
        }else {
            this.mStepCurrentSteps = steps;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if ((widthMode == MeasureSpec.AT_MOST) && (heightMode == MeasureSpec.AT_MOST)) {
            setMeasuredDimension(680, 680);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(height, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(width > height ? height : width, width > height ? height : width);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("TAG", "getHeight()=" + getHeight() + "-getMeasuredHeight()1=" + getMeasuredHeight());
        super.onLayout(changed, left, top, right, bottom);
        Log.d("TAG", "getHeight()=" + getHeight() + "-getMeasuredHeight()1=" + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("TAG", "getHeight()=" + getHeight() + "-getMeasuredHeight()3=" + getMeasuredHeight());
        super.onDraw(canvas);
        rectF.set(mStepBorderWidth / 2, mStepBorderWidth / 2,
                getWidth() - mStepBorderWidth / 2, getHeight() - mStepBorderWidth / 2);
         // 1、画背景圆弧
        canvas.drawArc(rectF, -45, 270, false, outPaint);
        // 2、画步数圆弧
        percentage = (float) this.mStepCurrentSteps / this.mStepTargetSteps;
        canvas.drawArc(rectF, -45, percentage * 270, false, innerPaint);

        //画文字
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;

        Rect rect = new Rect();
        String text = mStepCurrentSteps + "\n步";
        textPaint.getTextBounds(text, 0, text.length(), rect);
        int starX = getWidth() / 2 - rect.width() / 2;
        canvas.drawText(text, starX, baseLine, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
//        return true;
    }
}
