package com.mashup.lemonsatang.ui.remindWrite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityRemindWriteBinding
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity
import com.mashup.lemonsatang.ui.vo.*
import kotlinx.android.synthetic.main.activity_remind_write.*
import kotlinx.android.synthetic.main.bottom_sheet_remind_write.*
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

class RemindWriteActivity : BaseActivity<ActivityRemindWriteBinding>(R.layout.activity_remind_write) {

    private val remindWriteAdapter : RemindWriteAdapter by lazy {
        RemindWriteAdapter()
    }
//    private val response: RemindWriteResponse by lazy {
//        RemindWriteResponse(er)
//    }
    private val item: ArrayList<RemindWriteItemVo>  by lazy {
        ArrayList<RemindWriteItemVo>()
    }
    private val er : ArrayList<EmotionRank> by lazy{
       ArrayList<EmotionRank>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        loadData()
        init()
    }

    private fun init(){
        tvDateRemindWrite.text= RemindListActivity.date
        etContentRemindWrite.setText(RemindListActivity.content)
        setToolbar()
        btnBackClick()
    }

    private fun initRecyclerView(){
        binding.rvRemindWrite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRemindWrite.adapter = remindWriteAdapter

    }

    private fun setToolbar(){
        if(RemindListActivity.isBlank){
            etContentClick()
        }else{
            btnEditToolbarRemind.visibility = View.VISIBLE
            btnEditToolbarRemind.setOnClickListener {
                showBottomDialog()
            }
            etContentRemindWrite.isClickable = false
            etContentRemindWrite.isFocusable =false
        }
    }

    private fun loadData(){
        addData()

        for(i in 0 until er.size){
            when(er[i].rank){
                1-> addItem(i, 1)
                2-> addItem(i, 2)
                else -> addItem(i, 3)
            }
        }
        remindWriteAdapter.setData(item)
    }

    private fun addItem(i : Int, type : Int){
        er[i].let{
            item.add(RemindWriteItemVo(type, getString(R.string.remind_write_emotion_count, it.count), it.emotion))
        }
    }

    private fun etContentClick(){
        etContentRemindWrite.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this, RemindWriteContentActivity::class.java)
                startActivity(intent)
                overridePendingTransition(
                    R.anim.slide_bottom_in,
                    R.anim.slide_top_out
                )
                etContentRemindWrite.clearFocus()
            }
        }
    }

    private fun btnBackClick() = btnBackToolbarRemind.setOnClickListener {
        onBackPressed()
    }

    private fun showBottomDialog(){
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_remind_write, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        bottomDialogClick(dialog)
        dialog.show()
    }

    private fun bottomDialogClick(dialog: BottomSheetDialog){
        val btnEdit = dialog.btnEditRemindWrite
        val btnDelete = dialog.btnDeleteRemindWrite

        btnEdit.setOnClickListener {
            toast("수정동작")
        }
        btnDelete.setOnClickListener {
            showDeleteDialog(dialog)
        }
    }

    private fun showDeleteDialog(dialog: BottomSheetDialog){
        MaterialDialog(this).show{
            message (text = "삭제하시겠습니까?")
            negativeButton (text = "취소")
            positiveButton (text = "확인"){
                dialog.dismiss()
                finish()
            }
        }
    }

    //서버 연동후에 수정
    private fun addData(){
        er.add(EmotionRank(1, 5, null))
        er.add(EmotionRank(4,1, null))
        er.add(EmotionRank(2,4, null))
        er.add(EmotionRank(4,2, null))
        er.add(EmotionRank(1,6, null))
        er.add(EmotionRank(3,3, null))
        er.add(EmotionRank(1,7, null))

        //정렬
        val sorted = er.sortedWith(compareByDescending { it.count})
        sorted[0].rank = 1
        //rank 설정
        for (i in 0 .. sorted.lastIndex){
            if(i < sorted.lastIndex){
                if(sorted[i].count == sorted[i+1].count) sorted[i+1].rank = sorted[i].rank
                else sorted[i+1].rank = sorted[i].rank?.plus(1)
            }else {
                if(sorted[i].count == sorted[i-1].count) sorted[i].rank = sorted[i-1].rank
                else sorted[i].rank = sorted[i-1].rank?.minus(1)
            }
        }

        er.clear()
        er.addAll(sorted)
    }

}
