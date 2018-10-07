package com.ragertf.tinkoffnews.data.db.impls

import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.dto.News
import io.reactivex.Single

class NewsCacheImpl(private val tinkoffDatabase: TinkoffDatabase) : NewsCache {

    override fun getNews(newsId: Int): Single<News> {
        return tinkoffDatabase.getRealmRunnable {
            val result = it.where(News::class.java).equalTo("id", newsId).findFirst()
            if (result != null)
                it.copyFromRealm(result)
            else
                null
        }
    }

    override fun cache(news: News): Boolean {
        return tinkoffDatabase.realmTransaction {
            it.copyToRealmOrUpdate(news) != null
        }
    }

    override fun clear() {
        return tinkoffDatabase.realmTransaction {
            it.where(News::class.java).findAll().deleteAllFromRealm()
        }
    }

}