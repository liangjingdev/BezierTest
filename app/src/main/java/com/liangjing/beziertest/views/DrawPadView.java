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
 * 作用：利用Bezier曲线的原理来绘制平滑的线条
 *       若是使用lineTo()方法来绘制的话，会有明显缺点。（出现明显的棱角）
 *          1、绘制较快的时候
 *          2、绘制超过三个点的时候
 */

public class DrawPadView extends View {

    private Path mPath;
    private Paint mPaint;
    private float mX;
    private float mY;

    public DrawPadView(Context context) {
        super(context);
    }

    public DrawPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawPadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX,mY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();
                /**
                  将两个点之间连接成一条直线(由于使用该方法会有棱角出现，则可以利用bezier曲线来构建平滑的直线)
                   mPath.lineTo(x1,y1);
                */
                float cx = (x1 + mX) / 2;
                float cy = (y1 + mY) / 2;
                mPath.quadTo(mX,mY,cx,cy);
                //需要重新赋值。使得连接直线的两个点分别是新的那个点和上一个点。
                mX = x1;
                mY = y1;
                break;

        }
        //刷新重绘
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将路径画出
        canvas.drawPath(mPath,mPaint);
    }
}
