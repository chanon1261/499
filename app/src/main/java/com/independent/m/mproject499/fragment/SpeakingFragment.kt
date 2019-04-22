package com.independent.m.mproject499.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.EditText
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.MainApp.Companion.History
import com.independent.m.mproject499.MainApp.Companion.NUMBER
import com.independent.m.mproject499.MainApp.Companion.wordsList
import com.independent.m.mproject499.R
import com.independent.m.mproject499.activity.ResultActivity
import com.independent.m.mproject499.activity.SpeakingActivity
import com.independent.m.mproject499.data.Constants.maxQuestions
import kotlinx.android.synthetic.main.fragment_speaking.*
import java.util.*

class SpeakingFragment : Fragment() {


    private lateinit var speakingActivity: SpeakingActivity
    lateinit var editText: EditText
    var answer: String = ""
    var question = ""
    private var mode = 0


    companion object {
        fun fragment(speakingActivity: SpeakingActivity, it: String): SpeakingFragment {
            val fragment = SpeakingFragment()
            val args = Bundle()
            args.putInt("mode", it.toInt())
            fragment.arguments = args
            fragment.speakingActivity = speakingActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_speaking, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startSpeechToText()
        mode = arguments?.getInt("mode")!!

        editText = view.findViewById(R.id.editText)
        speak_count.text = "question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()

        randomQuiz()
        println(History)
        if (History.isNotEmpty()) {
            question = question()
            test_word.text = question
            nextFrag.text = "Next"
        } else {
            activity?.finish()
        }

        nextFrag.setOnClickListener {

            if (imgSpeak_show.visibility == View.INVISIBLE) {
                checkAnswer()
                speakAns.isClickable = false
                return@setOnClickListener
            }

            NUMBER += 1

            speakAns.isClickable = true
            imgSpeak_show.visibility = View.INVISIBLE
            speakAns.visibility = View.VISIBLE
            editText.text.clear()

            speak_count.text = "question " + (NUMBER + 1).toString() + "/" + maxQuestions.toString()

            question = question()
            test_word.text = question

            if (NUMBER == 9) {
                nextFrag.text = "EXIT"
                nextFrag.setOnClickListener {
                    checkAnswer()
                    startActivity(ResultActivity.getStartIntent(MainApp.instance.applicationContext, 2))
                    activity?.finish()
                }
            }
        }

        speakAns.setOnClickListener {
            speakAns.isClickable = false
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

    private fun checkAnswer() {
        answer = answer.replace(" ".toRegex(), "")
        imgSpeak_show.visibility = View.VISIBLE
        if (answer.toLowerCase() == question.toLowerCase()) {
            imgSpeak_show.setImageResource(R.drawable.ic_check)
            imgSpeak_show.setBackgroundResource(R.drawable.oval_shape)
            speakAns.visibility = View.INVISIBLE
            MainApp.Result.add(true)
        } else {
            imgSpeak_show.setImageResource(R.drawable.ic_close)
            imgSpeak_show.setBackgroundColor(
                ContextCompat.getColor(
                    MainApp.instance.applicationContext,
                    R.color.fail
                )
            )
            MainApp.Result.add(false)
        }
        Log.d("test fx", "${answer.toLowerCase()} == ${question.toLowerCase()}")
    }

    private fun question(): String {
        var question = ""
        when (mode) {
            4 -> {
                question = History[NUMBER].word.capitalize()
            }
            5 -> {
                question = History[NUMBER].desc_eng
                test_word.textSize = 18F
                test_word.gravity = Gravity.CENTER
            }
        }
        return question

    }

}
