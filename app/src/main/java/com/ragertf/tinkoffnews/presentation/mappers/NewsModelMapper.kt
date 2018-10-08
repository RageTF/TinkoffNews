package com.ragertf.tinkoffnews.presentation.mappers

import com.ragertf.tinkoffnews.data.dto.News
import com.ragertf.tinkoffnews.presentation.model.NewsModel
import com.ragertf.tinkoffnews.utils.toCalendar
import io.reactivex.functions.Function

class NewsModelMapper : Function<News, NewsModel> {

    override fun apply(t: News): NewsModel {
        val title = t.title
        return NewsModel( title?.text ?: "", title?.name ?: "", t.content
                ?: "", title?.publicationDate.toCalendar())
    }
}