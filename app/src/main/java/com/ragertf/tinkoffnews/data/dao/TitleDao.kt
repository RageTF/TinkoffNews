package com.ragertf.tinkoffnews.data.dao

import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Observable

interface TitleDao {
    fun getTitleListSortedByDateFromCache(): Observable<Title>
    fun getTitleListSortedByDate(): Observable<Title>
}