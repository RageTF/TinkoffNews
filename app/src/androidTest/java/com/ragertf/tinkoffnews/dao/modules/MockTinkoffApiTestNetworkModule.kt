package com.ragertf.tinkoffnews.dao.modules

import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffApi
import com.ragertf.tinkoffnews.data.network.tinkoff.TinkoffService
import com.ragertf.tinkoffnews.di.modules.app.NetworkModule
import org.mockito.Mockito


class MockTinkoffApiTestNetworkModule : NetworkModule(){

    override fun provideTinkoffApi(tinkoffService: TinkoffService): TinkoffApi {
        return Mockito.mock(TinkoffApi::class.java)
    }

}