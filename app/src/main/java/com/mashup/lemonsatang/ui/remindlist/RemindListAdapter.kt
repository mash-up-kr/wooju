package com.mashup.lemonsatang.ui.remindlist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseViewHolder
import com.mashup.lemonsatang.databinding.ItemBlankRemindListBinding
import com.mashup.lemonsatang.databinding.ItemMonthRemindListBinding
import com.mashup.lemonsatang.ui.vo.RemindListItemVo
import java.lang.RuntimeException

class RemindListAdapter(private val clickEvent : (position : Int)->Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), RemindDate{

    private val data = ArrayList<RemindListItemVo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when(viewType){
            RemindListItemVo.itemType-> RemindListViewHolder(clickEvent, parent)

            RemindListItemVo.monthType-> RemindListMonthViewHolder(parent)

            RemindListItemVo.blankType-> RemindListBlankViewHolder(clickEvent,parent)

            else-> throw RuntimeException("알 수 없는 뷰타입 에러")
        }
    }


    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int) = data[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when(it.type){
                RemindListItemVo.itemType->{
                    holder as RemindListViewHolder
                    it.emotionColor?.let { it1 -> holder.setColor(it1) }
                    holder.bind(it)
                }

                RemindListItemVo.monthType->{
                    holder as RemindListMonthViewHolder
                    holder.bind(it) }

                RemindListItemVo.blankType->{
                    holder as RemindListBlankViewHolder
                    holder.bind(it)
                }
           }
        }
    }

    fun setData(newData : ArrayList<RemindListItemVo>?){
        newData?.let {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    fun getDataAt(position: Int) = data[position]

    class RemindListMonthViewHolder(parent: ViewGroup) : BaseViewHolder<ItemMonthRemindListBinding>(
        R.layout.item_month_remind_list, parent) {

        fun bind(item : RemindListItemVo) {
            binding.remindListItemMonth = item
        }
    }

    class RemindListBlankViewHolder(
        private val clickEvent: (position: Int) -> Unit, parent:ViewGroup
    ) : BaseViewHolder<ItemBlankRemindListBinding>(R.layout.item_blank_remind_list, parent) {
        init {
            itemView.setOnClickListener { clickEvent(adapterPosition) }
        }

        fun bind(item: RemindListItemVo){
            binding.remindListItemBlank = item
        }

    }
}