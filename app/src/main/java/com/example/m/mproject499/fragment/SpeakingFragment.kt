package com.example.m.mproject499.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MainApp.Companion.NUMBER
import com.example.m.mproject499.R
import com.example.m.mproject499.activity.SpeakingActivity
import kotlinx.android.synthetic.main.speaking_fragment.*
import org.jetbrains.anko.toast

class SpeakingFragment : Fragment() {


    private lateinit var speakingActivity: SpeakingActivity
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
        nextFrag.setOnClickListener {
            NUMBER += 1
            nextFrag.text = "click $NUMBER"
            context?.toast("click $NUMBER")

            if(NUMBER == 10){
                activity?.finish()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        MainApp.NUMBER = 0
        super.onDestroyView()
    }



}
