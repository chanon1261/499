package com.example.m.mproject499.Dagger


import com.example.m.mproject499.Activity.ListeningActivity
import com.example.m.mproject499.Activity.MatchingActivity
import com.example.m.mproject499.Activity.SpeakingActivity
import com.example.m.mproject499.Activity.WordsActivity
import com.example.m.mproject499.MainActivity
import com.example.m.mproject499.MainApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApp: MainApp)
    fun inject(mainActivity: MainActivity)
    fun inject(wordsActivity: WordsActivity)
    fun inject(speakingActivity: SpeakingActivity)
    fun inject(listeningActivity: ListeningActivity)
    fun inject(matchingActivity:MatchingActivity)
}