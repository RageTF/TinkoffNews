package com.ragertf.tinkoffnews.presentation.screens

import android.support.v4.app.Fragment
import com.ragertf.tinkoffnews.presentation.view.fragments.NewsFragment
import com.ragertf.tinkoffnews.presentation.view.fragments.TitleListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen


class TitleScreen : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TitleListFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return "com.ragertf.tinkoffnews.presentation.view.fragments.TitleListFragment"
    }


}