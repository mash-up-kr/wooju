package com.mashup.lemonsatang.ui.remindlist

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

    fun setMonth(b : Boolean){
        when(b){
            true -> itemView.tvMonthItemRemindList.visibility = View.VISIBLE
            false-> itemView.tvMonthItemRemindList.visibility = View.GONE
        }
    }

    fun setColor(colorIdx : Int){
        initColorList()

        val color = colorArray.get(colorArray.keyAt(colorIdx))
        itemView.llRemindList.setBackgroundColor(ContextCompat.getColor(itemView.context,color))
    }

    private fun initColorList(){
        colorArray.append(0, R.color.colorPrimaryGray)
        colorArray.append(1, R.color.colorPrimaryBlue)
        colorArray.append(2, R.color.colorPrimaryGreen)
        colorArray.append(3, R.color.colorPrimaryRed)
    }

}