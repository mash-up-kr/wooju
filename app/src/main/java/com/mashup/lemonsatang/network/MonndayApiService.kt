package com.mashup.lemonsatang.network

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MonndayApiService {

    // article controller
    @GET("/dailyArticle")
    fun getDailyArticle(
        @Query("day") day: Int,
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Call<Article>

    @POST("/dailyArticle")
    fun saveDailyArticle(@Query("articlePostDto") articlePostDto: Article): Call<Unit>

    @PUT("/dailyArticle")
    fun updateDailyArticle(@Query("articleDto") articleDto: Article): Call<Article>

    @DELETE("/dailyArticle")
    fun deleteDailyArticle(@Query("dailyLogId") dailyLogId: Int): Call<Unit>

    // home controller
    @GET("/home")
    fun getHomeData(@Query("year") year: Int): Call<HomeDataResponse>

    // login controller
    @GET("/login")
    fun login(@Query("pushToken") pushToken: String): Call<Unit>

    @GET("/logout")
    fun logout(): Call<Unit>

}