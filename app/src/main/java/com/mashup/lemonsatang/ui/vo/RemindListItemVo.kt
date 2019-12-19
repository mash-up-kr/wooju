package com.mashup.lemonsatang.ui.vo

import com.google.gson.annotations.SerializedName

data class RemindListItemVo(
    var month : String?,
    var date : String?,
    @SerializedName("startDate") var start : String,
    @SerializedName("endDate") var end : String,
    @SerializedName("command")var content : String,
    @SerializedName("bestEmotion") var emotionColor : Int
)