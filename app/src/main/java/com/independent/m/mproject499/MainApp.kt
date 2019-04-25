package com.independent.m.mproject499

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import com.independent.m.mproject499.dagger.AppComponent
import com.independent.m.mproject499.dagger.AppModule
import com.independent.m.mproject499.dagger.DaggerAppComponent
import com.independent.m.mproject499.data.RealmMigrations
import com.independent.m.mproject499.model.Chapter
import com.independent.m.mproject499.model.WordFireBase
import com.google.firebase.database.*
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList


open class MainApp : Application(), TextToSpeech.OnInitListener {

    companion object {
        @JvmStatic
        lateinit var graph: AppComponent
        lateinit var instance: MainApp
        lateinit var database: DatabaseReference
        var wordsList: MutableList<WordFireBase> = mutableListOf()
        var NUMBER = 0
        var History: MutableList<WordFireBase> = mutableListOf()
        var Result:ArrayList<Boolean> = ArrayList()
        var tts: TextToSpeech? = null

        var chooseChapter = -1
    }

    override fun onCreate() {
        super.onCreate()
        initializeGraph()
        instance = this

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder().schemaVersion(9).migration(RealmMigrations()).build()
        Realm.setDefaultConfiguration(realmConfig)

        initializeGraph()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().reference
        tts = TextToSpeech(MainApp.instance.applicationContext, this)

        //writeNewWord()
        //writeChapter()
        initWords()
    }

    open fun initializeGraph() {
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        Realm.getDefaultInstance().close()
    }

    open fun stopTTS() {
        if (tts!!.isSpeaking) {
            //if speaking then stop
            tts!!.stop()
            //mTTS.shutdown()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)
            tts!!.setSpeechRate(0.75F)
            //tts!!.setPitch(0.1F)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
                toast("The Language specified is not supported!")
            } else {
                //buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    private fun initWords() {
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                wordsList.clear()
                dataSnapshot.children.mapNotNullTo(wordsList) { it.getValue<WordFireBase>(WordFireBase::class.java) }
                Log.d(MainActivity.TAG, wordsList.size.toString())
                //generateWord()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("words").addValueEventListener(userListener)
    }

    private fun writeNewWord() {
        for (i in 1..30) {
            for (j in 1..10) {

                val key = database.child("words").push().key
                if (key == null) {
                    Log.w("", "Couldn't get push key for posts")
                    return
                }
                val choice: MutableList<String> = mutableListOf("A", "B", "C", "D")
                val word = WordFireBase(" ", " ", " ", " ", i, 0, choice)
                val wordValues = word.toMap()
                val childUpdates = HashMap<String, Any>()
                childUpdates["/words/$key"] = wordValues
                database.updateChildren(childUpdates)

            }
        }

    }

    private fun writeChapter() {
        for (i in 1..30) {
            val key = database.child("chapters").push().key
            if (key == null) {
                Log.w("", "Couldn't get push key for posts")
                return
            }
            val chapter = Chapter(" ", " ", i)
            val chapterValue = chapter.toMap()
            val childUpdates = HashMap<String, Any>()
            childUpdates["/chapters/$key"] = chapterValue
            database.updateChildren(childUpdates)
        }
    }

}