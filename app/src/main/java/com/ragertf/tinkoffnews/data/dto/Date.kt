package com.ragertf.tinkoffnews.data.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Date(
        @field:SerializedName("milliseconds")
        @field:Expose
        var milliseconds: Long = 0
) : RealmObject() {
    override fun equals(other: Any?): Boolean {
        return if (hashCode() == other?.hashCode() && other is Date)
            milliseconds == other.milliseconds
        else
            false
    }

    override fun hashCode(): Int {
        return milliseconds.toInt()
    }
}