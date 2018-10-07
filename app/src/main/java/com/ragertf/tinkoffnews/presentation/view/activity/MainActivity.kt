package com.ragertf.tinkoffnews.presentation.view.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ragertf.tinkoffnews.R
import com.ragertf.tinkoffnews.TinkoffAplication
import com.ragertf.tinkoffnews.di.modules.activity.SupportAppNavigatorModule
import com.ragertf.tinkoffnews.presentation.presenter.MainPresenter
import com.ragertf.tinkoffnews.presentation.view.BackPressedListener
import com.ragertf.tinkoffnews.presentation.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var supportAppNavigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        TinkoffAplication.getActivityComponent(SupportAppNavigatorModule(this, R.id.fragment_container))
                .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(supportAppNavigator)
    }

    override fun onPause() {
        navigatorHolder.setNavigator(null)
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment != null
                && fragment is BackPressedListener
                && (fragment as BackPressedListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

}