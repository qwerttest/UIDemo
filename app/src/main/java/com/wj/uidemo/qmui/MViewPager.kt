package com.wj.uidemo.qmui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import com.qmuiteam.qmui.nestedScroll.IQMUIContinuousNestedScrollCommon.OnScrollNotifier
import com.qmuiteam.qmui.widget.QMUIViewPager

/**
 * Des 仅适用于QMUI模板
 * @author WangJian on 2021/09/02 18
 * */
class MViewPager @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, private var scrollNotifierListener: OnScrollNotifierListener? = null) : QMUIViewPager(context, attrs), BottomWidgetItemView {

    companion object {
        const val KEY_CURRENT_POSITION = "demo_bottom_current_position"
    }

    private var myQMUIPagerAdapter: MQMUIPagerAdapter? = null

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        myQMUIPagerAdapter = adapter as MQMUIPagerAdapter?
    }

    override fun consumeScroll(dyUnconsumed: Int) {
        myQMUIPagerAdapter?.getCurrentItemView()?.consumeScroll(dyUnconsumed)
    }

    override fun smoothScrollYBy(dy: Int, duration: Int) {
        myQMUIPagerAdapter?.getCurrentItemView()?.smoothScrollYBy(dy, duration)
    }

    override fun stopScroll() {
        myQMUIPagerAdapter?.getCurrentItemView()?.stopScroll()
    }

    override fun getContentHeight(): Int {
        return myQMUIPagerAdapter?.getCurrentItemView()?.contentHeight ?: 0
    }

    override fun getCurrentScroll(): Int {
        return myQMUIPagerAdapter?.getCurrentItemView()?.currentScroll ?: 0
    }

    override fun getScrollOffsetRange(): Int {
        return myQMUIPagerAdapter?.getCurrentItemView()?.scrollOffsetRange ?: height
    }

    override fun injectScrollNotifier(notifier: OnScrollNotifier) {
        scrollNotifierListener?.setScrollNotifier(notifier)
        myQMUIPagerAdapter?.getCurrentItemView()?.injectScrollNotifier(notifier)
    }

    override fun saveScrollInfo(bundle: Bundle) {
        myQMUIPagerAdapter?.let {
            bundle.putInt(KEY_CURRENT_POSITION, getCurrentPosition())
            it.getCurrentItemView()?.saveScrollInfo(bundle)
        }
    }

    override fun restoreScrollInfo(bundle: Bundle) {
        myQMUIPagerAdapter?.let {
            it.getCurrentItemView()?.let { currentItem ->
                val currentPos = bundle.getInt(KEY_CURRENT_POSITION, -1)
                if (currentPos == getCurrentPosition()) {
                    currentItem.restoreScrollInfo(bundle)
                }
            }
        }
    }

    private fun getCurrentPosition(): Int = myQMUIPagerAdapter?.getCurrentPosition() ?: -1

    interface OnScrollNotifierListener {
        fun setScrollNotifier(scrollNotifier: OnScrollNotifier?)
    }
}