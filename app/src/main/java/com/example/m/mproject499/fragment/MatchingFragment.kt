package com.example.m.mproject499

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.activity.MatchingActivity


class MatchingFragment : Fragment() {

    private lateinit var matchingActivity: MatchingActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }


}
