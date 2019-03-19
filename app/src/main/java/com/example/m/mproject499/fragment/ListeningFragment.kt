package com.example.m.mproject499

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.MainApp.Companion.History
import com.example.m.mproject499.MainApp.Companion.NUMBER
import com.example.m.mproject499.activity.ListeningActivity
import kotlinx.android.synthetic.main.fragment_listening.*
import org.jetbrains.anko.toast
import java.util.*


class ListeningFragment : Fragment() {

    private lateinit var listeningActivity: ListeningActivity
    var tts: TextToSpeech? = MainApp.tts
    private val maxQuestions = 10

    companion object {
        fun fragment(listeningActivity: ListeningActivity): ListeningFragment {
            val fragment = ListeningFragment()
            fragment.listeningActivity = listeningActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listening, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val speechListener = object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
                play_word.setImageResource(R.drawable.ic_play)

            }

            override fun onError(utteranceId: String?) {
                Log.d("TTS..", "EEROR")
            }

            override fun onStart(utteranceId: String?) {
                play_word.setImageResource(R.drawable.ic_stop)
            }

        }
        tts?.setOnUtteranceProgressListener(speechListener)


        randomQuiz()
        var question = History[NUMBER].word
        list_count.text = "Listening " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()
        play_word.setOnClickListener {
            speakOut(question)
        }

        listenNextFrag.setOnClickListener {
            context?.toast("Click")
            NUMBER += 1
            editTextListen.text?.clear()
            list_count.text = "Listening " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()
            question = History[NUMBER].word

            if (NUMBER == 9) {
                listenNextFrag.text = "EXIT"
                listenNextFrag.setOnClickListener {
                    activity?.finish()
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        MainApp.NUMBER = 0
        super.onDestroyView()
    }

    private fun randomQuiz() {
        MainApp.History.clear()
        val numbers: MutableList<Int> = mutableListOf()
        val random = Random()
        do {
            //val next = random.nextInt(wordsList.size)
            val next = random.nextInt(10)
            if (!numbers.contains(next)) {
                numbers.add(next)
                MainApp.History.add(MainApp.wordsList[next])
            }
        } while (numbers.size < maxQuestions)
    }

    private fun speakOut(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }
}
