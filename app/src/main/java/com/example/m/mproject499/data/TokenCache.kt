package com.example.m.mproject499.data

import com.example.m.mproject499.model.Token

interface TokenCache {
    fun getToken(): Token?
    fun saveToken(token: Token)
}