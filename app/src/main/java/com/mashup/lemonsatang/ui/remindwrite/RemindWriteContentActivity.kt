package com.mashup.lemonsatang.ui.remindwrite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.ui.remindlist.RemindDate
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity.Companion.REMIND_ID
import kotlinx.android.synthetic.main.activity_remind_write_content.*
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import org.koin.android.ext.android.inject

class RemindWriteContentActivity : AppCompatActivity(), RemindDate{

    private var startDate : String = ""
    private val repository : MonndayRepository by inject()
    private var remindId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind_write_content)
        init()
    }

    private fun init(){
        loadData()
        toolbarClick()
    }


    private fun loadData(){
        remindId = intent.getIntExtra(REMIND_ID, -1)
        repository
            .getRemindDetail(remindId,
                {
                    etRemindWriteContent.setText(it.contents)
                    startDate = it.start
                },
                { Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}
            )
    }

    private fun saveOrUpdateContents(id : Int){
        if(RemindListActivity.isBlank){
            saveContents(id)
        }else{
            updateContents(id)
        }
    }

    private fun saveContents(id : Int){
        repository.saveRemind(etRemindWriteContent.text.toString(),id, null,
            {},
            { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
    }

    private fun updateContents(id : Int){
        repository.updateRemind(etRemindWriteContent.text.toString() ,id, null,
            {},
            { Toast.makeText(this, it, Toast.LENGTH_SHORT).show()})
    }

    private fun toolbarClick(){
        tvCompleteToolbarRemind.visibility = View.VISIBLE
        tvCompleteToolbarRemind.setOnClickListener {
            MaterialDialog(this).show{
                message (text =  getString(R.string.toolbar_remind_dialog_msg, getMonth(startDate),convertKorean(getWeekOfMonth(startDate))) )
                positiveButton (text="확인"){
                    saveOrUpdateContents(remindId)
                    Intent(it.windowContext, RemindWriteActivity::class.java).apply {
                        putExtra(REMIND_ID, remindId)
                        setResult(Activity.RESULT_OK,this )
                        finish()
                    }
                }
            }
        }

        btnBackToolbarRemind.setOnClickListener {
            onBackPressed()
        }
    }

    private fun convertKorean(wom : String) : String{
        wom.let {
            return when(it){
                "1"-> "첫"
                "2"-> "둘"
                "3"-> "셋"
                "4"-> "넷"
                "5"-> "다섯"
                else -> ""
            }
        }
    }
}
