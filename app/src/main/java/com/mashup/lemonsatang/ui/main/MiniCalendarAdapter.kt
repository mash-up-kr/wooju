package com.mashup.lemonsatang.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.databinding.ItemMiniDateBinding
import com.mashup.lemonsatang.databinding.ItemMiniDayBinding
import com.mashup.lemonsatang.ui.base.BaseViewHolder
import com.mashup.lemonsatang.ui.main.model.DateItem
import com.mashup.lemonsatang.ui.main.model.DayItem
import com.mashup.lemonsatang.ui.main.model.HomeItem
import com.mashup.lemonsatang.ui.main.model.HomeItemInfo

class MiniCalendarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = mutableListOf<HomeItem>()

    fun setData(newData: List<HomeItem>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_DATE -> MiniDateViewHolder(parent)
            else -> MiniDayViewHolder(parent)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currItem = data[position]
        when(currItem.viewType){
            VIEW_TYPE_DATE -> (holder as MiniDateViewHolder).bind(currItem.item)
            VIEW_TYPE_DAY -> (holder as MiniDayViewHolder).bind(currItem.item)
        }
    }

    class MiniDateViewHolder(parent: ViewGroup) : BaseViewHolder<ItemMiniDateBinding>(R.layout.item_mini_date, parent) {
        fun bind(item: HomeItemInfo) {
            binding.item = item as DateItem
            binding.executePendingBindings()
        }
    }

    class MiniDayViewHolder(parent: ViewGroup) : BaseViewHolder<ItemMiniDayBinding>(R.layout.item_mini_day, parent) {
        fun bind(item: HomeItemInfo) {
            val currItem = item as DayItem
            when(currItem.emotionId){
                -2 -> binding.viewDay.visibility = View.INVISIBLE
                0 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.pleasure))
                1 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.happiness))
                2 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.tranguility))
                3 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.satisfaction))
                4 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.angro))
                5 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.depressed))
                6 -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.tired))
                else -> binding.viewDay.setCardBackgroundColor(binding.root.context.getColorStateList(R.color.greyscae90))
            }

            binding.executePendingBindings()
        }
    }

    companion object {
        const val VIEW_TYPE_DATE = 0
        const val VIEW_TYPE_DAY = 1
    }
}