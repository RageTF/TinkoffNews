package com.ragertf.tinkoffnews.data.dao

import com.ragertf.tinkoffnews.data.dto.Title
import io.reactivex.Observable

interface TitleDao {
    fun getTitleListSortedByDate(): Observable<Title>
}