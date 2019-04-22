package com.independent.m.mproject499.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var username: String = "", var email: String = "")


@IgnoreExtraProperties
data class Score(
    var uid: String = "",
    var listening: Double = 0.0,
    var speaking: Double = 0.0,
    var matching: Double = 0.0,
    var flag_l: Boolean,
    var flag_s: Boolean,
    var flag_m: Boolean
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "listening" to listening,
            "speaking" to speaking,
            "matching" to matching,
            "flag_l" to flag_l,
            "flag_s" to flag_s,
            "flag_m" to flag_m
        )
    }
}
