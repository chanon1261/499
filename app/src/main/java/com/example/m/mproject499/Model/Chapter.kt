package com.example.m.mproject499.Model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties



@IgnoreExtraProperties
data class Chapter(
    var eng: String = "",
    var th: String = "",
    var day: Int = 0

) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "eng" to eng,
            "th" to th,
            "day" to day
        )
    }
}
