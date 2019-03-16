package com.example.m.mproject499.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
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
import kotlinx.android.synthetic.main.speaking_fragment.*
import org.jetbrains.anko.toast
import java.util.*

class SpeakingFragment : Fragment() {


    private lateinit var speakingActivity: SpeakingActivity
    var random = 0

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

        nextFrag.text = "click $NUMBER"
        test_word.text = History[NUMBER].word

        nextFrag.setOnClickListener {
            NUMBER += 1
            nextFrag.text = "click $NUMBER"
            //context?.toast("click $NUMBER")
            test_word.text = History[NUMBER].word

            if (NUMBER == 11) {
                activity?.finish()
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        MainApp.NUMBER = 0
        super.onDestroyView()
    }

    private fun startSpeechToText() {
        val editText = view!!.findViewById<EditText>(R.id.editText)

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
                if (matches != null)
                    editText.setText(matches[0])
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


}
