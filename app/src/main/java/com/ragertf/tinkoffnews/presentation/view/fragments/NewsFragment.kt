package com.ragertf.tinkoffnews.presentation.view.fragments

import android.os.Bundle
import android.support.v4.text.HtmlCompat
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.presentation.model.NewsModel
import com.ragertf.tinkoffnews.presentation.presenter.NewsPresenter
import com.ragertf.tinkoffnews.presentation.view.BackPressedListener
import com.ragertf.tinkoffnews.presentation.view.NewsView
import com.ragertf.tinkoffnews.utils.toDateTimeFormat
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.layout_repeat.*
import kotlinx.android.synthetic.main.layout_repeat.view.*

class NewsFragment : MvpAppCompatFragment(), NewsView, BackPressedListener {

    companion object {
        private const val KEY_NEWS_ID = "KEY_NEWS_ID"
        fun getInstance(newsId: Int): NewsFragment {
            val bundle = Bundle()
            bundle.putInt(KEY_NEWS_ID, newsId)
            val news = NewsFragment()
            news.arguments = bundle
            return news
        }
    }

    @InjectPresenter
    lateinit var newsPresenter: NewsPresenter

    @ProvidePresenterTag(presenterClass = NewsPresenter::class)
    fun providePresenterTag() = "com.ragertf.tinkoffnews.presentation.view.fragments.NewsFragment$${arguments?.getInt(KEY_NEWS_ID)
            ?: -1}"

    @ProvidePresenter
    fun provideNewsPresenter() = NewsPresenter(arguments?.getInt(KEY_NEWS_ID)
            ?: throw IllegalArgumentException("NewsId is required"))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onBackPressed(): Boolean {
        newsPresenter.onBackPressed()
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repeat_stub.setOnInflateListener { _, inflated ->
            inflated.repeat.setOnClickListener {
                newsPresenter.onRepeat()
            }
        }
    }

    override fun showNews(newsModel: NewsModel) {
        date.text = newsModel.date.toDateTimeFormat()
        title.text = HtmlCompat.fromHtml(newsModel.title, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)

        content.text = HtmlCompat.fromHtml(newsModel.content, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
        content.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun showLoad() {
        load_layout.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        load_layout.visibility = View.GONE
    }

    override fun showErrorAndRepeat(text: Int) {
        repeat_stub?.inflate()
        getRepeatView()?.visibility = View.VISIBLE
        repeat_message?.setText(text)
    }

    override fun hideRepeat() {
        getRepeatView()?.visibility = View.GONE
    }

    private fun getRepeatView(): View? = view?.findViewById(R.id.repeat_layout)

}