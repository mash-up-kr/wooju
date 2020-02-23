package com.mashup.lemonsatang.data.vo

import com.google.gson.annotations.SerializedName

data class RemindDetailResponse(
    val emotionRank : ArrayList<EmotionRank>,
    @SerializedName("command")var contents : String,
    @SerializedName("startDate") var start : String,
    @SerializedName("endDate") var end : String

){
    data class EmotionRank(
        var count : Int,
        var emotion : Int,
        var rank : Int?
    )
}