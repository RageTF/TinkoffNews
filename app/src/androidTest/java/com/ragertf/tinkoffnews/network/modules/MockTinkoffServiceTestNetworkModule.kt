package com.ragertf.tinkoffnews.network.modules

import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffService
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import okhttp3.OkHttpClient
import org.mockito.Mockito

class MockTinkoffServiceTestNetworkModule : NetworkModule(){

    override fun provideTinkoffService(okHttpClient: OkHttpClient): TinkoffService {
        return Mockito.mock(TinkoffService::class.java)
    }

}