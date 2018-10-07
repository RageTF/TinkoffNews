package com.ragertf.tinkoffnews.data.network.tinkoff.impls

import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.ItemNotFoundException
import com.ragertf.tinkoffnews.data.NetworkException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.network.tinkoff.Response
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi.Companion.OK
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.io.IOException

class TinkoffApiImpl(private val requests: TinkoffService) : TinkoffApi {

    private fun <T> Single<Response<T>>.payload(defaultValueForPayload: T? = null): Single<T> {
        return onErrorResumeNext {
            if (it is IOException) {
                Single.error(InternetConnectionException("Server not responding",it))
            } else {
                Single.error(NetworkException(it))
            }
        }.map {
            if (it.resultCode == OK) {
                it.payload ?: defaultValueForPayload
                ?: throw ItemNotFoundException("Payload is null")
            } else {
                throw ServerRespondingException("Result code != OK")
            }
        }
    }

    override fun getTitleList(): Single<List<Title>> = requests.getTitleList().payload(emptyList())

    override fun getNews(newsId: Int): Single<News> = requests.getNews(newsId).payload()

}