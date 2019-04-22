package com.independent.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.independent.m.mproject499.ListeningFragment
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.R

class ListeningActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, ListeningActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Listening"


        val extras = intent.extras
        if (extras != null) {
            extras.getString("key")?.let {
                createFragment(ListeningFragment.fragment(this, it))
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        MainApp.Result.clear()
        onBackPressed()
        return true
    }

    private fun createFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.list_frag, fragment)
        fragmentTransaction.commit()
    }

}
