package com.ragertf.tinkoffnews.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.data.CacheException
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.presentation.mappers.NewsModelMapper
import com.ragertf.tinkoffnews.presentation.model.NewsModel
import com.ragertf.tinkoffnews.presentation.view.NewsView
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class NewsPresenter(private val newsId: Int) : MvpPresenter<NewsView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var newsDao: NewsDao

    private val newSingleObserver = object : SingleObserver<NewsModel> {
        override fun onSubscribe(d: Disposable?) {}

        override fun onSuccess(value: NewsModel) {
            viewState.showNews(value)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            viewState.showErrorAndRepeat(when (e) {
                is ServerRespondingException -> R.string.serverRespondingError
                is InternetConnectionException -> R.string.internetConnectionError
                else -> R.string.unknowError
            })
        }

    }

    init {
        TinkoffAplication.getPresenterComponent().inject(this)
        getUpdateNews(true).subscribe(newSingleObserver)
    }

    fun onRepeat(){
        getUpdateNews(false).subscribe(newSingleObserver)
    }

    private fun getUpdateNews(isCache: Boolean) =
            Single.just(isCache).flatMap {
                if (it)
                    newsDao.getNewsByIdFromCacheSingle(newsId)
                else
                    newsDao.getNewsByIdSingle(newsId)
            }.onErrorResumeNext {
                if (it is CacheException)
                    newsDao.getNewsByIdSingle(newsId)
                else
                    Single.error(it)
            }.map(NewsModelMapper()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                viewState.hideRepeat()
                viewState.showLoad()
            }.doFinally {
                viewState.hideLoad()
            }


    fun onBackPressed() {
        router.exit()
    }

}