package com.ragertf.tinkoffnews.data.dao.impls

import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import io.reactivex.Single

class NewsDaoImpl(private val tinkoffApi: TinkoffApi, private val newsCache: NewsCache) : NewsDao {

    override fun getNewsByIdFromCacheSingle(newsId: Int): Single<News> {
        return newsCache.getNews(newsId)
    }

    override fun getNewsByIdSingle(newsId: Int): Single<News> {
        return tinkoffApi.getNews(newsId).doOnSuccess {
            newsCache.cache(it)
        }
    }

}