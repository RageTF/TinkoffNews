package com.ragertf.tinkoffnews.di.modules.app

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class CiceroneModule{

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

}