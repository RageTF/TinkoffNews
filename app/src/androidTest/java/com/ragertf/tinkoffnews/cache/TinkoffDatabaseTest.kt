package com.ragertf.tinkoffnews.cache

import android.support.test.runner.AndroidJUnit4
import com.ragertf.tinkoffnews.TestDataFactory
import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.di.components.DaggerAppTestComponent
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.network.modules.MockTinkoffServiceTestNetworkModule
import io.reactivex.observers.TestObserver
import io.realm.kotlin.createObject
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class TinkoffDatabaseTest {

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
    fun mustClearAllTest() {
        val list = TestDataFactory.getMultipleRealmTestData()
        tinkoffDatabase.realmTransaction {
            it.copyToRealmOrUpdate(list)
        }
        tinkoffDatabase.clearAll()
        Assert.assertTrue(tinkoffDatabase.realm {
            it.where(News::class.java).count() == 0L && it.where(Title::class.java).count() == 0L
        })
    }

    @Test
    fun mustThrowCachedExceptionIfFunctionReturnNullTest() {
        val testObserver = TestObserver<Int>()
        tinkoffDatabase.getRealmRunnable {
            null
        }.subscribe(testObserver)
        testObserver.await()
        testObserver.assertError(CacheException::class.java)
    }

    @Test
    fun mustCancelCommitIfThrowExceptionTest() {
        val id = 1
        try {
            tinkoffDatabase.realmTransaction {
                it.createObject<Title>(id)
                throw Exception("Not insert this object")
            }
        } catch (e: Exception) {
            val result = tinkoffDatabase.realm {
                it.where(Title::class.java).equalTo("id", id).findFirst()
            }
            Assert.assertNull(result)
        }
    }

    @Test
    fun mustCommitRealmTransactionTest() {
        val id = 1
        tinkoffDatabase.realmTransaction {
            it.createObject<Title>(id)
        }
        val result = tinkoffDatabase.realm {
            it.where(Title::class.java).equalTo("id", id).findFirst()
        }
        Assert.assertNotNull(result)

    }

    @After
    fun clear() {
        tinkoffDatabase.clearAll()
    }

}