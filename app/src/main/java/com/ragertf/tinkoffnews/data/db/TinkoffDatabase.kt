package com.ragertf.tinkoffnews.data.db

import io.reactivex.Single
import io.realm.Realm

interface TinkoffDatabase {
    fun <T> getRealmRunnable(r: (Realm) -> T?): Single<T>
    fun <T> realm(r: (Realm) -> T): T
    fun <T> realmTransaction(r: (Realm) -> T): T
    fun clearAll()
}