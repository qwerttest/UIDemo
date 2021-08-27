package com.wj.uidemo.qmui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wj.qmuidemo.R

/**
 * Des
 * @author WangJian on 2021/8/24 15
 * */
class TopAdapter() : BaseQuickAdapter<String, BaseViewHolder>(R.layout.activity_qmui_item_top) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv, item)
    }
}