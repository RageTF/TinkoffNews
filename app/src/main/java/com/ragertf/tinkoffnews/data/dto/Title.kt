package com.ragertf.tinkoffnews.data.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Title(
        @field:PrimaryKey
        @field:SerializedName("id")
        @field:Expose
        var id: Int = 0,
        @field:SerializedName("name")
        @field:Expose
        var name: String? = null,
        @field:SerializedName("text")
        @field:Expose
        var text: String? = null,
        @field:SerializedName("publicationDate")
        @field:Expose
        var publicationDate: Date? = null,
        @field:SerializedName("bankInfoTypeId")
        @field:Expose
        var bankInfoTypeId: Int = 0
) : RealmObject() {
    override fun equals(other: Any?): Boolean {
        return if (hashCode() == other?.hashCode() && other is Title)
            id == other.id
        else
            false
    }

    override fun hashCode(): Int {
        return id
    }
}