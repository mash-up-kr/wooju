package com.mashup.lemonsatang.ui.monthlylist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.databinding.ItemMonthlyListBinding
import com.mashup.lemonsatang.ui.base.BaseViewHolder
import com.mashup.lemonsatang.ui.vo.MonthlyListItemVo

class MonthlyListAdapter(private val clickEvent: (position: Int) -> Unit) :
    RecyclerView.Adapter<MonthlyListAdapter.MonthlyListViewHolder>() {

    private val data = mutableListOf<MonthlyListItemVo>()

    fun setData(newData: List<MonthlyListItemVo>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    fun getDataAt(position: Int) = data[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyListViewHolder =
        MonthlyListViewHolder(clickEvent, parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MonthlyListViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class MonthlyListViewHolder(
        private val clickEvent: (position: Int) -> Unit,
        parent: ViewGroup
    ) : BaseViewHolder<ItemMonthlyListBinding>(R.layout.item_monthly_list, parent) {

        init {
            itemView.setOnClickListener { clickEvent(adapterPosition) }
        }

        fun bind(item: MonthlyListItemVo) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}