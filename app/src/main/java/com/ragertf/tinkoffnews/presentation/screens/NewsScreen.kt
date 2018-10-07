package com.ragertf.tinkoffnews.presentation.screens

import android.support.v4.app.Fragment
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import com.ragertf.tinkoffnews.presentation.view.fragments.NewsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class NewsScreen(private val newsId: Int) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return NewsFragment.getInstance(newsId)
    }

    override fun getScreenKey(): String {
        return "com.ragertf.tinkoffnews.presentation.view.fragments.NewsFragment?newsId=$newsId"
    }

}