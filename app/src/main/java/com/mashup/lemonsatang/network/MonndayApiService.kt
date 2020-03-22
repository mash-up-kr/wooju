package com.mashup.lemonsatang.network

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.data.vo.RemindDetailResponse
import com.mashup.lemonsatang.data.vo.RemindListResponse
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
    fun saveDailyArticle(@Body articlePostDto: Article): Call<Unit>

    @PUT("/dailyArticle")
    fun updateDailyArticle(@Body articleDto: Article): Call<Article>

    @DELETE("/dailyArticle/{dailyLogId}")
    fun deleteDailyArticle(@Path("dailyLogId") dailyLogId: Int): Call<Unit>

    // home controller
    @GET("/home")
    fun getHomeData(@Query("year") year: Int): Call<HomeDataResponse>

    // login controller
    @GET("/login")
    fun login(@Query("pushToken") pushToken: String): Call<Unit>

    @GET("/logout")
    fun logout(): Call<Unit>

    // Remind controller
    @GET("/remind")
    fun getRemind() : Call<RemindListResponse>

    @POST("/remind")
    fun saveRemind(
        @Query("command") command : String,
        @Query("remindId") remindId : Int,
        @Query("title") title : String?) : Call<Unit>

    @PUT("/remind")
    fun updateRemind(
        @Query("command") command : String,
        @Query("remindId") remindId : Int,
        @Query("title") title : String?) : Call<Unit>

    @DELETE("/remind")
    fun deleteRemind(
        @Query("remindId") remindId: Int
    ) : Call<Unit>

    @GET("/remind/detail")
    fun getRemindDetail(
        @Query("remindId") remindId: Int
    ) : Call<RemindDetailResponse>


}