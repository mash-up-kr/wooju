package com.mashup.lemonsatang.ui.remindwrite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.ui.base.BaseViewHolder
import com.mashup.lemonsatang.databinding.ItemFirstRemindWriteBinding
import com.mashup.lemonsatang.databinding.ItemSecondRemindWriteBinding
import com.mashup.lemonsatang.databinding.ItemThirdRemindWriteBinding
import java.lang.RuntimeException
import com.mashup.lemonsatang.ui.vo.RemindWriteItem

class RemindWriteAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = ArrayList<RemindWriteItem.Emotion>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RemindWriteItem.first-> RemindWriteFirstViewHolder(parent)
            RemindWriteItem.second-> RemindWriteSecondViewHolder(parent)
            RemindWriteItem.third-> RemindWriteThirdViewHolder(parent)
            else -> throw RuntimeException("알 수 없는 뷰타입 에러")
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int) : Int = data[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data[position].let {
            when(it.type){
                RemindWriteItem.first->{
                    holder as RemindWriteFirstViewHolder
                    holder.bind(it)
                }
                RemindWriteItem.second->{
                    holder as RemindWriteSecondViewHolder
                    holder.bind(it)
                }
                RemindWriteItem.third->{
                    holder as RemindWriteThirdViewHolder
                    holder.bind(it)
                }
            }
        }
    }

    fun setData(newData : ArrayList<RemindWriteItem.Emotion>?){
        newData?.let {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }

    class RemindWriteFirstViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemFirstRemindWriteBinding>(R.layout.item_first_remind_write, parent){

        fun bind(item : RemindWriteItem.Emotion){
            binding.remindWriteItemFirst = item
        }
    }

    class RemindWriteSecondViewHolder(parent: ViewGroup):
        BaseViewHolder<ItemSecondRemindWriteBinding>(R.layout.item_second_remind_write, parent){

        fun bind(item: RemindWriteItem.Emotion){
            binding.remindWriteItemSecond = item
        }
    }

    class RemindWriteThirdViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemThirdRemindWriteBinding>(R.layout.item_third_remind_write, parent){

        fun bind(item: RemindWriteItem.Emotion){
            binding.remindWriteItemThird = item
        }
    }
}