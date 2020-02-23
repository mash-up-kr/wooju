package com.mashup.lemonsatang.ui.vo

data class RemindListItem(
    var remindId : Int?,
    var type : Int,
    var month : String,
    var date : String?,
    var contents : String?,
    var emotionColor : Int?,
    var start : String?
)
{
    companion object{
        const val itemType=0
        const val monthType=1
        const val blankType=2
    }
}
