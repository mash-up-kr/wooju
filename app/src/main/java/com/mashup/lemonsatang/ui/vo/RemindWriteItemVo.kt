package com.mashup.lemonsatang.ui.vo

import com.google.gson.annotations.SerializedName

data class RemindWriteItemVo (
    var type : Int,
    var count : String,
    var emotion: Int
){
    companion object{
        const val first = 1
        const val second = 2
        const val third = 3
    }
}

data class EmotionRank(
    var count : Int,
    var emotion : Int,
    var rank : Int?
)

data class RemindWriteResponse(
    var emotionRank : ArrayList<EmotionRank>
)