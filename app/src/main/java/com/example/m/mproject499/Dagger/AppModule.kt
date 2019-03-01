package com.example.m.mproject499.Dagger

import com.example.m.mproject499.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mainApp: MainApp) {

    @Provides
    @Singleton
    fun provideMainApp() = mainApp
}