package com.ragertf.tinkoffnews.presentation.view.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
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

class MainActivity : MvpAppCompatActivity(), MainView, FragmentManager.OnBackStackChangedListener {

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

        mainPresenter.onFirstViewAttach(savedInstanceState!=null)

        setSupportActionBar(toolbar)
        supportFragmentManager.addOnBackStackChangedListener(this)
        mainPresenter.onBackStackChange(supportFragmentManager.backStackEntryCount)
    }

    override fun showHomeUp() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            mainPresenter.onHomeUp()
        }
    }

    override fun hideHomeUp() {
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar.setNavigationOnClickListener(null)
    }

    override fun onBackStackChanged() {
        mainPresenter.onBackStackChange(supportFragmentManager.backStackEntryCount)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(supportAppNavigator)
    }

    override fun onPause() {
        navigatorHolder.setNavigator(null)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
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