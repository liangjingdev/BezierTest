package com.liangjing.beziertest.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.liangjing.beziertest.BezierEvaluator;

/**
 * Created by liangjing on 2017/7/16.
 * 功能：实现购物车抛物轨迹动画
 */

public class PathBezierView extends View implements View.OnClickListener {

    //起点坐标
    private int mStartPointX;
    private int mStartPointY;

    //终点坐标
    private int mEndPointX;
    private int mEndPointY;

    //控制点坐标
    private int mFlagPointX;
    private int mFlagPointY;

    //移动物体的坐标
    private int mMovePointX;
    private int mMovePointY;

    private Path mPath;
    private Paint mPaint;
    private Paint mPaintCircle;

    public PathBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);

        mStartPointX = 100;
        mStartPointY = 100;

        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;

        mEndPointX = 600;
        mEndPointY = 600;

        mFlagPointX = 500;
        mFlagPointY = 0;

        //设置监听器
        setOnClickListener(this);
    }

    public PathBezierView(Context context) {
        super(context);
    }

    public PathBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将起点和终点绘画出来
        canvas.drawCircle(mStartPointX, mStartPointY, 20, mPaintCircle);
        canvas.drawCircle(mEndPointX, mEndPointY, 20, mPaintCircle);
        //将移动点画出来
        canvas.drawCircle(mMovePointX,mMovePointY,20,mPaintCircle);

        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void onClick(View v) {
        BezierEvaluator evaluator = new BezierEvaluator(new PointF(mFlagPointX, mFlagPointY));
        ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator,
                new PointF(mStartPointX, mStartPointY),
                new PointF(mEndPointX, mEndPointY));
        valueAnimator.setDuration(600);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //通过ValueAnimator计算出了当前的点
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                //然后将点的坐标值分别取出来赋给相应的变量
                mMovePointX = (int) pointF.x;
                mMovePointY = (int) pointF.y;
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }
}
