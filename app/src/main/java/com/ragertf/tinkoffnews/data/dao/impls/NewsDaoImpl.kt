package com.ragertf.tinkoffnews.data.dao.impls

import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor

class NewsDaoImpl(private val tinkoffApi: TinkoffApi, private val newsCache: NewsCache) : NewsDao {

    private fun getNewsSingle(newsId: Int): Single<News> {
        return tinkoffApi.getNews(newsId).doOnSuccess {
            newsCache.cache(it)
        }
    }

    override fun getNewsByIdSingle(newsId: Int, isTakeFromCacheIfExists: Boolean): Single<News> {
        return if (isTakeFromCacheIfExists)
            newsCache.getNews(newsId).onErrorResumeNext {
                if (it is CacheException) {
                    getNewsSingle(newsId)
                } else {
                    Single.error(it)
                }
            }
        else
            getNewsSingle(newsId)
    }

}