package com.ragertf.tinkoffnews.presentation.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView: MvpView{
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showHomeUp()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideHomeUp()
}