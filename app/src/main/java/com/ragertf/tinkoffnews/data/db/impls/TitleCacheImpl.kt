package com.ragertf.tinkoffnews.data.db.impls

import com.ragertf.tinkoffnews.data.EmptyCacheException
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Sort

class TitleCacheImpl(private val tinkoffDatabase: TinkoffDatabase) : TitleCache {

    override fun getTitleListSortByDate(): Observable<Title> {
        return tinkoffDatabase.getRealmRunnable {
            val result = it.copyFromRealm(it.where(Title::class.java).sort("publicationDate.milliseconds", Sort.DESCENDING).findAll())
            if (result.isEmpty()) {
                throw EmptyCacheException("TitleList cache is empty")
            } else {
                result
            }
        }.flatMapObservable {
            Observable.fromIterable(it)
        }
    }

    override fun getCount(): Single<Long> {
        return tinkoffDatabase.getRealmRunnable {
            it.where(Title::class.java).count()
        }
    }


    override fun cache(title: Title): Boolean {
        return tinkoffDatabase.realmTransaction {
            it.copyToRealmOrUpdate(title) != null
        }
    }

    override fun cache(title: List<Title>) {
        return tinkoffDatabase.realmTransaction {
            for (t in title) {
                it.copyToRealmOrUpdate(t)
            }
        }
    }

    override fun clear() {
        return tinkoffDatabase.realmTransaction {
            it.where(Title::class.java).findAll().deleteAllFromRealm()
        }
    }


}