package com.mashup.lemonsatang.ui.remindwrite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.lemonsatang.R
import com.mashup.lemonsatang.data.MonndayRepository
import com.mashup.lemonsatang.data.vo.RemindDetailResponse
import com.mashup.lemonsatang.ui.base.BaseActivity
import com.mashup.lemonsatang.databinding.ActivityRemindWriteBinding
import com.mashup.lemonsatang.ui.remindlist.RemindDate
import com.mashup.lemonsatang.ui.remindlist.RemindListActivity
import com.mashup.lemonsatang.ui.vo.*
import kotlinx.android.synthetic.main.activity_remind_write.*
import kotlinx.android.synthetic.main.bottom_sheet_remind_write.*
import kotlinx.android.synthetic.main.toolbar_remind_list.*
import org.koin.android.ext.android.inject
import retrofit2.Call

class RemindWriteActivity : BaseActivity<ActivityRemindWriteBinding>(R.layout.activity_remind_write),
    RemindDate {

    private val remindWriteAdapter : RemindWriteAdapter by lazy {
        RemindWriteAdapter()
    }
    private val emotionItem: ArrayList<RemindWriteItem.Emotion>  by lazy {
        ArrayList<RemindWriteItem.Emotion>()
    }
    private lateinit var er : ArrayList<RemindDetailResponse.EmotionRank>
    private var remindId : Int = -1
    private val repository : MonndayRepository by inject()
    private val call : Call<RemindDetailResponse>? = null

    companion object{
        const val REMIND_WRITE_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        loadData()
    }

    override fun onStop() {
        super.onStop()
        call?.run { cancel() }
    }

    private fun init(){
        initRecyclerView()
        intentConfig()
        btnBackClick()
    }

    private fun intentConfig(){
        remindId = intent.getIntExtra(RemindListActivity.REMIND_ID, -1)
    }

    private fun initRecyclerView(){
        binding.rvRemindWrite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRemindWrite.adapter = remindWriteAdapter

    }

    private fun remindWriteConfig(it : RemindDetailResponse){
        setToolbar(it)
        setEtContent(it)
        setDate(it)
    }

    private fun setToolbar(it : RemindDetailResponse){
        if(it.contents.isNullOrBlank()){
            disableBtnEditToolbar()
        }else{
            enableBtnEditToolbar()
        }
    }

    private fun setDate(it : RemindDetailResponse){
        tvDateRemindWrite.text= getRemindDate(it.start, it.end)
    }

    private fun setEtContent(it : RemindDetailResponse){
        if(it.contents.isNullOrBlank()){
            etContentRemindWrite.text = null
            enableEtContentClick()
            etContentClick()

        }
        else{
            etContentRemindWrite.setText(it.contents)
            disableEtContentClick()
        }
    }

    private fun btnBackClick() = btnBackToolbarRemind.setOnClickListener {
        Intent(this, RemindListActivity::class.java).apply {
            setResult(Activity.RESULT_OK,this)
            finish()
        }
    }

    private fun enableBtnEditToolbar(){
        btnEditToolbarRemind.visibility = View.VISIBLE
        btnEditToolbarRemind.setOnClickListener {
            showBottomDialog()
        }
    }

    private fun disableBtnEditToolbar(){
        btnEditToolbarRemind.visibility = View.GONE
    }

    private fun enableEtContentClick(){
        etContentRemindWrite.isEnabled = true
    }

    private fun disableEtContentClick(){
        etContentRemindWrite.isEnabled = false
    }

    private fun etContentClick(){
        etContentRemindWrite.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Intent(this, RemindWriteContentActivity::class.java).apply {
                    putExtra(RemindListActivity.REMIND_ID, remindId)
                    startActivityForResult(this, REMIND_WRITE_REQUEST)
                }
                overridePendingTransition(
                    R.anim.slide_bottom_in,
                    R.anim.slide_top_out
                )
                etContentRemindWrite.clearFocus()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REMIND_WRITE_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                intentConfig()
                loadData()
            }
        }
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
            Intent(this, RemindWriteContentActivity::class.java).apply {
                putExtra(RemindListActivity.REMIND_ID, remindId)
                startActivityForResult(this, REMIND_WRITE_REQUEST)
                dialog.hide()
            }
        }
        btnDelete.setOnClickListener {
            showDeleteDialog(dialog)
        }
    }

    private fun loadData(){
        repository
            .getRemindDetail(remindId,
                {
                    er = it.emotionRank
                    setAdapterData()
                    remindWriteConfig(it)
                },
                { Toast.makeText(this, it, Toast.LENGTH_SHORT).show()}
            )
    }

    private fun clearItem() = emotionItem.clear()

    private fun setAdapterData(){
        clearItem()
        setEmotionRank()

        for(i in 0 until er.size){
            when(er[i].rank){
                -1-> { }
                1-> addEmotionItem(i, 1)
                2-> addEmotionItem(i, 2)
                else -> addEmotionItem(i, 3)
            }
        }
        remindWriteAdapter.setData(emotionItem)
    }

    private fun addEmotionItem(i : Int, type : Int) =
        er[i].let{
            emotionItem.add(RemindWriteItem.Emotion(type, getString(R.string.remind_write_emotion_count, it.count), it.emotion) )
        }


    private fun setEmotionRank(){
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
            if(sorted[i].count == 0) sorted[i].rank = -1
        }

        er.clear()
        er.addAll(sorted)
    }

    //삭제
    private fun deleteContents(){
        repository
            .deleteRemind(
                remindId,
                {},
                {Toast.makeText(this, it, Toast.LENGTH_SHORT).show()})
    }

    private fun showDeleteDialog(dialog: BottomSheetDialog){
        MaterialDialog(this).show{
            message (text = "삭제하시겠습니까?")
            negativeButton (text = "취소")
            positiveButton (text = "확인"){
                deleteContents()
                dialog.dismiss()
                Intent(it.windowContext, RemindListActivity::class.java).apply {
                    setResult(Activity.RESULT_OK,this)
                    finish()
                }
            }
        }
    }
}
