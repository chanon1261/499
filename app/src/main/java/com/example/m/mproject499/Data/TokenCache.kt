package com.example.m.mproject499.Data

import com.example.m.mproject499.Model.Token

interface TokenCache {
    fun getToken(): Token?
    fun saveToken(token: Token)
}