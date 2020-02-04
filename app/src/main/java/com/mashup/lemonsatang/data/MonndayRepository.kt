package com.mashup.lemonsatang.data

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import retrofit2.Call

interface MonndayRepository {

    // remote

    // article controller
    fun getDailyArticle(day: Int, month: Int, year: Int): Call<Article>

    fun saveDailyArticle(articlePostDto: Article): Call<Unit>

    fun updateDailyArticle(articleDto: Article): Call<Article>

    fun deleteDailyArticle(dailyLogId: Int): Call<Unit>

    // home controller
    fun getHomeData(year: Int): Call<HomeDataResponse>

    // login controller
    fun login(pushToken: String): Call<Unit>

    fun logout(): Call<Unit>
}