package com.mashup.lemonsatang.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.ui.base.BaseViewHolder
import com.mashup.lemonsatang.databinding.ItemMonthSummaryBinding
import com.mashup.lemonsatang.util.setEmotionApngDrawable

class EntryPointerAdapter(private val clickEvent: (position: Int) -> Unit) :
    RecyclerView.Adapter<EntryPointerAdapter.EntryPointerViewHolder>() {
    private val data = mutableListOf<HomeDataResponse.Year>()

    fun setData(newData: List<HomeDataResponse.Year>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryPointerViewHolder =
        EntryPointerViewHolder(clickEvent, parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: EntryPointerViewHolder, position: Int) =
        holder.bind(data[position])

    class EntryPointerViewHolder(
        private val clickEvent: (position: Int) -> Unit,
        parent: ViewGroup
    ) :
        BaseViewHolder<ItemMonthSummaryBinding>(R.layout.item_month_summary, parent) {

        init {
            binding.ivCalendar.setOnClickListener { clickEvent(adapterPosition) }
        }

        fun bind(item: HomeDataResponse.Year) {
            binding.tvCalendar.text = item.month.toString() + "월"
            binding.ivEmotion.setEmotionApngDrawable(item.mostEmotion)
        }
    }
}