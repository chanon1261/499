package com.independent.m.mproject499.dagger


import com.independent.m.mproject499.activity.ListeningActivity
import com.independent.m.mproject499.activity.MatchingActivity
import com.independent.m.mproject499.activity.SpeakingActivity
import com.independent.m.mproject499.activity.WordsActivity
import com.independent.m.mproject499.MainActivity
import com.independent.m.mproject499.MainApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainApp: MainApp)
    fun inject(mainActivity: MainActivity)
    fun inject(wordsActivity: WordsActivity)
    fun inject(speakingActivity: SpeakingActivity)
    fun inject(listeningActivity: ListeningActivity)
    fun inject(matchingActivity: MatchingActivity)
}