package com.ragertf.tinkoffnews.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ragertf.tinkoffnews.TinkoffAplication
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

    /**
     *  @param isRestore True если Activity воссоздался после уничтожения.
     */
    fun onFirstViewAttach(isRestore: Boolean) {
        super.onFirstViewAttach()
        if (!isRestore)
            router.newRootScreen(TitleScreen())
    }

    fun onBackStackChange(stackSize: Int) {
        if (stackSize > 0) {
            viewState.showHomeUp()
        } else {
            viewState.hideHomeUp()
        }
    }

    fun onHomeUp() {
        router.exit()
    }

}