package com.ragertf.tinkoffnews.di.modules.activity

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator

@Module
open class NavigatorModule internal constructor(){
    @Provides
    open fun provideNavigator(): Navigator = Navigator {}
}