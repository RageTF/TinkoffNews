package com.ragertf.tinkoffnews.presentation.view

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ragertf.tinkoffnews.presentation.model.NewsModel

interface NewsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showNews(newsModel: NewsModel)

    @StateStrategyType(AddToEndStrategy::class)
    fun showLoad()

    @StateStrategyType(AddToEndStrategy::class)
    fun hideLoad()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorAndRepeat(@StringRes text: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRepeat()
}