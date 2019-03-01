package com.example.m.mproject499.Adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.example.m.mproject499.Activity.WordsActivity
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.Model.Words
import com.example.m.mproject499.databinding.DaysListBinding
import com.example.m.mproject499.databinding.WordListBinding
import org.jetbrains.anko.toast
import java.util.*


class WordsAdapter(val context: Context): RecyclerView.Adapter<WordsAdapter.WordsAdapterViewHolder>() {


    private var items: ArrayList<Words> = java.util.ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return WordsAdapterViewHolder(com.example.m.mproject499.databinding.WordListBinding.inflate(layoutInflator, parent, false))
    }

    override fun onBindViewHolder(holder: WordsAdapterViewHolder, position: Int) = holder.bind(items[position])

    class WordsAdapterViewHolder(val binding: WordListBinding) : RecyclerView.ViewHolder(binding.root) ,
        TextToSpeech.OnInitListener{

        var tts: TextToSpeech? = null
        override fun onInit(status: Int) {
            if (status == TextToSpeech.SUCCESS) {
                // set US English as language for tts
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS","The Language specified is not supported!")
                    context.toast("The Language specified is not supported!")
                } else {
                    //buttonSpeak!!.isEnabled = true
                }

            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        }

        val context: Context = binding.root.context

        fun bind(item: Words) {
            binding.setVariable(BR.word, item.word)
            binding.setVariable(BR.meaning, item.meaning)
            binding.setVariable(BR.descEng, item.desc_eng)
            binding.setVariable(BR.descTH, item.desc_th)

            tts = TextToSpeech(MainApp.instance.applicationContext,this)
            itemView.setOnClickListener {
                speakOut(item.word)
                context.toast(item.word)
            }
        }

        fun speakOut(text:String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
            }
        }
    }

    fun loadDatas(data: ArrayList<Words>){
        this.items = data
        notifyDataSetChanged()
    }





}