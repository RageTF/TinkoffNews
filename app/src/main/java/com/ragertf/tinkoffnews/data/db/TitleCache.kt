package com.ragertf.tinkoffnews.data.db

import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Observable
import io.reactivex.Single

interface TitleCache {
    fun getTitleListSortByDate(): Observable<Title>
    fun getCount(): Single<Long>
    fun cache(title: Title): Boolean
    fun cache(title: List<Title>)
    fun clear()
}