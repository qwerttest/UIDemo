package com.wj.uidemo.qmui

import android.view.View
import android.view.ViewGroup
import com.qmuiteam.qmui.widget.QMUIPagerAdapter

/**
 * 基础适配器
 *
 * @author WangJian
 * */
class MQMUIPagerAdapter(private var views: List<BottomWidgetItemView>, private var listener: PrimaryItemListener? = null) : QMUIPagerAdapter() {
    private var mCurrentItemView: BottomWidgetItemView? = null
    private var mCurrentPosition = -1

    override fun hydrate(container: ViewGroup, position: Int): Any {
        return views[position]
    }

    override fun populate(container: ViewGroup, item: Any, position: Int) {
        container.addView(item as View)
    }

    override fun destroy(container: ViewGroup, position: Int, itemView: Any) {
        container.removeView(itemView as View)
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun isViewFromObject(view: View, itemView: Any): Boolean {
        return view === itemView
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, itemView: Any) {
        super.setPrimaryItem(container, position, itemView)
        mCurrentItemView = itemView as BottomWidgetItemView
        if (mCurrentPosition != position && listener != null) {
            mCurrentPosition = position
            listener?.setPrimaryItem(mCurrentItemView, position)
        }
    }

    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }

    fun getCurrentItemView(): BottomWidgetItemView? {
        return mCurrentItemView
    }

    interface PrimaryItemListener {
        fun setPrimaryItem(itemViewBase: BottomWidgetItemView?, currentPosition: Int)
    }
}

