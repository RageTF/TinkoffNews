package com.ragertf.tinkoffnews

import android.app.Application
import com.ragertf.tinkoffnews.di.components.ActivityComponent
import com.ragertf.tinkoffnews.di.components.AppComponent
import com.ragertf.tinkoffnews.di.components.DaggerActivityComponent
import com.ragertf.tinkoffnews.di.components.DaggerAppComponent
import com.ragertf.tinkoffnews.di.modules.activity.NavigatorModule
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.CiceroneModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import com.ragertf.tinkoffnews.di.modules.presenter.HelperModule
import io.reactivex.plugins.RxJavaPlugins
import io.realm.Realm

class TinkoffAplication : Application() {

    companion object {
        private lateinit var instance: TinkoffAplication

        private fun getAppComponent() = instance.appComponent

        fun getPresenterComponent() = getAppComponent()
                .plusPresenterSubComponent(HelperModule(instance))

        fun getActivityComponent(navigatorModule: NavigatorModule) = instance.getActivityComponent(navigatorModule)
    }

    private val appComponent: AppComponent = DaggerAppComponent.builder()
            .ciceroneModule(CiceroneModule())
            .cacheModule(CacheModule())
            .networkModule(NetworkModule())
            .daoModule(DaoModule())
            .build()

    private fun getActivityComponent(navigatorModule: NavigatorModule): ActivityComponent = DaggerActivityComponent.builder()
            .appComponent(appComponent)
            .navigatorModule(navigatorModule)
            .build()

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
        instance = this
    }

}