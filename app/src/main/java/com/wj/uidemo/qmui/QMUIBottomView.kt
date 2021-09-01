package com.wj.uidemo.qmui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.nestedScroll.IQMUIContinuousNestedBottomView
import com.qmuiteam.qmui.nestedScroll.IQMUIContinuousNestedScrollCommon.OnScrollNotifier
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.QMUIPagerAdapter
import com.wj.qmuidemo.R
import com.wj.uidemo.widget.ScrollViewPager
import java.util.*

/**
 * Des
 * @author WangJian on 2021/8/27 17
 * */
class QMUIBottomView @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : QMUIContinuousNestedBottomDelegateLayout(context, attrs, defStyleAttr) {

    private var mViewPager: MyViewPager? = null
    private var mCurrentItemView: QMUIContinuousNestedBottomRecyclerView? = null
    private var stickyHeight: Int = QMUIDisplayHelper.dp2px(context, 50)
    private var headerHeight: Int = 0
    private var tab1: TextView? = null
    private var tab2: TextView? = null
    private var mCurrentPosition = -1
    private var secondTab: View? = null
    private var bottomActionListener: OnBottomActionListener? = null

    private var mOnScrollNotifier: OnScrollNotifier? = null

    init {
        initHeaderView()
    }

    override fun onCreateHeaderView(): View {
        return LayoutInflater.from(context).inflate(R.layout.activity_qmui_headerview, null)
    }

    override fun onCreateContentView(): View {
        mViewPager = MyViewPager(context)

        var count = 0
        mViewPager?.adapter = object : QMUIPagerAdapter() {
            override fun hydrate(container: ViewGroup, position: Int): Any {
                val recyclerView = QMUIContinuousNestedBottomRecyclerView(context)
                recyclerView.layoutManager = object : LinearLayoutManager(context) {
                    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT)
                    }
                }
                val adapter = TopAdapter()
                adapter.setOnItemClickListener { _, _, position -> Toast.makeText(context, "click position=$position", Toast.LENGTH_SHORT).show()
                    onDataLoaded(adapter)
                }
                recyclerView.adapter = adapter
                onDataLoaded(adapter, count > 0)
                count++
                return recyclerView
            }

            override fun populate(container: ViewGroup, item: Any, position: Int) {
                container.addView(item as View)
            }

            override fun destroy(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

            override fun getCount(): Int {
                return 2
            }

            override fun isViewFromObject(view: View, o: Any): Boolean {
                return view === o
            }

            override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
                super.setPrimaryItem(container, position, `object`)
                mCurrentItemView = `object` as QMUIContinuousNestedBottomRecyclerView
                mCurrentPosition = position
                if (mOnScrollNotifier != null) {
                    mCurrentItemView?.injectScrollNotifier(mOnScrollNotifier)
                }
            }
        }
        return mViewPager as MyViewPager
    }

    /**
     * 修改为这样后，如果viewpager某个item高度不够时也会充满全屏
     * */
    override fun getContentHeight(): Int {
        println("this@QMUIBottomView.height = ${this@QMUIBottomView.height}")
        return this@QMUIBottomView.height
    }

    override fun getHeaderStickyHeight(): Int {
        return stickyHeight
    }

    override fun getHeaderHeightLayoutParam(): Int {
        return if(headerHeight == 0) QMUIDisplayHelper.dp2px(context, 50) else headerHeight
    }

    fun initHeaderView() {
        tab1 = headerView.findViewById(R.id.tab1)
        tab2 = headerView.findViewById(R.id.tab2)
        secondTab = headerView.findViewById(R.id.secondTab)

        tab1?.setOnClickListener {
            headerHeight = QMUIDisplayHelper.dp2px(context, 50)
            secondTab?.visibility = GONE
            stickyHeight = QMUIDisplayHelper.dp2px(context, 50)
            removeView(headerView)
            addView( headerView, 0, LayoutParams(LayoutParams.MATCH_PARENT, headerHeightLayoutParam))
        }

        tab2?.setOnClickListener {
            headerHeight = QMUIDisplayHelper.dp2px(context, 100)
            secondTab?.visibility = VISIBLE
            stickyHeight = QMUIDisplayHelper.dp2px(context, 100)
            removeView(headerView)
            addView( headerView, 0, LayoutParams(LayoutParams.MATCH_PARENT, headerHeightLayoutParam))
        }

        headerView.findViewById<TextView>(R.id.second_tab4).setOnClickListener {
            bottomActionListener?.scrollBottomViewToTop()
        }
    }

    private fun onDataLoaded(adapter: TopAdapter, short: Boolean = false) {
        if(short){
            val data: List<String> = ArrayList(listOf("Helps", "Maintain", "Liver",
                    "Health", "Function"))
            Collections.shuffle(data)
            adapter.replaceData(data)
        } else {
            val data: List<String> = ArrayList(listOf("Helps", "Maintain", "Liver",
                    "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally",
                    "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet",
                    "Bolster", "Pillow", "Cushion"))
            Collections.shuffle(data)
            adapter.replaceData(data)
        }
    }

    fun hideSticky() {
        stickyHeight = 0
        tab2?.visibility = GONE
    }

    fun showSticky() {
        stickyHeight = QMUIDisplayHelper.dp2px(context, 50)
        tab2?.visibility = VISIBLE
    }

    internal inner class MyViewPager(context: Context?) : ScrollViewPager(context), IQMUIContinuousNestedBottomView {

        override fun consumeScroll(dyUnconsumed: Int) {
            mCurrentItemView?.consumeScroll(dyUnconsumed)
        }

        override fun smoothScrollYBy(dy: Int, duration: Int) {
            mCurrentItemView?.smoothScrollYBy(dy, duration)
        }

        override fun stopScroll() {
            mCurrentItemView?.stopScroll()
        }

        override fun getContentHeight(): Int {
            return mCurrentItemView?.contentHeight ?: 0
        }

        override fun getCurrentScroll(): Int {
            return mCurrentItemView?.currentScroll ?: 0
        }

        override fun getScrollOffsetRange(): Int {
            return mCurrentItemView?.scrollOffsetRange ?: height
        }

        override fun injectScrollNotifier(notifier: OnScrollNotifier) {
            mOnScrollNotifier = notifier
            mCurrentItemView?.injectScrollNotifier(notifier)
        }

        override fun saveScrollInfo(bundle: Bundle) {
            bundle.putInt(KEY_CURRENT_POSITION, mCurrentPosition)
            mCurrentItemView?.saveScrollInfo(bundle)
        }

        override fun restoreScrollInfo(bundle: Bundle) {
            if (mCurrentItemView != null) {
                val currentPos = bundle.getInt(KEY_CURRENT_POSITION, -1)
                if (currentPos == mCurrentPosition) {
                    mCurrentItemView?.restoreScrollInfo(bundle)
                }
            }
        }
    }

    companion object {
        const val KEY_CURRENT_POSITION = "demo_bottom_current_position"
    }

    interface OnBottomActionListener{
        fun scrollBottomViewToTop()
    }

    fun setBottomActionListener(listener: OnBottomActionListener){
        this.bottomActionListener = listener
    }
}