package com.example.m.mproject499

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stat.*


class StatFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    companion object {
        fun fragment(mainActivity: MainActivity): StatFragment {
            val fragment = StatFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stat_page.text = "STAT FRAGMENT"
        super.onViewCreated(view, savedInstanceState)
    }


}
