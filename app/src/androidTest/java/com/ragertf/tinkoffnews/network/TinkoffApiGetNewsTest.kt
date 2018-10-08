package com.ragertf.tinkoffnews.network


import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.NetworkException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dto.News
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
class TinkoffApiGetNewsTest {

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
    fun mustThrowExceptionWhenCanNotLoadNewsListTest() {
        Mockito.`when`(tinkoffService.getNews(1)).thenReturn(Single.error(IOException()))

        val testObserver = TestObserver<News>()
        tinkoffApi.getNews(1).subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(InternetConnectionException::class.java)

        val testObserver1  = TestObserver<News>()
        Mockito.`when`(tinkoffService.getNews(1)).thenReturn(Single.error(Exception()))
        tinkoffApi.getNews(1).subscribe(testObserver1)
        testObserver1.await()
        testObserver1.assertError(NetworkException::class.java)
    }

    @Test
    fun mustThrowServerRespondingExceptionIfGetNewsResponseIsNotOkTest() {
        val id = 1
        Mockito.`when`(tinkoffService.getNews(id)).thenReturn(Single.just(TestDataFactory.getNotOkResponse()))
        val testObserver = TestObserver<News>()
        tinkoffApi.getNews(id).subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(ServerRespondingException::class.java)
    }

    @Test
    fun mustThrowServerRespondingExceptionOnGetNewsIfPayloadIsNullAndResponseIsOkTest() {
        val id = 1
        Mockito.`when`(tinkoffService.getNews(id)).thenReturn(Single.just(TestDataFactory.getOkResponseWithNullPayload()))
        val testObserver = TestObserver<News>()
        tinkoffApi.getNews(id).subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(ServerRespondingException::class.java)
    }


}