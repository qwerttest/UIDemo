package com.wj.uidemo.qmui

import android.content.Context
import android.util.AttributeSet
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView

/**
 * Des 继承QMUIContinuousNestedBottomRecyclerView，可添加自定义实现
 * @author WangJian on 2021/9/3 10
 * */
class MContinuousNestedBottomRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : QMUIContinuousNestedBottomRecyclerView(context, attrs, defStyle), BottomWidgetItemView