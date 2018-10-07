package com.ragertf.tinkoffnews.di.modules.activity

import com.arellomobile.mvp.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class SupportAppNavigatorModule(private val activity: MvpAppCompatActivity, private val containerId: Int) : NavigatorModule() {
    override fun provideNavigator(): Navigator = SupportAppNavigator(activity, containerId)
}