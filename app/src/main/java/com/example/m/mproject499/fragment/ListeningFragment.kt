package com.example.m.mproject499

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.activity.ListeningActivity


class ListeningFragment : Fragment() {

    private lateinit var listeningActivity: ListeningActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
