package com.wj.uidemo.qmui

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.nestedScroll.*
import com.wj.qmuidemo.R
import kotlinx.android.synthetic.main.activity_qmui.*
import java.util.*

/**
 * Des
 * @author WangJian on 2021/8/17 14
 * */
class QMUIActivity : AppCompatActivity() {
    private var mTopDelegateLayout: QMUIContinuousNestedTopDelegateLayout? = null
    private var mTopRecyclerView: QMUIContinuousNestedTopRecyclerView? = null
    private var topAdapter: TopAdapter? = null
    private var bottomView: QMUIBottomView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qmui)
        initTop()
        initBottom()
        initListener()
        onDataLoaded()
    }

    private fun initListener(){
        btn1.setOnClickListener {
            bottomView?.hideSticky()
        }

        btn2.setOnClickListener {
            bottomView?.showSticky()
        }

        btn3.setOnClickListener {
            coordinator.scrollBottomViewToTop()
        }
    }

    private fun initBottom() {
        bottomView = QMUIBottomView(this)
        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val recyclerViewLp: CoordinatorLayout.LayoutParams = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        coordinator.setBottomAreaView(bottomView, recyclerViewLp)

        bottomView?.setBottomActionListener(object : QMUIBottomView.OnBottomActionListener{
            override fun scrollBottomViewToTop() {
                coordinator.scrollBottomViewToTop()
            }
        })
    }

    private fun initTop(){
        mTopDelegateLayout = QMUIContinuousNestedTopDelegateLayout(this)
        mTopRecyclerView = QMUIContinuousNestedTopRecyclerView(this)
        mTopRecyclerView!!.layoutManager = object : LinearLayoutManager(this) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        mTopRecyclerView?.let {
            mTopDelegateLayout?.delegateView = it
        }

        topAdapter = TopAdapter()
        mTopRecyclerView?.adapter = topAdapter

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val topLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(this)
        coordinator.setTopAreaView(mTopDelegateLayout, topLp)
    }

    private fun onDataLoaded() {
        val data: List<String> = ArrayList(Arrays.asList("Helps", "Maintain", "Liver",
                "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally",
                "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet",
                "Bolster", "Pillow", "Cushion"))
        Collections.shuffle(data)
        topAdapter?.replaceData(data)
    }
}