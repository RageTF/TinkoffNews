package com.ragertf.tinkoffnews.data.db.impls

import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.concurrent.Executors

class TinkoffDatabaseImpls : TinkoffDatabase {

    private val singleExecutor = Executors.newSingleThreadExecutor()
    private val realmScheduler = Schedulers.from(singleExecutor)

    companion object {
        private const val NAME = "tinkoff.db"
        private const val VERSION = 1L
    }

    private fun getRealmInstance() = Realm.getInstance(
            RealmConfiguration.Builder()
                    .name(NAME)
                    .schemaVersion(VERSION)
                    .build())

    override fun <T> getRealmRunnable(r: (Realm) -> T?): Single<T> {
        return Single.fromCallable {
            getRealmInstance()
        }.flatMap {
            val result = r(it)
            if (result != null) {
                Single.just(result)
            } else {
                Single.error(CacheException("Returned null"))
            }.doFinally { it.close() }
        }.subscribeOn(realmScheduler)
    }

    override fun clearAll() {
        realmTransaction {
            it.deleteAll()
        }
    }

    override fun <T> realm(r: (Realm) -> T): T = getRealmInstance().use { realm ->
        return r(realm)
    }

    override fun <T> realmTransaction(r: (Realm) -> T): T = getRealmInstance().use { realm ->
        try {
            realm.beginTransaction()
            val result = r(realm)
            realm.commitTransaction()
            result
        } catch (e: Throwable) {
            realm.cancelTransaction()
            throw e
        }
    }

}