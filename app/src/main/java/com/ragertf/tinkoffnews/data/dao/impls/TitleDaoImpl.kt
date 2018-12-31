package com.ragertf.tinkoffnews.data.dao.impls

import android.util.Log
import com.ragertf.tinkoffnews.data.dao.TitleDao
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import io.reactivex.Observable
import java.util.*

class TitleDaoImpl(private val tinkoffApi: TinkoffApi) : TitleDao {

    companion object {
        private const val TAG = "TitleDaoImpl"
    }

    private val sortedComparator = Comparator { o1: Title, o2: Title ->
        val t1 = o1.publicationDate?.milliseconds ?: 0
        val t2 = o2.publicationDate?.milliseconds ?: 0

        when {
            t2 > t1 -> 1
            t1 == t2 -> 0
            else -> -1
        }
    }

    override fun getTitleListSortedByDate(): Observable<Title> {
        val random = Random()
        return tinkoffApi
                .getTitleList("abcd").flatMapObservable {
                    Observable.fromIterable(it)
                }.sorted(sortedComparator).doOnSubscribe {
                    Log.v(TAG, "getTitleListSortedByDateSubscribed")
                }
    }

}