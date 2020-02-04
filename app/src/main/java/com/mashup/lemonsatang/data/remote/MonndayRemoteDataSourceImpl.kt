package com.mashup.lemonsatang.data.remote

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.network.MonndayApiService
import retrofit2.Call

class MonndayRemoteDataSourceImpl(private val api: MonndayApiService) : MonndayRemoteDataSource {
    override fun getDailyArticle(day: Int, month: Int, year: Int): Call<Article> =
        api.getDailyArticle(day, month, year)

    override fun saveDailyArticle(articlePostDto: Article): Call<Unit> =
        api.saveDailyArticle(articlePostDto)

    override fun updateDailyArticle(articleDto: Article): Call<Article> =
        api.updateDailyArticle(articleDto)

    override fun deleteDailyArticle(dailyLogId: Int): Call<Unit> =
        api.deleteDailyArticle(dailyLogId)

    override fun getHomeData(year: Int): Call<HomeDataResponse> =
        api.getHomeData(year)

    override fun login(pushToken: String): Call<Unit> =
        api.login(pushToken)

    override fun logout(): Call<Unit> =
        api.logout()

}