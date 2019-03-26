package com.example.m.mproject499.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var username: String = "", var email: String = "")


@IgnoreExtraProperties
data class Score(var uid: String = "", var listening: Double = 0.0,var speaking: Double = 0.0,var matching: Double = 0.0,var status: Boolean)
{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "listening" to listening,
            "speaking" to speaking,
            "matching" to matching,
            "status" to status
        )
    }
}
