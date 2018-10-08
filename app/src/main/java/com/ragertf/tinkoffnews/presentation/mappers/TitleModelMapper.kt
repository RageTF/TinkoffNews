package com.ragertf.tinkoffnews.presentation.mappers

import com.ragertf.tinkoffnews.data.dto.Title
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import com.ragertf.tinkoffnews.utils.toCalendar
import io.reactivex.functions.Function

class TitleModelMapper: Function<Title,TitleModel>{
    override fun apply(t: Title): TitleModel {
        return TitleModel(t.id,t.name?:"",t.text?:"",t.publicationDate.toCalendar())
    }
}