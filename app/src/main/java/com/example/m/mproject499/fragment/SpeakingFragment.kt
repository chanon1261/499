package com.example.m.mproject499.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MainApp.Companion.History
import com.example.m.mproject499.MainApp.Companion.NUMBER
import com.example.m.mproject499.MainApp.Companion.wordsList
import com.example.m.mproject499.R
import com.example.m.mproject499.activity.SpeakingActivity
import com.example.m.mproject499.data.Constants.maxQuestions
import kotlinx.android.synthetic.main.speaking_fragment.*
import org.jetbrains.anko.toast
import java.util.*

class SpeakingFragment : Fragment() {


    private lateinit var speakingActivity: SpeakingActivity
    lateinit var editText: EditText
    var answer: String = ""
    var question = ""


    companion object {
        fun fragment(speakingActivity: SpeakingActivity): SpeakingFragment {
            val fragment = SpeakingFragment()
            fragment.speakingActivity = speakingActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.speaking_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startSpeechToText()
        editText = view.findViewById(R.id.editText)
        speak_count.text = "Question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()


        randomQuiz()
        println(History)
        if (History.isNotEmpty()) {
            question = History[NUMBER].word
            test_word.text = question
            nextFrag.text = "Next"
        } else {
            activity?.finish()
        }

        nextFrag.setOnClickListener {

            NUMBER += 1

            if (imgSpeak_show.visibility == View.INVISIBLE) {
                checkAnswer()
                return@setOnClickListener
            }

            imgSpeak_show.visibility = View.INVISIBLE
            speakAns.visibility = View.VISIBLE
            editText.text.clear()

            speak_count.text = "Question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()

            question = History[NUMBER].word
            test_word.text = History[NUMBER].word

            if (NUMBER == 9) {
                nextFrag.text = "EXIT"
                nextFrag.setOnClickListener {
                    activity?.finish()
                }
            }
        }

        speakAns.setOnClickListener {
            checkAnswer()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        MainApp.NUMBER = 0
        super.onDestroyView()
    }

    private fun startSpeechToText() {

        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainApp.instance.applicationContext)
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(v: Float) {}

            override fun onBufferReceived(bytes: ByteArray) {}

            override fun onEndOfSpeech() {}

            override fun onError(i: Int) {}

            override fun onResults(bundle: Bundle) {
                val matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)//getting all the matches
                //displaying the first match
                if (matches != null) {
                    editText.setText(matches[0])
                    answer = matches[0]
                }
            }

            override fun onPartialResults(bundle: Bundle) {}

            override fun onEvent(i: Int, bundle: Bundle) {}
        })

        btSpeech.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    speechRecognizer.stopListening()
                    editText.hint = getString(R.string.text_hint)
                }

                MotionEvent.ACTION_DOWN -> {
                    speechRecognizer.startListening(speechRecognizerIntent)
                    editText.setText("")
                    editText.hint = "Listening..."
                }
            }
            false
        }
    }

    private fun randomQuiz() {
        History.clear()
        val numbers: MutableList<Int> = mutableListOf()
        val random = Random()
        do {
            //val next = random.nextInt(wordsList.size)
            val next = random.nextInt(10)
            if (!numbers.contains(next)) {
                numbers.add(next)
                History.add(wordsList[next])
            }
        } while (numbers.size < maxQuestions)
    }

    fun checkAnswer() {
        answer = answer.replace(" ".toRegex(), "")
        imgSpeak_show.visibility = View.VISIBLE
        if (answer.toLowerCase() == question.toLowerCase()) {
            imgSpeak_show.setImageResource(R.drawable.ic_check)
            imgSpeak_show.setBackgroundResource(R.drawable.oval_shape)
            speakAns.visibility = View.INVISIBLE
        } else {
            imgSpeak_show.setImageResource(R.drawable.ic_close)
            imgSpeak_show.setBackgroundColor(
                ContextCompat.getColor(
                    MainApp.instance.applicationContext,
                    R.color.fail
                )
            )
        }
        Log.d("test fx", "${answer.toLowerCase()} == ${question.toLowerCase()}")
    }


}
