package com.mashup.lemonsatang.data.remote

import com.mashup.lemonsatang.data.vo.Article
import com.mashup.lemonsatang.data.vo.HomeDataResponse
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

}