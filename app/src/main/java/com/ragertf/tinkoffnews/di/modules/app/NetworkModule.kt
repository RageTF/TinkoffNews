package com.ragertf.tinkoffnews.di.modules.app

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffService
import com.ragertf.tinkoffnews.data.network.tinkoff.impls.CachedInterceptor
import com.ragertf.tinkoffnews.data.network.tinkoff.impls.TinkoffApiImpl
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    open fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addNetworkInterceptor(CachedInterceptor(context.applicationContext))
            .cache(Cache(File( context.cacheDir,"retrofit"),10 * 1024 * 1024))
            .connectionPool(ConnectionPool(2, 1, TimeUnit.MINUTES))
            .build()

    @Provides
    @Singleton
    open fun provideTinkoffService(okHttpClient: OkHttpClient): TinkoffService = Retrofit.Builder()
            .baseUrl(TinkoffApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TinkoffService::class.java)

    @Provides
    @Singleton
    open fun provideTinkoffApi(tinkoffService: TinkoffService): TinkoffApi = TinkoffApiImpl(tinkoffService)

}