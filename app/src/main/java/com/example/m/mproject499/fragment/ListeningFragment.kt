package com.example.m.mproject499

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.MainApp.Companion.History
import com.example.m.mproject499.MainApp.Companion.NUMBER
import com.example.m.mproject499.activity.ListeningActivity
import com.example.m.mproject499.data.Constants.maxQuestions
import kotlinx.android.synthetic.main.fragment_listening.*
import kotlinx.android.synthetic.main.speaking_fragment.*
import org.jetbrains.anko.toast
import java.util.*


class ListeningFragment : Fragment() {

    private lateinit var listeningActivity: ListeningActivity
    var tts: TextToSpeech? = MainApp.tts
    var question = ""

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
        question = History[NUMBER].word
        list_count.text = "Question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()
        play_word.setOnClickListener {
            speakOut(question)
        }

        editTextListen.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                return@OnKeyListener true
            }
            false
        })


        listenNextFrag.setOnClickListener {

            if (imgListening_show.visibility == View.INVISIBLE) {
                checkAnswer()
                return@setOnClickListener
            }

            listenAns.visibility = View.VISIBLE
            list_show_ans.visibility = View.INVISIBLE
            imgListening_show.visibility = View.INVISIBLE

            NUMBER += 1
            editTextListen.text?.clear()
            list_count.text = "Question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()
            question = History[NUMBER].word

            if (NUMBER == 9) {
                listenNextFrag.text = "EXIT"
                listenNextFrag.setOnClickListener {
                    activity?.finish()
                }
            }


        }

        listenAns.setOnClickListener {
            checkAnswer()
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

    private fun checkAnswer() {
        val answer = editTextListen.text.toString()
        imgListening_show.visibility = View.VISIBLE
        list_show_ans.visibility = View.VISIBLE

        list_show_ans.text = question
        if (answer.toLowerCase() == question.toLowerCase()) {
            imgListening_show.setImageResource(R.drawable.ic_check)
            imgListening_show.setBackgroundResource(R.drawable.oval_shape)
            list_show_ans.setTextColor(
                ContextCompat.getColor(
                    MainApp.instance.applicationContext,
                    R.color.correct
                )
            )

        } else {
            imgListening_show.setImageResource(R.drawable.ic_close)
            imgListening_show.setBackgroundColor(
                ContextCompat.getColor(
                    MainApp.instance.applicationContext,
                    R.color.fail
                )
            )
            list_show_ans.setTextColor(
                ContextCompat.getColor(
                    MainApp.instance.applicationContext,
                    R.color.fail
                )
            )
        }
        listenAns.visibility = View.INVISIBLE
        Log.d("test fx", "${answer.toLowerCase()} == ${question.toLowerCase()}")
    }
}
