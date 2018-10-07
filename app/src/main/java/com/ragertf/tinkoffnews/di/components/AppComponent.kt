package com.ragertf.tinkoffnews.di.components

import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.CiceroneModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import com.ragertf.tinkoffnews.di.modules.presenter.HelperModule
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Component(modules = [CacheModule::class, DaoModule::class, NetworkModule::class, CiceroneModule::class])
@Singleton
interface AppComponent {
    fun provideNavigatorHolder(): NavigatorHolder
    fun plusPresenterSubComponent(helperModule: HelperModule): PresenterSubComponent
}