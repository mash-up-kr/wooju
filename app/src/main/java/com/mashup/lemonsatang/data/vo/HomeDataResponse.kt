package com.mashup.lemonsatang.data.vo

data class HomeDataResponse(
    val year: List<Year>
) {
    data class Year(
        val emotionList: List<Emotion>,
        val month: Int,
        val mostEmotion: Int
    )

    data class Emotion(
        val day: Int,
        val emotion: Int
    )
}