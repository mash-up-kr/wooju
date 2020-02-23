package com.mashup.lemonsatang.data.remote

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.data.vo.RemindDetailResponse
import com.mashup.lemonsatang.data.vo.RemindListResponse

import retrofit2.Call

interface MonndayRemoteDataSource {

    // article controller
    fun getDailyArticle(
        day: Int,
        month: Int,
        year: Int,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun saveDailyArticle(
        articlePostDto: Article,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun updateDailyArticle(
        articleDto: Article,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun deleteDailyArticle(
        dailyLogId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    // home controller
    fun getHomeData(
        year: Int,
        onSuccess: (homeDataResponse: HomeDataResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    // login controller
    fun login(
        pushToken: String,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun logout(
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    // remind controller

    fun getRemind(
        onSuccess: (remindListResponse : RemindListResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun saveRemind(
        command : String,
        remindId : Int,
        title : String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit

    )

    fun updateRemind(
        command : String,
        remindId : Int,
        title : String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun deleteRemind(
        remindId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    )

    fun getRemindDetail(
        remindId: Int,
        onSuccess: (remindDetailResponse : RemindDetailResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    )
}