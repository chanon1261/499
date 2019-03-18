package com.example.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MatchingFragment
import com.example.m.mproject499.R

class MatchingActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MatchingActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Matching"
        createFragment(MatchingFragment.fragment(this))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun createFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.match_frag, fragment)
        fragmentTransaction.commit()
    }
}
