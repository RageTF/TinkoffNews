package com.ragertf.tinkoffnews.data.network.tinkoff.impls

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CachedInterceptor(private val context: Context) : Interceptor {

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.method() == "GET") {
            request = if (isOnline(context)) {
                request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build()
            } else {
                request.newBuilder()
                        .header("Cache-Control","only-if-cached, max-stale=0")
                        .build()
            }
        }

        val original = chain.proceed(request).newBuilder()
                .cacheResponse()
                .removeHeader("Pragma")
                .header("Cache-Control",
                        String.format("max-age=%d", 120))
                .build()
        val url = request.url()

        val newRequest = request.newBuilder()
                .url(url.newBuilder().removeAllQueryParameters("key")
                        .build())
                .build()

        return  original.newBuilder()
                .request(newRequest)
                .build()
    }
}