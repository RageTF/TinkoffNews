package com.ragertf.tinkoffnews.data.network.tinkoff

import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 *  Запрашивает данные из сети
 */
interface TinkoffApi {
    companion object {
        const val BASE_URL = "https://api.tinkoff.ru/v1/"
        /**
         * resultCode Запрос выполнен успешно
         */
        const val OK = "OK"
        const val FAIL = "FAIL"
    }

    fun getTitleList(): Single<List<Title>>

    /**
     *  @param newsId Идентификатор новости
     */
    fun getNews(newsId: Int): Single<News>
}