package com.ragertf.tinkoffnews.di.modules.app

import com.ragertf.tinkoffnews.data.dao.NewsDao
import com.ragertf.tinkoffnews.data.dao.TitleDao
import com.ragertf.tinkoffnews.data.dao.impls.NewsDaoImpl
import com.ragertf.tinkoffnews.data.dao.impls.TitleDaoImpl
import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule{

    @Singleton
    @Provides
    fun provideNewsDao(tinkoffApi: TinkoffApi): NewsDao = NewsDaoImpl(tinkoffApi)

    @Singleton
    @Provides
    fun provideTitleDao(tinkoffApi: TinkoffApi): TitleDao = TitleDaoImpl(tinkoffApi)

}