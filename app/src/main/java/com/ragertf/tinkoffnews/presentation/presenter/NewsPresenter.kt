package com.ragertf.tinkoffnews.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.presentation.mappers.NewModelMapper
import com.ragertf.tinkoffnews.presentation.view.NewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter(private val newsId: Int) : MvpPresenter<NewsView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var newsDao: NewsDao

    init {
        TinkoffAplication.getPresenterComponent().inject(this)
        updateNews(true)
    }

    fun onUpdate() {
        updateNews(false)
    }

    private fun updateNews(isTakeFromCache: Boolean) {
        newsDao.getNewsByIdSingle(newsId, isTakeFromCache)
                .subscribeOn(Schedulers.io())
                .map(NewModelMapper())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewState.hideRepeat()
                    viewState.showLoad()
                }
                .doFinally {
                    viewState.hideLoad()
                }
                .subscribe({
                    viewState.showNews(it)
                }, {
                    it.printStackTrace()
                    viewState.showErrorAndRepeat(when (it) {
                        is ServerRespondingException -> R.string.server_responding_error
                        is InternetConnectionException -> R.string.internet_connection_error
                        else -> R.string.unkonow_error
                    })
                })

    }

    fun onBackPressed() {
        router.exit()
    }

}