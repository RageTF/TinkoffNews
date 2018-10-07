package com.ragertf.tinkoffnews.data.network.tinkoff

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response<T>(
        @field:SerializedName("resultCode")
        @field:Expose
        var resultCode: String? = null,
        @field:SerializedName("payload")
        @field:Expose
        var payload: T? = null,
        @field:SerializedName("trackingId")
        @field:Expose
        var trackingId: String? = null
)