package com.mashup.lemonsatang.ui.vo

data class RemindWriteItem (
    val emotionRank : ArrayList<Emotion>,
    var contents : String,
    var Date : String,
    var start : String?

){
    companion object{
        const val first = 1
        const val second = 2
        const val third = 3
    }

    data class Emotion(
        var type : Int,
        var count : String,
        var emotion: Int
    )
}

