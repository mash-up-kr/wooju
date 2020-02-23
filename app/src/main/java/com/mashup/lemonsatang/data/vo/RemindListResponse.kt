package com.mashup.lemonsatang.data.vo

import com.google.gson.annotations.SerializedName

data class RemindListResponse (
    @SerializedName("remind") var remindList : ArrayList<RemindList>
){
    data class RemindList(
        var remindId : Int,
        @SerializedName("startDate") var start : String,
        @SerializedName("endDate") var end : String,
        @SerializedName("command")var contents : String,
        @SerializedName("bestEmotion") var emotionColor : Int?
    )
}
