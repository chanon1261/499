package com.example.m.mproject499.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var username: String = "", var email: String = "")

