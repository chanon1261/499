package com.example.m.mproject499

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.m.mproject499.Adapter.DaysAdapter
import com.example.m.mproject499.Model.Days
import com.example.m.mproject499.R.string.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        createFragment(MainFragment.fragment(this),getString(learning_mode))

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        user_name.text = "test"
        user_email.text = "test@email.com"
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var title = ""
        when (item.itemId) {
            R.id.learning_mode -> {
                fragment = MainFragment.fragment(this)
                title = getString(learning_mode)
            }
            R.id.test_mode -> {
                title = getString(test_mode)
                fragment = TestFragment.fragment(this)
            }
            R.id.stat -> {
                title = getString(stat)
                fragment = StatFragment.fragment(this)
            }
        }
        fragment?.let {
            createFragment(it,title)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun createFragment(fragment: Fragment,title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content, fragment)
        fragmentTransaction.commit()
        supportActionBar?.title = title
    }

    private fun generateData(): ArrayList<Days> {
        val result = ArrayList<Days>()

        for (i in 0..9) {
            val user = Days("Bett", "Awesome work ;)")
            result.add(user)
        }
        return result
    }

}
