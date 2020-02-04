package com.mashup.lemonsatang.data

import com.mashup.lemonsatang.data.remote.MonndayRemoteDataSource
import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import retrofit2.Call

class MonndayRepositoryImpl(private val remoteDataSource: MonndayRemoteDataSource) : MonndayRepository{
    override fun getDailyArticle(day: Int, month: Int, year: Int): Call<Article> =
        remoteDataSource.getDailyArticle(day, month, year)

    override fun saveDailyArticle(articlePostDto: Article): Call<Unit> =
        remoteDataSource.saveDailyArticle(articlePostDto)

    override fun updateDailyArticle(articleDto: Article): Call<Article> =
        remoteDataSource.updateDailyArticle(articleDto)

    override fun deleteDailyArticle(dailyLogId: Int): Call<Unit> =
        remoteDataSource.deleteDailyArticle(dailyLogId)

    override fun getHomeData(year: Int): Call<HomeDataResponse> =
        remoteDataSource.getHomeData(year)

    override fun login(pushToken: String): Call<Unit> =
        remoteDataSource.login(pushToken)

    override fun logout(): Call<Unit> =
        remoteDataSource.logout()
}