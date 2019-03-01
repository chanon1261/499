package com.example.m.mproject499


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    companion object {
        fun fragment(mainActivity: MainActivity): TestFragment {
            val fragment = TestFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        test_page.text = "TEST FRAGMENT"
        super.onViewCreated(view, savedInstanceState)
    }





}
