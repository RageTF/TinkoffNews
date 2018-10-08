package com.ragertf.tinkoffnews.cache


import android.support.test.runner.AndroidJUnit4
import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.network.modules.MockTinkoffServiceTestNetworkModule
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


@RunWith(MockitoJUnitRunner::class)
class TitleCacheTest {

    @Inject
    lateinit var titleCache: TitleCache
    @Inject
    lateinit var tinkoffDatabase: TinkoffDatabase

    @Before
    fun init() {
        DaggerAppTestComponent.builder()
                .cacheModule(CacheModule())
                .networkModule(MockTinkoffServiceTestNetworkModule())
                .daoModule(DaoModule())
                .build()
                .inject(this)
        tinkoffDatabase.clearAll()
    }


    @Test
    fun mustClearTitleCacheTest() {
        val titles = TestDataFactory.getTestTitleList()
        titleCache.cache(titles)
        titleCache.clear()
        val testObserver = TestObserver<Title>()
        titleCache.getTitleListSortByDate().subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(CacheException::class.java)
    }

    @Test
    fun mustAddTitleToCacheTest() {
        val title = TestDataFactory.getTestTitleList()
        val testObserver = TestObserver<Title>()
        titleCache.cache(title)
        titleCache.getTitleListSortByDate().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSet(title)
    }


    @Test
    fun mustAddTitleListToCacheAndReturnSortedListTest() {
        var titles = TestDataFactory.getTestTitleList()
        val testObserver = TestObserver<Title>()
        titleCache.cache(titles)

        titles = titles.sortedByDescending {
            it.publicationDate?.milliseconds ?: 0
        }

        titleCache.getTitleListSortByDate().subscribe(testObserver)
        testObserver.await()
        testObserver.assertValueSequence(titles)
    }

    @Test
    fun mustThrowCachedExceptionIfNotCachedTest() {
        val testObserver = TestObserver<Title>()
        titleCache.getTitleListSortByDate().subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(CacheException::class.java)
    }

    @After
    fun clear() {
        tinkoffDatabase.clearAll()
    }

}