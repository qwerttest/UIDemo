package com.wj.uidemo.qmui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.qmuiteam.qmui.nestedScroll.IQMUIContinuousNestedScrollCommon
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout

/**
 * Des 抽象出通用BottomView控件
 * @author WangJian on 2021/9/3 11
 * */
abstract class MBottomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : QMUIContinuousNestedBottomDelegateLayout(context, attrs, defStyleAttr) {
    //content
    private var mViewPager: MViewPager? = null
    private var mOnScrollNotifier: IQMUIContinuousNestedScrollCommon.OnScrollNotifier? = null
    //content

    final override fun onCreateHeaderView(): View {
        return initHeaderView()
    }

    final override fun onCreateContentView(): View {
        mViewPager = MViewPager(context, scrollNotifierListener = object : MViewPager.OnScrollNotifierListener {
            override fun setScrollNotifier(scrollNotifier: IQMUIContinuousNestedScrollCommon.OnScrollNotifier?) {
                mOnScrollNotifier = scrollNotifier
            }
        })
        mViewPager?.adapter = MQMUIPagerAdapter(initContentViews(), listener = object : MQMUIPagerAdapter.PrimaryItemListener {
            override fun setPrimaryItem(itemViewBase: BottomWidgetItemView?, currentPosition: Int) {
                itemViewBase?.injectScrollNotifier(mOnScrollNotifier)
                onPrimaryItem(itemViewBase, currentPosition)
            }
        })
        doViewPager(mViewPager)
        return mViewPager as MViewPager
    }

    /**
     * 修改为这样后，如果viewpager某个item高度不够时也会充满全屏
     * */
    override fun getContentHeight(): Int {
        return this@MBottomView.height
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
//       return super.onInterceptTouchEvent(event);
        return false
    }


    abstract fun initContentViews(): List<BottomWidgetItemView>

    abstract fun initHeaderView(): View

    /**
     * 在此处对content(ViewPager进行自定义处理)
     * */
    protected open fun doViewPager(viewPager: MViewPager?) {
    }

    /**
     * ContentView内容页发生了变化，viewPager第一次初始化内容时也会调用
     */
    protected open fun onPrimaryItem(itemViewBase: BottomWidgetItemView?, currentPosition: Int) {
    }
}