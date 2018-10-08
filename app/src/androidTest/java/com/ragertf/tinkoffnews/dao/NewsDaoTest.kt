package com.ragertf.tinkoffnews.dao

import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.dao.modules.MockTestCacheModule
import com.ragertf.tinkoffnews.dao.modules.MockTinkoffApiTestNetworkModule
import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class NewsDaoTest {

    @Inject
    lateinit var tinkoffApi: TinkoffApi
    @Inject
    lateinit var newsCache: NewsCache
    @Inject
    lateinit var newsDao: NewsDao

    @Before
    fun initCache() {
        DaggerAppTestComponent.builder()
                .cacheModule(MockTestCacheModule())
                .networkModule(MockTinkoffApiTestNetworkModule())
                .daoModule(DaoModule())
                .build()
                .inject(this)
    }

    @Test
    fun mustReturnNewsFromServerAndCacheThemTest() {
        val testData = TestDataFactory.getTestNews(1)
        Mockito.`when`(newsCache.cache(testData)).thenReturn(true)
        Mockito.`when`(tinkoffApi.getNews(testData.id)).thenReturn(Single.just(testData))

        val testObserver = TestObserver<News>()
        newsDao.getNewsByIdSingle(testData.id).subscribe(testObserver)
        testObserver.await()

        Mockito.verify(newsCache).cache(testData)
    }

    @Test
    fun mustReturnNewsFromCacheTest() {
        val testData = TestDataFactory.getTestNews(1)
        Mockito.`when`(newsCache.getNews(testData.id)).thenReturn(Single.just(testData))
        val testObserver = TestObserver<News>()
        newsDao.getNewsByIdFromCacheSingle(testData.id).subscribe(testObserver)
        testObserver.await()
        testObserver.assertValue(testData)
    }

}