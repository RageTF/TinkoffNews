package com.ragertf.tinkoffnews.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.data.EmptyCacheException
import com.ragertf.tinkoffnews.data.InternetConnectionException
import com.ragertf.tinkoffnews.data.ServerRespondingException
import com.ragertf.tinkoffnews.data.dao.TitleDao
import com.ragertf.tinkoffnews.presentation.mappers.TitleModelMapper
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import com.ragertf.tinkoffnews.presentation.screens.NewsScreen
import com.ragertf.tinkoffnews.presentation.view.TitleListView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import ru.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

@InjectViewState
class TitleListPresenter : MvpPresenter<TitleListView>() {

    @Inject
    lateinit var titleListDao: TitleDao
    @Inject
    lateinit var router: Router

    /**
     * Флаг означющий, что предыдущий набор данных был пуст
     */

    private var isEmpty = true
    private val singleObserverTitleList = object : SingleObserver<List<TitleModel>> {
        override fun onSubscribe(d: Disposable?) {}
        override fun onSuccess(value: List<TitleModel>) {
            if (value.isEmpty()) {
                viewState.showEmpty()
            } else {
                viewState.hideEmpty()
            }
            viewState.showTitleList(value)
            isEmpty = value.isEmpty()
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            if (isEmpty) viewState.showEmpty()
            viewState.showErrorRepeatSnackBar(when (e) {
                is ServerRespondingException -> R.string.server_responding_error
                is InternetConnectionException -> R.string.internet_connection_error
                else -> R.string.unkonow_error
            })
        }
    }

    init {
        TinkoffAplication.getPresenterComponent().inject(this)
        getDataSource(true).subscribe(singleObserverTitleList)
    }

    private fun getDataSource(isCache: Boolean): Single<List<TitleModel>> = Observable.just(isCache).flatMap {
        if (it) {
            titleListDao.getTitleListSortedByDateFromCache()
        } else {
            titleListDao.getTitleListSortedByDate()
        }
    }.onErrorResumeNext(Function {
        if (it is EmptyCacheException) {
            titleListDao.getTitleListSortedByDate()
        } else {
            Observable.error(it)
        }
    }).map(TitleModelMapper()).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
        viewState.showProgress()
    }.doFinally {
        viewState.hideProgress()
    }.doAfterSuccess {
        viewState.showGroupsTitleByDate(mapToGroup(it))
    }

    private fun mapToGroup(titleListModel: List<TitleModel>): Map<Int, Calendar> {
        var month = -1
        var year = -1
        val map = mutableMapOf<Int, Calendar>()
        for (i in 0 until titleListModel.size) {
            val titleMonth = titleListModel[i].publicationDate.get(Calendar.MONTH)
            val titleYear = titleListModel[i].publicationDate.get(Calendar.YEAR)
            if (month != titleMonth || year != titleYear) {
                month = titleMonth
                year = titleYear
                map[i] = titleListModel[i].publicationDate
            }
        }
        return map
    }

    fun onRefreshTitleList() {
        getDataSource(false).subscribe(singleObserverTitleList)
    }

    fun onTitleModelClick(titleModel: TitleModel) {
        router.navigateTo(NewsScreen(titleModel.id))
    }

}