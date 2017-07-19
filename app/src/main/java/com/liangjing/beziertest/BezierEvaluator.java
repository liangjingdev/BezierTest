package com.liangjing.beziertest;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.liangjing.beziertest.util.BezierUtil;

/**
 * Created by liangjing on 2017/7/16.
 * 功能：Evaluator是属性动画中非常重要的一个东西，他根据输入的初始值和结束值以及一个进度比，
 * 那么就可以计算出每一个进度比下所要返回的值。
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mFlagPoint;

    /**
     * 功能：传入控制点的坐标，然后再利用BezierUtil工具类进行计算曲线上相应点的坐标
     *
     * @param flagPoint
     */
    public BezierEvaluator(PointF flagPoint) {
        mFlagPoint = flagPoint;
    }

    /**
     * 功能：通过计算之后得到的一个点，告诉我们的物体应该显示在Bezier曲线上的哪个地方。
     *
     * @param fraction   比例参数（表示开始值和结束值之间的比例）计算是一个简单的参数计算:结果=x0+t(x1-x0)，x0是起始值，x1是结束值，而t是比例参数。
     * @param startValue 起点
     * @param endValue   终点
     * @return 将返回值返回给ValueAnimator,让它告诉属性动画的调用者当前的点应该显示在哪个地方
     */
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(fraction, startValue, mFlagPoint, endValue);
    }
}
