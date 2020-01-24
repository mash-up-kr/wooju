package com.mashup.lemonsatang.ui.remindlist

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseViewHolder
import com.mashup.lemonsatang.databinding.ItemRemindListBinding
import com.mashup.lemonsatang.ui.vo.RemindListItemVo
import kotlinx.android.synthetic.main.item_remind_list.view.*

class RemindListViewHolder(
    private val clickEvent: (position: Int) -> Unit, parent: ViewGroup
) : BaseViewHolder<ItemRemindListBinding>(R.layout.item_remind_list, parent) {

    private val colorArray = SparseIntArray()

    init {
        itemView.setOnClickListener{clickEvent(adapterPosition)}
    }

    fun bind(item : RemindListItemVo){
        binding.remindListItem = item
    }

    fun setColor(colorIdx : Int){
        initColorList()

        val color = colorArray.get(colorArray.keyAt(colorIdx))
        val bg = itemView.ivEmotionColorRemindList.background as GradientDrawable
        bg.setColor(ContextCompat.getColor(itemView.context,color))

    }

    private fun initColorList(){
        colorArray.append(0, R.color.pleasure)
        colorArray.append(1, R.color.happiness)
        colorArray.append(2, R.color.tranguility)
        colorArray.append(3, R.color.satisfaction)
        colorArray.append(4, R.color.angro)
        colorArray.append(5, R.color.depressed)
        colorArray.append(6, R.color.tired)
        colorArray.append(7, R.color.sadness)
    }

}