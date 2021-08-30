package com.wj.uidemo.qmui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomDelegateLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.wj.qmuidemo.R
import java.util.*

/**
 * Des
 * @author WangJian on 2021/8/27 17
 * */
class QMUIBottomView @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : QMUIContinuousNestedBottomDelegateLayout(context, attrs, defStyleAttr) {

    private var bottomRecyclerView: QMUIContinuousNestedBottomRecyclerView? = null
    private var adapter: TopAdapter? = null
    private var stickyHeight: Int = QMUIDisplayHelper.dp2px(context, 50)
    private var tab2: TextView? = null

    override fun onCreateHeaderView(): View {
        return LayoutInflater.from(context).inflate(R.layout.activity_qmui_headerview, null)
    }

    override fun onCreateContentView(): View {
        bottomRecyclerView = QMUIContinuousNestedBottomRecyclerView(context)
        return bottomRecyclerView!!
    }

    override fun getHeaderStickyHeight(): Int {
        return stickyHeight
    }

    override fun getHeaderHeightLayoutParam(): Int {
        return QMUIDisplayHelper.dp2px(context, 50)
    }

    fun loadData(){
        bottomRecyclerView?.let {
            it.layoutManager = LinearLayoutManager(context)
            adapter = TopAdapter()
            it.adapter = adapter
        }
        onDataLoaded()
        tab2 = headerView.findViewById(R.id.tab2)
    }

    private fun onDataLoaded() {
        val data: List<String> = ArrayList(Arrays.asList("Helps", "Maintain", "Liver",
                "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally",
                "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet",
                "Bolster", "Pillow", "Cushion"))
        Collections.shuffle(data)
        adapter?.replaceData(data)
    }

    fun hideSticky(){
        stickyHeight = 0
        tab2?.visibility = GONE
    }

    fun showSticky(){
        stickyHeight = QMUIDisplayHelper.dp2px(context, 50)
        tab2?.visibility = VISIBLE
    }
}