package com.ragertf.tinkoffnews.presentation.view

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import java.util.*

interface TitleListView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndStrategy::class)
    fun showEmpty()

    @StateStrategyType(AddToEndStrategy::class)
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