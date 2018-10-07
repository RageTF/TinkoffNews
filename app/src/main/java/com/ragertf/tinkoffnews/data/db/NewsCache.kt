package com.ragertf.tinkoffnews.data.db

import com.ragertf.tinkoffnews.data.dto.News
import io.reactivex.Maybe
import io.reactivex.Single

interface NewsCache {
    fun getNews(newsId: Int): Single<News>
    fun cache(news: News): Boolean
    fun clear()
}