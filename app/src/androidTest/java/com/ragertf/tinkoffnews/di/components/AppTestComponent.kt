package com.ragertf.tinkoffnews.di.components

import com.ragertf.tinkoffnews.cache.NewsCacheTest
import com.ragertf.tinkoffnews.cache.TinkoffDatabaseTest
import com.ragertf.tinkoffnews.cache.TitleCacheTest
import com.ragertf.tinkoffnews.dao.NewsDaoTest
import com.ragertf.tinkoffnews.dao.TitleDaoTest
import com.ragertf.tinkoffnews.di.modules.app.CacheModule
import com.ragertf.tinkoffnews.di.modules.app.CiceroneModule
import com.ragertf.tinkoffnews.di.modules.app.DaoModule
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import com.ragertf.tinkoffnews.network.TinkoffApiGetNewsTest
import com.ragertf.tinkoffnews.network.TinkoffApiGetTitleListTest
import dagger.Component
import javax.inject.Singleton


@Component(modules = [CacheModule::class, DaoModule::class, NetworkModule::class,CiceroneModule::class])
@Singleton
abstract class AppTestComponent : AppComponent {

    abstract fun inject(newsTest: NewsCacheTest)
    abstract fun inject(titleTest: TitleCacheTest)
    abstract fun inject(tinkoffDatabaseTest: TinkoffDatabaseTest)

    abstract fun inject(tinkoffApiGetNewsTest: TinkoffApiGetNewsTest)
    abstract fun inject(tinkoffApiGetTitleListTest: TinkoffApiGetTitleListTest)

    abstract fun inject(newsDaoTest: NewsDaoTest)
    abstract fun inject(titleDaoTest: TitleDaoTest)

}