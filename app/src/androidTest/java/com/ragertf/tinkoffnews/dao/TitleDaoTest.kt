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
    fun mustSaveToCacheTitleListWhenSuccessResponseTest() {
        val testDataList = TestDataFactory.getTestTitleList()

        Mockito.`when`(titleCache.cache(TestDataFactory.getTestTitle(0))).thenReturn(true)
        Mockito.`when`(tinkoffApi.getTitleList()).thenReturn(Observable.fromIterable(testDataList))

        val testObserver = TestObserver<Title>()
        titleDao.getTitleListSortedByDate(false).subscribe(testObserver)
        testObserver.await()

        Mockito.verify(titleCache).cache(testDataList)
    }

    @Test
    fun mustReturnTitleListFromCacheIfFailResponse() {
        val testDataList = TestDataFactory.getTestTitleList()
        Mockito.`when`(titleCache.getCount()).thenReturn(Single.just(testDataList.size.toLong()))
        Mockito.`when`(titleCache.getTitleListSortByDate()).thenReturn(Observable.fromIterable(testDataList))
        Mockito.`when`(tinkoffApi.getTitleList()).thenReturn(Observable.error(ServerRespondingException()))

        val testObserver = TestObserver<Title>()
        titleDao.getTitleListSortedByDate(true).subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSequence(testDataList)
    }

    @Test
    fun mustLoadTitleListFromApiIfCachedTitleEmptyTest() {
        val testDataList = TestDataFactory.getTestTitleList()
        Mockito.`when`(titleCache.getCount()).thenReturn(Single.just(0))
        Mockito.`when`(titleCache.getTitleListSortByDate()).thenReturn(Observable.empty<Title>())
        Mockito.`when`(tinkoffApi.getTitleList()).thenReturn(Observable.fromIterable<Title>(testDataList))

        val testObserver = TestObserver<Title>()
        titleDao.getTitleListSortedByDate(true).subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSequence(testDataList)
    }

}