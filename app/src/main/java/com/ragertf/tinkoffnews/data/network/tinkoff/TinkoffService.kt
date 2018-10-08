package com.ragertf.tinkoffnews.data.network.tinkoff

import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TinkoffService {

    @GET("news")
    fun getTitleList(): Single<Response<List<Title>>>

    @GET("news_content")
    fun getNews(@Query("id") newsId: Int): Single<Response<News>>
}