package com.example.m.mproject499.Model

import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey

data class Token(
        @PrimaryKey
        @SerializedName("access_token")
        var accessToken: String?,
        @SerializedName("token_type")
        var tokenType: String?,
        @SerializedName("expires_in")
        var expires: Long?, override val id: String?): Entity {
}