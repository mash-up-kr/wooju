package com.mashup.lemonsatang.ui.remindlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.RemindListResponse
import com.mashup.lemonsatang.data.vo.RemindListResponse.RemindList
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityRemindListBinding
import com.mashup.lemonsatang.ui.main.MainActivity
import com.mashup.lemonsatang.ui.remindwrite.RemindWriteActivity
import com.mashup.lemonsatang.ui.vo.RemindListItem
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import org.koin.android.ext.android.inject
import retrofit2.Call

class RemindListActivity : BaseActivity<ActivityRemindListBinding>(R.layout.activity_remind_list)
    ,RemindDate{

    private val remindListListAdapter : RemindListAdapter by lazy {
        RemindListAdapter{clickEventCallback(it)}
    }
    private val item: ArrayList<RemindListItem>  by lazy {
        ArrayList<RemindListItem>()
    }
    private val call : Call<RemindListResponse>? = null
    private val repository : MonndayRepository by inject()
    private var type = 0

    companion object{
        const val REMIND_ID = "REMIND_ID"
        const val START_DATE = "START_DATE"
        const val REMIND_LIST_REQUSET = 200

        var isBlank : Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnBackClick()
        initRecyclerView()
        loadData()
    }

    override fun onStop() {
        super.onStop()
        call?.run { cancel() }
    }

    private fun clickEventCallback(position : Int){
        val data = remindListListAdapter.getDataAt(position)
        setIsBlank(data)

        Intent(this, RemindWriteActivity::class.java).apply {
            putExtra(REMIND_ID, data.remindId)
            putExtra(START_DATE, data.start)
            startActivityForResult(this, REMIND_LIST_REQUSET)
        }
    }

    private fun initRecyclerView() {
        binding.rvRemindList.adapter = remindListListAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REMIND_LIST_REQUSET){
            if(resultCode == Activity.RESULT_OK){
                loadData()
            }
        }
    }

    private fun setIsBlank(remindListItem: RemindListItem){
        isBlank = remindListItem.contents == getString(R.string.remind_write_content)
    }

    private fun btnBackClick() = btnBackToolbarRemind.setOnClickListener {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun loadData(){
        repository
            .getRemind(
                {
                    setAdapterData(it.remindList)},
                {Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}
            )
    }

    private fun setAdapterData(it : ArrayList<RemindList>){
        clearItem()
        for (i in 0 until it.size) {
            if(i < it.size-1){
                setType(it,i)
                when{
                    i == 0 ->{
                        addMonth(it,i)
                        responseToItem(it,i)
                    }
                    getMonth(it[i].start) != getMonth(it[i + 1].start) -> {
                        responseToItem(it,i)
                        addMonth(it,i)
                    }
                    else -> {
                        responseToItem(it,i)
                    }
                }
            }
        }
        remindListListAdapter.setData(item)
    }


    private fun addMonth(it : ArrayList<RemindList>,i : Int)=
        item.add(RemindListItem(null,1,getMonth(it[i+1].start),null, null, null, null))

    private fun setType(it : ArrayList<RemindList>, i: Int){
        if(it[i].contents.isNullOrBlank()) {
            type = 2
            it[i].contents = getString(R.string.remind_write_content)
        } else type = 0

    }

    private fun responseToItem(remindList : ArrayList<RemindList>, i : Int){
        remindList[i].let{
            item.add(RemindListItem(it.remindId, type, getMonth(it.start),getRemindDate(it.start, it.end),
                it.contents, it.emotionColor, it.start))
        }
    }

    private fun clearItem(){
        item.clear()
    }

}
