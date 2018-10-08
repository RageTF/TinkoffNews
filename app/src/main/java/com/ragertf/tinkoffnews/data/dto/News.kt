package com.ragertf.tinkoffnews.data.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class News(
        @field:SerializedName("title")
        @field:Expose
        var title: Title? = null,
        @field:SerializedName("creationDate")
        @field:Expose
        var creationDate: Date? = null,
        @field:SerializedName("lastModificationDate")
        @field:Expose
        var lastModificationDate: Date? = null,
        @field:SerializedName("content")
        @field:Expose
        var content: String? = null,
        @field:SerializedName("bankInfoTypeId")
        @field:Expose
        var bankInfoTypeId: Int = 0,
        @field:SerializedName("typeId")
        @field:Expose
        var typeId: String? = null
) : RealmObject() {

    @field:PrimaryKey
    var id: Int = 0

    override fun equals(other: Any?): Boolean {
        return if (hashCode() == other?.hashCode() && other is News)
            title?.id ?: 0 == other.title?.id ?: 0
        else
            false
    }

    override fun hashCode(): Int {
        return title?.id ?: 0
    }
}