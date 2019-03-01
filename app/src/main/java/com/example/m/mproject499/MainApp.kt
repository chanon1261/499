package com.example.m.mproject499

import android.app.Application
import com.example.m.mproject499.Dagger.AppComponent
import com.example.m.mproject499.Dagger.AppModule
import com.example.m.mproject499.Dagger.DaggerAppComponent
import io.realm.Realm


open class MainApp : Application() {

    companion object {
        @JvmStatic lateinit var graph: AppComponent
        lateinit var instance: MainApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initializeGraph()
        instance = this
        initializeGraph()

    }

    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Realm.getDefaultInstance().close()
    }
}