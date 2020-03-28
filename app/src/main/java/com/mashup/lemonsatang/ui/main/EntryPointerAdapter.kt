package com.mashup.lemonsatang.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.databinding.ItemMonthSummaryBinding
import com.mashup.lemonsatang.ui.base.BaseViewHolder
import com.mashup.lemonsatang.util.extension.setEmotionApngDrawable

class EntryPointerAdapter : RecyclerView.Adapter<EntryPointerAdapter.EntryPointerViewHolder>() {
    private val data = mutableListOf<HomeDataResponse.Year>()

    fun setData(newData: List<HomeDataResponse.Year>?) {
        if (newData != null) {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryPointerViewHolder =
        EntryPointerViewHolder(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: EntryPointerViewHolder, position: Int) =
        holder.bind(data[position])

    class EntryPointerViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemMonthSummaryBinding>(R.layout.item_month_summary, parent) {

        fun bind(item: HomeDataResponse.Year) {
            when(item.mostEmotion == null){
                true -> showEmptyView()
                false -> showMostEmotionImage(item.mostEmotion)
            }
        }

        private fun showEmptyView(){
            with(binding){
                tvEmpty.visibility = View.VISIBLE
                ivEmotion.visibility = View.INVISIBLE
            }
        }

        private fun showMostEmotionImage(mostEmotionId : Int){
            with(binding){
                tvEmpty.visibility = View.INVISIBLE
                ivEmotion.visibility = View.VISIBLE
                binding.ivEmotion.setEmotionApngDrawable(mostEmotionId)
            }
        }
    }
}