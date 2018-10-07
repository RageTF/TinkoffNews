package com.ragertf.tinkoffnews.presentation.view

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.ragertf.tinkoffnews.presentation.model.NewsModel

interface NewsView : MvpView {
    fun showNews(newsModel: NewsModel)
    fun showLoad()
    fun hideLoad()

    fun showErrorAndRepeat(@StringRes text: Int)
    fun hideRepeat()
}