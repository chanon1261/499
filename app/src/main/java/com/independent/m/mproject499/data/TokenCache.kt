package com.independent.m.mproject499.data

import com.independent.m.mproject499.model.Token

interface TokenCache {
    fun getToken(): Token?
    fun saveToken(token: Token)
}