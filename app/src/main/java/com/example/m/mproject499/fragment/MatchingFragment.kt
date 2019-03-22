package com.example.m.mproject499

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.MainApp.Companion.History
import com.example.m.mproject499.MainApp.Companion.NUMBER
import com.example.m.mproject499.activity.MatchingActivity
import com.example.m.mproject499.activity.ResultActivity
import com.example.m.mproject499.adapter.MatchingAdapter
import com.example.m.mproject499.data.Constants
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.fragment_listening.*
import kotlinx.android.synthetic.main.fragment_matching.*
import kotlinx.android.synthetic.main.item_matching.view.*
import org.jetbrains.anko.toast
import java.util.*


class MatchingFragment : Fragment() {

    private lateinit var matchingActivity: MatchingActivity
    private lateinit var adapter: MatchingAdapter

    companion object {
        fun fragment(matchingActivity: MatchingActivity): MatchingFragment {
            val fragment = MatchingFragment()
            fragment.matchingActivity = matchingActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matching, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        randomQuiz()
        match_quest.text = MainApp.History[NUMBER].let {
            it.desc_eng.replace(it.word.toLowerCase(), "______")
        }
        match_count.text = "Question " + (NUMBER + 1).toString() + "/" + Constants.maxQuestions.toString()


        adapter = MatchingAdapter(MainApp.instance.applicationContext, History[NUMBER].choice, History[NUMBER].word)
        gridView.adapter = adapter



        match_next.setOnClickListener {

            NUMBER += 1
            if (NUMBER == 9) {
                match_next.text = "EXIT"
                match_next.setOnClickListener {
                    //startActivity(ResultActivity.getStartIntent(MainApp.instance.applicationContext))
                    activity?.finish()
                }
            }

            match_count.text = "Question " + (NUMBER + 1).toString() + "/" + Constants.maxQuestions.toString()
            MainApp.History[NUMBER].let { q ->
                match_quest.text = q.desc_eng.replace(q.word.toLowerCase(), "______")
            }

            adapter = MatchingAdapter(MainApp.instance.applicationContext, History[NUMBER].choice,History[NUMBER].word)
            gridView.adapter = adapter
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
        } while (numbers.size < Constants.maxQuestions)
    }


}
