package com.ragertf.tinkoffnews.network


import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.NetworkException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffService
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.network.modules.MockTinkoffServiceTestNetworkModule
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class TinkoffApiGetTitleListTest {

    @Inject
    lateinit var tinkoffService: TinkoffService

    @Inject
    lateinit var tinkoffApi: TinkoffApi

    @Before
    fun init() {
        DaggerAppTestComponent.builder()
                .cacheModule(CacheModule())
                .networkModule(MockTinkoffServiceTestNetworkModule())
                .daoModule(DaoModule())
                .build()
                .inject(this)
    }

    @Test
    fun mustThrowServerRespondingExceptionIfGetTitleListResponseIsNotOkTest() {
        Mockito.`when`(tinkoffService.getTitleList()).thenReturn(Single.just(TestDataFactory.getNotOkResponse()))
        val testObserver = TestObserver<List<Title>>()
        tinkoffApi.getTitleList().subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(ServerRespondingException::class.java)
    }

    @Test
    fun mustThrowExceptionWhenCanNotLoadTitleListTest() {
        Mockito.`when`(tinkoffService.getTitleList()).thenReturn(Single.error(IOException()))
        val testObserver = TestObserver<List<Title>>()

        tinkoffApi.getTitleList().subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(InternetConnectionException::class.java)

        Mockito.`when`(tinkoffService.getTitleList()).thenReturn(Single.error(Exception()))

        val testObserver1 = TestObserver<List<Title>>()
        tinkoffApi.getTitleList().subscribe(testObserver1)
        testObserver.await()
        testObserver.assertError(NetworkException::class.java)
    }


    @Test
    fun mustReturnTitleListIfResponseIsOkTest() {
        val testData = TestDataFactory.getTestTitleList()
        Mockito.`when`(tinkoffService.getTitleList()).thenReturn(Single.just(TestDataFactory.getOkResponse(testData)))
        val testObserver = TestObserver<List<Title>>()
        tinkoffApi.getTitleList().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValue(testData)
    }

    @Test
    fun mustReturnEmptyTitleListIfPayloadIsNullAndResponseIsOkTest() {
        Mockito.`when`(tinkoffService.getTitleList()).thenReturn(Single.just(TestDataFactory.getOkResponseWithNullPayload()))
        val testObserver = TestObserver<List<Title>>()
        tinkoffApi.getTitleList().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValue{
            it.isEmpty()
        }
    }

}