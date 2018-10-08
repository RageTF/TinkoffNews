package com.ragertf.tinkoffnews.dao


import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.dao.modules.MockTestCacheModule
import com.ragertf.tinkoffnews.dao.modules.MockTinkoffApiTestNetworkModule
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dao.TitleDao
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class TitleDaoTest {

    @Inject
    lateinit var tinkoffApi: TinkoffApi
    @Inject
    lateinit var titleDao: TitleDao
    @Inject
    lateinit var titleCache: TitleCache

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
    fun mustReturnTitleListFromServerAndCacheThemTest() {
        val testDataList = TestDataFactory.getTestTitleList()
        Mockito.`when`(tinkoffApi.getTitleList()).thenReturn(Single.just(testDataList))
        val testObserver = TestObserver<Title>()
        titleDao.getTitleListSortedByDate().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSet(testDataList)
        Mockito.verify(titleCache).cache(testDataList)
    }

    @Test
    fun mustReturnTitleListFromCacheTest() {
        val testDataList = TestDataFactory.getTestTitleList()
        Mockito.`when`(titleCache.getTitleListSortByDate()).thenReturn(Observable.fromIterable(testDataList))
        val testObserver = TestObserver<Title>()
        titleDao.getTitleListSortedByDateFromCache().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSet(testDataList)
    }

}