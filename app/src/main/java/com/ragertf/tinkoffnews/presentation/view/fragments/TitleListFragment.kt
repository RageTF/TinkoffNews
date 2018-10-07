package com.ragertf.tinkoffnews.presentation.view.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.presentation.adapter.TitleListAdapter
import com.ragertf.tinkoffnews.presentation.model.TitleModel
import com.ragertf.tinkoffnews.presentation.presenter.TitleListPresenter
import com.ragertf.tinkoffnews.presentation.view.TitleListView
import com.ragertf.tinkoffnews.utils.decorations.GroupItemDecoration
import com.ragertf.tinkoffnews.utils.decorations.MarginItemDecoration
import com.ragertf.tinkoffnews.utils.getMonth
import kotlinx.android.synthetic.main.fragment_title_list.*
import java.util.*

class TitleListFragment : MvpAppCompatFragment(), TitleListView {

    companion object {
        fun getInstance(): TitleListFragment = TitleListFragment()
    }

    private val titleListAdapter = TitleListAdapter()
    private val groupItemDecoration by lazy {
        GroupItemDecoration(
                resources.getDimensionPixelOffset(R.dimen.title_group_decoration_height),
                resources.getDimensionPixelOffset(R.dimen.title_group_text_size).toFloat()
        )
    }

    @InjectPresenter
    lateinit var titleListPresenter: TitleListPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_title_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        title_list.adapter = titleListAdapter
        title_list.layoutManager = LinearLayoutManager(context)
        title_list.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelOffset(R.dimen.item_space)))
        title_list.addItemDecoration(groupItemDecoration)

        title_list_refresh.setOnRefreshListener {
            titleListPresenter.onRefreshTitleList()
        }

        titleListAdapter.listener = titleListPresenter::onTitleModelClick


    }

    override fun showErrorSnackBar(stringRes: Int) {
        view?.let {
            Snackbar.make(it, stringRes, Snackbar.LENGTH_SHORT)
        }?.show()
    }

    override fun showErrorRepeatSnackBar(stringRes: Int) {
        view?.let {
            Snackbar.make(it, stringRes, Snackbar.LENGTH_LONG)
        }?.setAction(R.string.snackbar_repeat) {
            titleListPresenter.onRefreshTitleList()
        }?.show()
    }

    override fun showProgress() {
        title_list_refresh.isRefreshing = true
    }

    override fun showEmpty() {
        empty.visibility = View.VISIBLE
    }

    override fun hideEmpty() {
        empty.visibility = View.GONE
    }

    override fun showGroupsTitleByDate(titleMap: Map<Int, Calendar>) {
        groupItemDecoration.positionTitleMap = titleMap.mapValues {
            val month = it.value.get(Calendar.MONTH)
            val year = it.value.get(Calendar.YEAR)
            "${resources.getString(getMonth(month))} $year".toUpperCase()
        }
    }

    override fun showTitleList(titleList: List<TitleModel>) {
        titleListAdapter.titleList = titleList
    }

    override fun hideProgress() {
        title_list_refresh.isRefreshing = false
    }
}

