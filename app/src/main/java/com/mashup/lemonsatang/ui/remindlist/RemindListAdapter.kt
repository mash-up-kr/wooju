package com.mashup.lemonsatang.ui.remindlist

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lemonsatang.ui.vo.RemindListItemVo

class RemindListAdapter(private val clickEvent : (position : Int)->Unit)
    : RecyclerView.Adapter<RemindListViewHolder>(), RemindListDate{

    private val data = ArrayList<RemindListItemVo>()
    companion object{
        lateinit var date : String
        lateinit var startDate : String
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindListViewHolder
            = RemindListViewHolder(clickEvent, parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RemindListViewHolder, position: Int) {
        data[position].let {
            //수정해야함
           if(weekOfMonth(it.start) == 1 || weekOfMonth(it.start) == 2 ){
                it.month = getMonth(it.start) + "월"
               holder.setMonth(true)
           }
            startDate = it.start
           it.date = getRemindDate(it.start, it.end)
           holder.setColor(it.emotionColor)
           holder.bind(it)

        }
    }

    fun getDataAt(position: Int) = data[position]

    fun setData(newData : ArrayList<RemindListItemVo>?){
        newData?.let {
            data.clear()
            data.addAll(newData)
            notifyDataSetChanged()
        }
    }
}