package com.wj.uidemo.qmui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.wj.qmuidemo.R
import kotlinx.android.synthetic.main.activity_qmui_selectview.view.*
import java.util.*

/**
 * Des 选择列表
 * @author WangJian on 2021/9/1 11
 * */
class SelectView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        setBackgroundColor(0x99000000.toInt())
        LayoutInflater.from(context).inflate(R.layout.activity_qmui_selectview, this, true)
        selectViewRv.layoutManager = LinearLayoutManager(context)
        val adapter = TopAdapter()
        selectViewRv.adapter = adapter

        adapter.setOnItemClickListener { _, _, position -> Toast.makeText(context, "您选择了$position", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        onDataLoaded(adapter)

        setOnClickListener {
            dismiss()
        }
    }

    private fun onDataLoaded(adapter: TopAdapter) {
        val data: List<String> = ArrayList(Arrays.asList("第一节课", "第二节课", "第三节课",
                "第4节课", "第5节课", "第6节课", "第7节课", "第8节课", "第9节课", "第10节课",
                "第11节课", "第12节课", "第13节课", "第14节课", "第15节课", "第16节课", "第17节课",
                "第18节课", "第19节课", "第20节课"))
        adapter?.replaceData(data)
    }

    fun show(){
        visibility = VISIBLE
    }

    fun dismiss(){
        visibility = GONE
    }
}