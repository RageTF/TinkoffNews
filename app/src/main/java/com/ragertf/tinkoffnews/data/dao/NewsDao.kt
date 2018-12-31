package com.ragertf.tinkoffnews.data.dao

import com.ragertf.tinkoffnews.data.dto.News
import io.reactivex.Single

interface NewsDao {
    /**
     *  @param newsId Идентификатор новости
     *
     *  @params isTakeFromCacheIfExists Флаг указывающий, что данные нужно вернуть из кэша,
     *                                  если они там есть, в противном случае данные
     *                                  должны быть запрошены у сервера
     */
    fun getNewsByIdSingle(newsId: Int): Single<News>
}