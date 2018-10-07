package com.ragertf.tinkoffnews.di.modules.app

import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.data.db.impls.NewsCacheImpl
import com.ragertf.tinkoffnews.data.db.impls.TinkoffDatabaseImpls
import com.ragertf.tinkoffnews.data.db.impls.TitleCacheImpl
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
open class CacheModule {

    @Singleton
    @Provides
    open fun provideTinkoffDatabase(): TinkoffDatabase = TinkoffDatabaseImpls()

    @Singleton
    @Provides
    open fun provideNewsCache(tinkoffDatabase: TinkoffDatabase): NewsCache = NewsCacheImpl(tinkoffDatabase)

    @Singleton
    @Provides
    open fun provideTitleCache(tinkoffDatabase: TinkoffDatabase): TitleCache = TitleCacheImpl(tinkoffDatabase)

}