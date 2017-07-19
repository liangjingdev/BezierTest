package com.liangjing.beziertest.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by liangjing on 2017/7/15.
 * 目的：利用Bezier曲线实现路径变换动画
 * 关键：改变控制点的位置（可以人为的改变控制点的坐标从而改变动画效果）
 * 流程：使用属性动画来控制这两个控制点的坐标，让它们从起点和终点之间逐渐的增大到某一个高度，让这样的一条曲线从直线开始发生变化
 */

public class PathMorphingBezierView extends View implements View.OnClickListener{

    //起始点
    private float mStartPointX;
    private float mStartPointY;

    //终点
    private float mEndPointX;
    private float mEndPointY;

    //控制点
    private float mFlagPointOneX;
    private float mFlagPointOneY;
    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;

    private Paint mPaintBezier;
    private Paint mPaintFlag;
    private Paint mPaintFlagText;

    private ValueAnimator mValueAnimator;

    public PathMorphingBezierView(Context context) {
        super(context);
    }

    public PathMorphingBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(3);
        mPaintFlag.setStyle(Paint.Style.STROKE);

        mPaintFlagText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlagText.setStyle(Paint.Style.STROKE);
        mPaintFlagText.setTextSize(20);

    }

    public PathMorphingBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        //开始时是一条直线，现在要利用属性动画来控制这两个控制点，从而让该曲线发生改变
        mFlagPointOneX = mStartPointX;
        mFlagPointOneY = mStartPointY;
        mFlagPointTwoX = mEndPointX;
        mFlagPointTwoY = mEndPointY;

        mPath = new Path();
        mValueAnimator = ValueAnimator.ofFloat(mStartPointY,h);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.setDuration(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPointOneY = (float) animation.getAnimatedValue();
                mFlagPointTwoY = (float) animation.getAnimatedValue();
                //记得要调用invalidate()让整个自定义view进行重绘
                invalidate();
            }
        });
        //最後不要忘记在其初始化的时候给该自定义view设置好监听器
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY);

        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintFlagText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintFlagText);
        canvas.drawPoint(mFlagPointOneX, mFlagPointOneY, mPaintFlag);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mPaintFlagText);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintFlagText);
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintFlag);
        canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag);
        canvas.drawLine(mEndPointX, mEndPointY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
