package com.ragertf.tinkoffnews.presentation.view

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import java.util.*

interface TitleListView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmpty()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideEmpty()

    /**
     *  @param titleMap Key - позиция перед которой нужно создать заголовок группы,
     *                  Calendar - дата
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showGroupsTitleByDate(titleMap: Map<Int, Calendar>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTitleList(titleList: List<TitleModel>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorSnackBar(@StringRes stringRes: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorRepeatSnackBar(@StringRes stringRes: Int)

}