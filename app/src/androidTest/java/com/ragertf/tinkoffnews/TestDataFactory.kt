package com.ragertf.tinkoffnews

import com.ragertf.tinkoffnews.data.dto.Date
import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.data.network.tinkoff.Response
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import io.realm.RealmObject

object TestDataFactory {

    fun getTestTitle(id: Int): Title {
        return Title(id, "Test", "Test", Date(id.toLong()), id)
    }

    fun getTestTitleList(): List<Title> {
        val titles = ArrayList<Title>()
        for (i in 0 until 10) {
            titles.add(getTestTitle(i))
        }
        return titles
    }

    fun getTestNews(id: Int): News {
        return News(getTestTitle(id), Date(id.toLong()), Date(id.toLong()), "Test", id, "usual")
    }

    fun getTestNewsList(): List<News> {
        val list = ArrayList<News>()
        for (i in 0..10) {
            list.add(getTestNews(i))
        }
        return list
    }

    fun <T> getNotOkResponse() = Response<T>(TinkoffApi.FAIL, null, null)
    fun <T> getOkResponse(data: T) = Response(TinkoffApi.OK, data, "trackId")
    fun <T> getOkResponseWithNullPayload() = Response<T>(TinkoffApi.OK, null, null)


    fun getMultipleRealmTestData(): List<RealmObject> {
        val list = ArrayList<RealmObject>()
        list.addAll(getTestTitleList())
        list.addAll(getTestNewsList())
        return list
    }


}