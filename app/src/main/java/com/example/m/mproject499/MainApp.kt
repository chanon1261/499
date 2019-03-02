package com.example.m.mproject499

import android.app.Application
import com.example.m.mproject499.Dagger.AppComponent
import com.example.m.mproject499.Dagger.AppModule
import com.example.m.mproject499.Dagger.DaggerAppComponent
import com.example.m.mproject499.Data.RealmMigrations
import com.example.m.mproject499.Model.TestWord
import com.vicpin.krealmextensions.save
import io.realm.Realm
import io.realm.RealmConfiguration


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


        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder().schemaVersion(9).migration(RealmMigrations()).build()
        Realm.setDefaultConfiguration(realmConfig)

        initializeGraph()

        generateWord()

    }

    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Realm.getDefaultInstance().close()
    }

    private fun generateWord(){
        for (i in 1..30) {
            TestWord().let {
                it.id = i
                it.word = i.toString()
                it.meaning = "Meaning $i"
                it.desc_th = "DESCRIPTION TH $i"
                it.desc_eng = "DESCRIPTION ENG $i"
                it.save()
            }
        }
    }
}