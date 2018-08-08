package com.rjy.dagger.utils

import android.graphics.PointF



class GeometryUtil {

    companion object {
        /**
         * As meaning of method name.
         * 获得两点之间的距离
         *
         * @param p0
         * @param p1
         * @return
         */
        fun getDistanceBetween2Points(p0: PointF, p1: PointF): Float {
            return Math.sqrt(Math.pow((p0.y - p1.y).toDouble(), 2.0) + Math.pow((p0.x - p1.x).toDouble(), 2.0)).toFloat()
        }

        /**
         * Get middle point between p1 and p2.
         * 获得两点连线的中点
         *
         * @param p1
         * @param p2
         * @return
         */
        fun getMiddlePoint(p1: PointF, p2: PointF): PointF {
            return PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f)
        }

        /**
         * Get point between p1 and p2 by percent.
         * 根据百分比获取两点之间的某个点坐标
         *
         * @param p1
         * @param p2
         * @param percent
         * @return
         */
        fun getPointByPercent(p1: PointF, p2: PointF, percent: Float): PointF {
            return PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y))
        }

        /**
         * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
         *
         * @param fraction
         * @param start
         * @param end
         * @return
         */
        fun evaluateValue(fraction: Float, start: Number, end: Number): Float {
            return start.toFloat() + (end.toFloat() - start.toFloat()) * fraction
        }


        /**
         * Get the point of intersection between circle and line.
         * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
         *
         * @param pMiddle The circle center point.
         * @param radius  The circle radius.
         * @param lineK   The slope of line which cross the pMiddle.
         * @return
         */
        fun getIntersectionPoints(pMiddle: PointF, radius: Float, lineK: Double?): Array<PointF?> {
            val points = arrayOfNulls<PointF>(2)

            val radian: Float
            var xOffset = 0f
            var yOffset = 0f
            if (lineK != null) {
                radian = Math.atan(lineK).toFloat()
                xOffset = (Math.sin(radian.toDouble()) * radius).toFloat()
                yOffset = (Math.cos(radian.toDouble()) * radius).toFloat()
            } else {
                xOffset = radius
                yOffset = 0f
            }
            points[0] = PointF(pMiddle.x + xOffset, pMiddle.y - yOffset)
            points[1] = PointF(pMiddle.x - xOffset, pMiddle.y + yOffset)

            return points
        }
    }
}