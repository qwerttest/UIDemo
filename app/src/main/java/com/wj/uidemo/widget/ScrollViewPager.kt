package com.wj.uidemo.widget

import android.content.Context
import android.view.MotionEvent
import com.qmuiteam.qmui.widget.QMUIViewPager

/**
 * Des 控件viewPager是否可滑动切换
 * @author WangJian on 2021/8/31 16
 * */
open class ScrollViewPager(context: Context?) : QMUIViewPager(context){
    var mScrollEnable = true

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return mScrollEnable && super.onTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        try {
            return mScrollEnable && super.onInterceptTouchEvent(ev)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}