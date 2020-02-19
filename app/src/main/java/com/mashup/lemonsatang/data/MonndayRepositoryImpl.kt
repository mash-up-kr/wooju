package com.mashup.lemonsatang.data

import com.mashup.lemonsatang.data.remote.MonndayRemoteDataSource
import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.data.vo.RemindDetailResponse
import com.mashup.lemonsatang.data.vo.RemindListResponse
import retrofit2.Call

class MonndayRepositoryImpl(private val remoteDataSource: MonndayRemoteDataSource) :
    MonndayRepository {
    override fun getDailyArticle(
        day: Int,
        month: Int,
        year: Int,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.getDailyArticle(day, month, year, onSuccess, onFail)
    }

    override fun saveDailyArticle(
        articlePostDto: Article,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.saveDailyArticle(articlePostDto, onSuccess, onFail)
    }

    override fun updateDailyArticle(
        articleDto: Article,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.updateDailyArticle(articleDto, onSuccess, onFail)
    }

    override fun deleteDailyArticle(
        dailyLogId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.deleteDailyArticle(dailyLogId, onSuccess, onFail)
    }

    override fun getHomeData(
        year: Int,
        onSuccess: (homeDataResponse: HomeDataResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.getHomeData(year, onSuccess, onFail)
    }

    override fun login(
        pushToken: String,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.login(pushToken, onSuccess, onFail)
    }

    override fun logout(onSuccess: () -> Unit, onFail: (errorMsg: String) -> Unit) {
        remoteDataSource.logout(onSuccess, onFail)
    }

    override fun getRemind(
        onSuccess: (remindListResponse: RemindListResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit) {
        remoteDataSource.getRemind(onSuccess, onFail)
    }

    override fun saveRemind(
        command: String,
        remindId: Int,
        title: String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.saveRemind(command, remindId, title,onSuccess, onFail)
    }

    override fun updateRemind(
        command: String,
        remindId: Int,
        title: String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.updateRemind(command, remindId, title, onSuccess, onFail)
    }

    override fun deleteRemind(
        remindId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.deleteRemind(remindId, onSuccess, onFail)
    }

    override fun getRemindDetail(
        remindId: Int,
        onSuccess: (remindDetailResponse: RemindDetailResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        remoteDataSource.getRemindDetail(remindId, onSuccess, onFail)
    }
}