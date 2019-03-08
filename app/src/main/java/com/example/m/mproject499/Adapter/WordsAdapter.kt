package com.example.m.mproject499.Adapter

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MainApp.Companion.database
import com.example.m.mproject499.Model.WordFireBase
import com.example.m.mproject499.databinding.WordListBinding
import org.jetbrains.anko.toast
import java.util.*


class WordsAdapter(val context: Context) : RecyclerView.Adapter<WordsAdapter.WordsAdapterViewHolder>() {


    private var items: ArrayList<WordFireBase> = java.util.ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return WordsAdapterViewHolder(
            com.example.m.mproject499.databinding.WordListBinding.inflate(
                layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordsAdapterViewHolder, position: Int) = holder.bind(items[position])

    class WordsAdapterViewHolder(val binding: WordListBinding) : RecyclerView.ViewHolder(binding.root),
        TextToSpeech.OnInitListener {

        var tts: TextToSpeech? = null
        override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // set US English as language for tts
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "The Language specified is not supported!")
                    context.toast("The Language specified is not supported!")
                } else {
                    //buttonSpeak!!.isEnabled = true
                }

            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        }

        val context: Context = binding.root.context

        fun bind(item: WordFireBase) {
            binding.setVariable(BR.word, item.word)
            binding.setVariable(BR.meaning, item.meaning)
            binding.setVariable(BR.descEng, item.desc_eng)
            binding.setVariable(BR.descTH, item.desc_th)

            tts = TextToSpeech(MainApp.instance.applicationContext, this)
            itemView.setOnClickListener {
                speakOut(item.word)
                context.toast(item.word)
//                val post = WordFireBase(item.word, item.meaning, item.desc_eng, item.desc_th,7,item.position)
//                val postValues = post.toMap()
//                val childUpdates = HashMap<String, Any>()
//                val key = database.child("words").push().key
//                childUpdates["/words/$key"] = postValues
//                database.updateChildren(childUpdates)
            }
        }

        private fun speakOut(text: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    fun loadData(data: ArrayList<WordFireBase>) {
        this.items = data
        notifyDataSetChanged()
    }


}