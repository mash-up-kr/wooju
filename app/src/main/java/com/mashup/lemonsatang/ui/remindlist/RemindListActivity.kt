package com.mashup.lemonsatang.ui.remindlist

import android.content.Intent
import android.os.Bundle
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityRemindListBinding
import com.mashup.lemonsatang.ui.remindwrite.RemindWriteActivity
import com.mashup.lemonsatang.ui.vo.RemindList
import com.mashup.lemonsatang.ui.vo.RemindListItemVo
import kotlinx.android.synthetic.main.toolbar_remind_list.*

class RemindListActivity : BaseActivity<ActivityRemindListBinding>(R.layout.activity_remind_list)
                        ,RemindDate{

    private val remindListListAdapter : RemindListAdapter by lazy {
        RemindListAdapter{clickEventCallback(it)}
    }
//    private val response: RemindListResponse by lazy{
//        RemindListResponse(remindList)
//    }
    private val item: ArrayList<RemindListItemVo>  by lazy {
        ArrayList<RemindListItemVo>()
    }
    private val remindList : ArrayList<RemindList> by lazy{
        ArrayList<RemindList>()
    }
    private var type = 0

    companion object{
        lateinit var date : String
        lateinit var startDate : String
        lateinit var content : String
        var isBlank : Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnBackClick()
        initRecyclerView()
        loadData()
    }

    private fun clickEventCallback(position : Int){
        val data = remindListListAdapter.getDataAt(position)

        initCompanionObject(data)

        startActivity(Intent(this, RemindWriteActivity::class.java))
    }

    private fun initRecyclerView() {
        binding.rvRemindList.adapter = remindListListAdapter
    }

    private fun initCompanionObject(data : RemindListItemVo) {
        startDate = data.start.toString()
        date = data.date.toString()
        content = data.contents.toString()
        isBlank = content == getString(R.string.remind_write_content)
    }

    private fun btnBackClick() = btnBackToolbarRemind.setOnClickListener {
        onBackPressed()
    }
    private fun loadData() {
        addData()

        for (i in 0 until remindList.size) {
            val cur = getMonth(remindList[i].start)
            var next = ""

            if(i < remindList.size-1){
                next = getMonth(remindList[i + 1].start)
            }

            setType(i)
            when{
                i == 0 ->{
                    addMonth(i)
                    responseToItem(i)
                }
                cur < next -> {
                    responseToItem(i)
                    addMonth(i)
                }
                else -> {
                    responseToItem(i)
                }
            }

        }
        remindListListAdapter.setData(item)
    }

    private fun responseToItem(i : Int){
        remindList[i].let{
            item.add(RemindListItemVo(type, getMonth(it.start),getRemindDate(it.start, it.end),
                it.contents, it.emotionColor, it.start))
        }
    }

    private fun addMonth(i : Int)=item.add(RemindListItemVo(1,getMonth(remindList[i+1].start),null, null, null, null))


    private fun setType(i: Int){
        if(remindList[i].contents.isBlank()) {
            type = 2
            remindList[i].contents = getString(R.string.remind_write_content)
        } else type = 0

    }

    private fun addData(){
        remindList.add(
            RemindList(
                "2019-11-04T15:54:07.292Z", "2019-11-10T15:54:07.292Z",
                "Mash-Up 사람들 많이 만났다. 다음 주도 다음 달도 계속 많이 만날 예정^_________^", 3
            )
        )
        remindList.add(
            RemindList(
                "2019-11-11T15:54:07.292Z", "2019-11-17T15:54:07.292Z",
                "이번주는 정말 나른하게 살았다. 기분은 좋은데 뭔가 안한 것 같아서 찝찝~ 다음 주는 열심히 해야징", 2
            )
        )
        remindList.add(
            RemindList(
                "2019-11-18T15:54:07.292Z", "2019-11-23T15:54:07.292Z",
                "열심히 살자!", 1
            )
        )
        remindList.add(
            RemindList(
                "2019-11-24T15:54:07.292Z", "2019-11-30T15:54:07.292Z",
                "", null
            )
        )

        remindList.add(
            RemindList(
                "2019-12-01T15:54:07.292Z", "2019-12-07T15:54:07.292Z",
                "", null
            )
        )

    }
}

