package com.wj.uidemo.qmui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedTopAreaBehavior
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedTopDelegateLayout
import com.qmuiteam.qmui.span.QMUITouchableSpan
import com.wj.qmuidemo.R
import kotlinx.android.synthetic.main.activity_qmui.*

/**
 * Des
 * @author WangJian on 2021/8/17 14
 * */
class QMUIActivity : AppCompatActivity() {
    private var mTopDelegateLayout: QMUIContinuousNestedTopDelegateLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qmui)
        initTop()
    }

    private fun initTop(){
        mTopDelegateLayout = QMUIContinuousNestedTopDelegateLayout(this)
        val tv = TextView(this)
        tv.text = "这是第一个头部View"
        tv.textSize = 18f
        tv.setBackgroundResource(R.color.qmui_config_color_50_blue)
        mTopDelegateLayout?.addView(tv, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100))

        val tvBottom = TextView(this)
        tvBottom.text = "这是第一个底部View"
        tvBottom.textSize = 18f
        tv.setBackgroundResource(R.color.qmui_config_color_gray_1)
        mTopDelegateLayout?.addView(tvBottom, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100))

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val topLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(this)
        coordinator.setTopAreaView(mTopDelegateLayout, topLp)
    }
}