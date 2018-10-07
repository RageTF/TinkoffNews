package com.ragertf.tinkoffnews.di.components

import com.ragertf.tinkoffnews.di.modules.presenter.HelperModule
import com.ragertf.tinkoffnews.presentation.presenter.MainPresenter
import com.ragertf.tinkoffnews.presentation.presenter.NewsPresenter
import com.ragertf.tinkoffnews.presentation.presenter.TitleListPresenter
import dagger.Subcomponent

@Subcomponent(modules = [HelperModule::class])
interface PresenterSubComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(titleListPresenter: TitleListPresenter)
    fun inject(newsPresenter: NewsPresenter)
}