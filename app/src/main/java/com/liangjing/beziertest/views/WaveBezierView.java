package com.liangjing.beziertest.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by liangjing on 2017/7/15.
 * 目的：绘制波浪效果（sinx图形状）
 * 方法：1、借助三角函数 2、借助Bezier曲线
 * 流程：因为需要绘制两个二阶Bezier曲线，所以需要有两个控制点
 */

public class WaveBezierView extends View implements View.OnClickListener {

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

    //要使得波浪动起来，那么就需要利用ValueAnimator属性动画去做一个数值发生器，来控制控制点坐标的偏移，从而实现动画效果
    private ValueAnimator mValueAnimator;
    private int mOffSet;

    //一个正弦波的长度（即两段Bezier曲线所构成的长度）
    private int mWaveLength;
    /**
     * 屏幕高度。屏幕宽度
     * 用来确定屏幕能够容纳多少个正弦波
     */
    private int mScreenHeight;
    private int mScreenWidth;
    //屏幕纵轴上中点坐标
    private int mCenterY;
    //保存屏幕上能容纳的波形的个数以及屏幕外的一个波形
    private int mWaveCount;


    public WaveBezierView(Context context) {
        super(context);
    }

    public WaveBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setColor(Color.LTGRAY);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);

        mWaveLength = 800;
    }

    public WaveBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        setOnClickListener(this);
        mScreenHeight = h;
        mScreenWidth = w;
        mCenterY = h / 2;
        mWaveCount = (int) (mScreenWidth / mWaveLength + 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //起点为屏幕的左侧中点
        mPath.moveTo(-mWaveLength + mOffSet, mCenterY);
        //通过循环依次的画出每个波形
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4 + i * mWaveLength + mOffSet, mCenterY + 60, -mWaveLength / 2 + i * mWaveLength + mOffSet, mCenterY);
            mPath.quadTo(-mWaveLength / 4 + i * mWaveLength + mOffSet, mCenterY - 60, i * mWaveLength + mOffSet, mCenterY);
        }
        //绘制完成波形图之后将该图形封闭起来
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffSet = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
