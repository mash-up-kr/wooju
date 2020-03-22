package com.mashup.lemonsatang.data.vo

data class Article(
    val article: String,
    val dailylogId: Int?,
    val emotion: Int,
    val time: String
)