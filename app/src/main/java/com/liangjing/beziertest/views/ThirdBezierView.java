package com.liangjing.beziertest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liangjing on 2017/7/15.
 * 目的：了解三阶Bezier曲线的原理和运用
 */

public class ThirdBezierView extends View {

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

    //用于判断是否有两根手指同时触摸到手机屏幕
    private boolean isSecondPoint = false;

    public ThirdBezierView(Context context) {
        super(context);
    }

    public ThirdBezierView(Context context, AttributeSet attrs) {
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

    public ThirdBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointOneX = w / 2 - 100;
        mFlagPointOneY = h / 2 - 300;
        mFlagPointTwoX = w / 2 + 100;
        mFlagPointTwoY = h / 2 - 300;

        mPath = new Path();
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
    public boolean onTouchEvent(MotionEvent event) {
        //多点触控。0表示第一根手指，1表示第二根手指。（映射）
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mFlagPointOneX = event.getX(0);
                mFlagPointOneY = event.getY(0);
                if (isSecondPoint) {
                    mFlagPointTwoX = event.getX(1);
                    mFlagPointTwoY = event.getY(1);
                }
                invalidate();
                break;
        }
        return true;
    }
}
