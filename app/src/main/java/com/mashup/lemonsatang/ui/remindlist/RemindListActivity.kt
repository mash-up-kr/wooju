package com.mashup.lemonsatang.ui.remindlist

import android.content.Intent
import android.os.Bundle
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityRemindListBinding
import com.mashup.lemonsatang.ui.dailyview.DailyViewActivity
import com.mashup.lemonsatang.ui.vo.RemindListItemVo

class RemindListActivity : BaseActivity<ActivityRemindListBinding>(R.layout.activity_remind_list) {

    private val remindListAdapter : RemindListAdapter by lazy {
        RemindListAdapter{clickEventCallback()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        loadData()

    }

    private fun clickEventCallback(){
        //remind write 생성후 수정
        startActivity(Intent(this, DailyViewActivity::class.java))
    }

    private fun initRecyclerView() {
        binding.rvRemindList.adapter = remindListAdapter
    }

    private fun loadData(){
        val list : ArrayList<RemindListItemVo> = ArrayList()
        list.add(RemindListItemVo("","","2019-11-04T15:54:07.292Z" ,"2019-11-10T15:54:07.292Z",
            "Mash-Up 사람들 많이 만났다. 다음 주도 다음 달도 계속 많이 만날 예정^_________^", 3))
        list.add(RemindListItemVo("","","2019-11-11T15:54:07.292Z" ,"2019-11-17T15:54:07.292Z",
            "이번주는 정말 나른하게 살았다. 기분은 좋은데 뭔가 안한 것 같아서 찝찝~ 다음 주는 열심히 해야징", 1))
        list.add(RemindListItemVo("","","2019-11-18T15:54:07.292Z" ,"2019-11-24T15:54:07.292Z",
            "돈을 너무 많이 썼다 ^^,,, 다음주는 아낀다. :)", 2))
        list.add(RemindListItemVo("","","2019-11-24T15:54:07.292Z" ,"2019-11-30T15:54:07.292Z",
            "리마인드를 해주세요 :)", 0))
        list.add(RemindListItemVo("","","2019-12-01T15:54:07.292Z" ,"2019-12-07T15:54:07.292Z",
            "리마인드를 해주세요 :)", 0))

        remindListAdapter.setData(list)
    }
}
