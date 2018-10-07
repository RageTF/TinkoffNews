package com.ragertf.tinkoffnews.cache

import android.support.test.runner.AndroidJUnit4
import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class NewsCacheTest {

    @Inject
    lateinit var newsCache: NewsCache
    @Inject
    lateinit var tinkoffDatabase: TinkoffDatabase

    @Before
    fun init() {
        DaggerAppTestComponent.builder()
                .cacheModule(CacheModule())
                .networkModule(NetworkModule())
                .daoModule(DaoModule())
                .build()
                .inject(this)
        tinkoffDatabase.clearAll()
    }

    @Test
    fun mustClearNewsCacheTest() {
        val news = TestDataFactory.getTestNews(1)
        newsCache.cache(news)
        newsCache.clear()
        val testObserver = TestObserver<News>()
        newsCache.getNews(news.id).subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(CacheException::class.java)
    }

    @Test
    fun mustThrowIfNewsNotExists() {
        val testObserver = TestObserver<News>()
        newsCache.getNews(-10).subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(CacheException::class.java)
    }

    @Test
    fun mustAddNewsToCacheTest() {
        val news = TestDataFactory.getTestNews(1)
        val testSubscriber = TestObserver<News>()
        newsCache.cache(news)
        newsCache.getNews(news.id).subscribe(testSubscriber)
        testSubscriber.await()
        testSubscriber.assertValue(news)
    }

    @After
    fun clear() {
        tinkoffDatabase.clearAll()
    }

}