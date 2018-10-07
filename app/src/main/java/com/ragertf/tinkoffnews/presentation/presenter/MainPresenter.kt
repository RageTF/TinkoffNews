package com.ragertf.tinkoffnews.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.presentation.screens.NewsScreen
import com.ragertf.tinkoffnews.presentation.screens.TitleScreen
import com.ragertf.tinkoffnews.presentation.view.MainView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    init {
        TinkoffAplication.getPresenterComponent().inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(TitleScreen())
    }

}