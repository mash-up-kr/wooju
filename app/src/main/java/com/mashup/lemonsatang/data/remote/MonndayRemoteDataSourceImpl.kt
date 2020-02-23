package com.mashup.lemonsatang.data.remote

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
import com.mashup.lemonsatang.data.vo.RemindDetailResponse
import com.mashup.lemonsatang.data.vo.RemindListResponse
import com.mashup.lemonsatang.network.MonndayApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MonndayRemoteDataSourceImpl(private val monndayApiService: MonndayApiService) :
    MonndayRemoteDataSource {
    override fun getDailyArticle(
        day: Int,
        month: Int,
        year: Int,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .getDailyArticle(day, month, year)
            .enqueue(object : Callback<Article> {
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Article>, response: Response<Article>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess(it) }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun saveDailyArticle(
        articlePostDto: Article,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .saveDailyArticle(articlePostDto)
            .enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun updateDailyArticle(
        articleDto: Article,
        onSuccess: (articleResponse: Article) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .updateDailyArticle(articleDto)
            .enqueue(object : Callback<Article> {
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Article>, response: Response<Article>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess(it) }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun deleteDailyArticle(
        dailyLogId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .deleteDailyArticle(dailyLogId)
            .enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun getHomeData(
        year: Int,
        onSuccess: (homeDataResponse: HomeDataResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .getHomeData(year)
            .enqueue(object : Callback<HomeDataResponse> {
                override fun onFailure(call: Call<HomeDataResponse>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(
                    call: Call<HomeDataResponse>,
                    response: Response<HomeDataResponse>
                ) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess(it) }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun login(
        pushToken: String,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .login(pushToken)
            .enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun logout(onSuccess: () -> Unit, onFail: (errorMsg: String) -> Unit) {
        monndayApiService
            .logout()
            .enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when (response.isSuccessful) {
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail(response.toString())
                    }
                }
            })
    }

    override fun getRemind(
        onSuccess: (remindListResponse: RemindListResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .getRemind()
            .enqueue(object : Callback<RemindListResponse>{
                override fun onFailure(call: Call<RemindListResponse>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<RemindListResponse>, response: Response<RemindListResponse>) {
                    when(response.isSuccessful){
                        true-> response.body()?.let { onSuccess(it) }
                        false-> onFail(response.errorBody().toString())
                    }
                }
            })
    }

    override fun saveRemind(
        command: String,
        remindId: Int,
        title: String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit) {
        monndayApiService
            .saveRemind(command, remindId, title)
            .enqueue(object : Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when(response.isSuccessful){
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail (response.errorBody().toString())
                    }
                }
            })

    }

    override fun deleteRemind(
        remindId: Int,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .deleteRemind(remindId)
            .enqueue(object : Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when(response.isSuccessful){
                        true -> response.body()?.let { onSuccess() }
                        false -> onFail (response.errorBody().toString())
                    }
                }
            })
    }

    override fun updateRemind(
        command: String,
        remindId: Int,
        title: String?,
        onSuccess: () -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .updateRemind(command, remindId, title)
            .enqueue(object : Callback<Unit>{
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    when(response.isSuccessful){
                        true-> response.body()?.let { onSuccess() }
                        false-> onFail(response.errorBody().toString())
                    }
                }
            })
    }

    override fun getRemindDetail(
        remindId: Int,
        onSuccess: (remindDetailResponse: RemindDetailResponse) -> Unit,
        onFail: (errorMsg: String) -> Unit
    ) {
        monndayApiService
            .getRemindDetail(remindId)
            .enqueue(object : Callback<RemindDetailResponse>{
                override fun onFailure(call: Call<RemindDetailResponse>, t: Throwable) {
                    onFail(t.toString())
                }

                override fun onResponse(
                    call: Call<RemindDetailResponse>,
                    response: Response<RemindDetailResponse>
                ) {
                    when(response.isSuccessful){
                        true-> response.body()?.let { onSuccess(it)  }
                        false-> onFail(response.errorBody().toString())
                    }
                }
            })
    }
}