package com.wj.uidemo.qmui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.wj.qmuidemo.R
import com.wj.uidemo.utils.dp

/**
 * Des
 * @author WangJian on 2021/8/27 17
 * */
class QMUIBottomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MBottomView(context, attrs, defStyleAttr) {

    private var stickyHeight: Int = 50.dp
    private var headerHeight: Int = 0
    //header
    private var tab1: TextView? = null
    private var tab2: TextView? = null
    private var secondTab: View? = null
    //header

    private var bottomActionListener: OnBottomActionListener? = null

    override fun initHeaderView(): View {
        val headerView = LayoutInflater.from(context).inflate(R.layout.activity_qmui_headerview, null)

        tab1 = headerView.findViewById(R.id.tab1)
        tab2 = headerView.findViewById(R.id.tab2)
        secondTab = headerView.findViewById(R.id.secondTab)

        tab1?.setOnClickListener {
            headerHeight = 50.dp
            secondTab?.visibility = GONE
            stickyHeight = 50.dp
            removeView(headerView)
            addView(headerView, 0, LayoutParams(LayoutParams.MATCH_PARENT, headerHeightLayoutParam))
        }

        tab2?.setOnClickListener {
            headerHeight = 100.dp
            secondTab?.visibility = VISIBLE
            stickyHeight = 100.dp
            removeView(headerView)
            addView(headerView, 0, LayoutParams(LayoutParams.MATCH_PARENT, headerHeightLayoutParam))
        }

        headerView.findViewById<TextView>(R.id.second_tab4).setOnClickListener {
            bottomActionListener?.scrollBottomViewToTop()
        }
        return headerView
    }

    override fun initContentViews(): List<BottomWidgetItemView> {
        val list = mutableListOf<BottomWidgetItemView>()
        list.add(generateComp0())
        list.add(generateViewPager())
        return list
    }

    override fun doViewPager(viewpager: MViewPager?){
        viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if(position == 0){
                    tab1?.performClick()
                } else if(position == 1){
                    tab2?.performClick()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun getHeaderStickyHeight(): Int {
        return stickyHeight
    }

    override fun getHeaderHeightLayoutParam(): Int {
        return if (headerHeight == 0) 50.dp else headerHeight
    }


    private fun onDataLoaded(adapter: TopAdapter, short: Boolean = false) {
        if (short) {
            val data: List<String> = ArrayList(listOf("Helps", "Maintain", "Liver",
                    "Health", "Function"))
            adapter.replaceData(data)
        } else {
            val data: List<String> = ArrayList(listOf("Helps", "Maintain", "Liver",
                    "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally",
                    "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet",
                    "Bolster", "Pillow", "Cushion"))
            adapter.replaceData(data)
        }
    }

    fun hideSticky() {
        stickyHeight = 0
        tab2?.visibility = GONE
    }

    fun showSticky() {
        stickyHeight = headerHeight
        tab2?.visibility = VISIBLE
    }

    interface OnBottomActionListener {
        fun scrollBottomViewToTop()
    }

    fun setBottomActionListener(listener: OnBottomActionListener) {
        this.bottomActionListener = listener
    }

    private fun generateComp0(): BottomWidgetItemView{
        val recyclerView = MContinuousNestedBottomRecyclerView(context)
        recyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val adapter = TopAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            Toast.makeText(context, "click position=$position", Toast.LENGTH_SHORT).show()
            onDataLoaded(adapter)
        }
        recyclerView.adapter = adapter
        onDataLoaded(adapter)
        return recyclerView
    }

    private fun generateViewPager() : BottomWidgetItemView{
        val viewPager = MViewPager(context)

        val list = mutableListOf<BottomWidgetItemView>()
        list.add(generateComp0())
        list.add(generateComp0())
        viewPager?.adapter = MQMUIPagerAdapter(list)

        return viewPager
    }
}