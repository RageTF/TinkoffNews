package com.ragertf.tinkoffnews.presentation.model

import java.util.*

data class TitleModel(
        val id: Int,
        val title: String,
        val text: String,
        val publicationDate: Calendar
)