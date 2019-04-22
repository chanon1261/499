package com.independent.m.mproject499

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.independent.m.mproject499.activity.GoogleLoginActivity
import com.independent.m.mproject499.data.Constants.FONT
import com.independent.m.mproject499.R.string.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import android.text.Spannable
import com.independent.m.mproject499.data.CustomTypefaceSpan
import android.text.SpannableString


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: DatabaseReference
    private lateinit var typeface: Typeface

    companion object {
        const val TAG = "fxdxdk"
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //supportActionBar?.hide()
        MainApp.graph.inject(this)
        requestPermissions()

        typeface = Typeface.createFromAsset(assets, FONT)
        toolbar.changeToolbarFont()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        createFragment(MainFragment.fragment(this), getString(learning_mode))
        menuChangFont()


    }

    override fun onBackPressed() {
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
//            drawer_layout.closeDrawer(GravityCompat.START)
//
//        } else {
//
//        }
//
//        if (supportFragmentManager.backStackEntryCount == 1) {
//            this.finish()
//        } else {
//            supportFragmentManager.popBackStack()
//        }
        MainApp.Result.clear()
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        try {
            auth.currentUser?.let {
                Picasso.with(this).load(it.photoUrl).into(imageView)
                user_name.text = it.displayName
                user_email.text = it.email
                user_name.typeface = typeface
                user_email.typeface = typeface
            }
        } catch (e: Exception) {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
            finish()
            e.printStackTrace()
        }
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
            R.id.logout -> {
                logout()
            }
        }
        fragment?.let {
            createFragment(it, title)
        }
        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun logout() {
        auth.signOut()
        googleSignInClient.signOut()
        startActivity(Intent(this, GoogleLoginActivity::class.java))
        finish()
    }

    private fun createFragment(fragment: Fragment, name: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_content, fragment)
        fragmentTransaction.commit()
        toolbar.changeToolbarFont()
        title = name


    }

    private fun Toolbar.changeToolbarFont() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = typeface
                break
            }
        }
    }

    fun menuChangFont() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val m = navView.menu
        for (i in 0 until m.size()) {
            val mi = m.getItem(i)
            val subMenu = mi.subMenu
            if (subMenu != null && subMenu.size() > 0) {
                for (j in 0 until subMenu.size()) {
                    val subMenuItem = subMenu.getItem(j)
                    applyFontToMenuItem(subMenuItem)
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi)
        }

    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        val font = Typeface.createFromAsset(assets, FONT)
        val mNewTitle = SpannableString(mi.title)
        mNewTitle.setSpan(CustomTypefaceSpan("", font), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        mi.title = mNewTitle
    }

    @Throws(PackageManager.NameNotFoundException::class)
    private fun requestPermissions(): Boolean {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        val toRequest = java.util.ArrayList<String>()
        if (info.requestedPermissions != null) {
            for (p in info.requestedPermissions) {
                if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                    toRequest.add(p)
                }
            }
        }

        if (toRequest.size > 0) {
            val tra = arrayOfNulls<String>(toRequest.size)
            for (i in tra.indices) {
                tra[i] = toRequest[i]
            }
            ActivityCompat.requestPermissions(this, tra, 102)
        }
        return toRequest.size == 0
    }
}

