package com.mashup.lemonsatang.ui.vo

import com.google.gson.annotations.SerializedName

data class RemindListItemVo(
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

data class RemindListResponse(
    @SerializedName("remind") var remindList : ArrayList<RemindList>
)

data class RemindList(
    @SerializedName("startDate") var start : String,
    @SerializedName("endDate") var end : String,
    @SerializedName("command")var contents : String,
    @SerializedName("bestEmotion") var emotionColor : Int?
)