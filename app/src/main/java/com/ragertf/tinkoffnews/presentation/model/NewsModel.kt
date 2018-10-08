package com.ragertf.tinkoffnews.presentation.model

import java.util.*

data class NewsModel(
        val title: String,
        val name: String,
        val content: String,
        val date: Calendar
)