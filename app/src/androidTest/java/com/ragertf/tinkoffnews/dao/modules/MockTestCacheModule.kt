package com.ragertf.tinkoffnews.dao.modules

import com.ragertf.tinkoffnews.data.db.NewsCache
import com.ragertf.tinkoffnews.data.db.TinkoffDatabase
import com.ragertf.tinkoffnews.data.db.TitleCache
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import org.mockito.Mockito

class MockTestCacheModule: CacheModule(){

    override fun provideNewsCache(tinkoffDatabase: TinkoffDatabase): NewsCache {
        return Mockito.mock(NewsCache::class.java)
    }

    override fun provideTitleCache(tinkoffDatabase: TinkoffDatabase): TitleCache {
        return Mockito.mock(TitleCache::class.java)
    }

}