package com.example.luckyface.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.luckyface.R
import com.example.luckyface.ui.main.feed.views.FeedFragment
import com.example.luckyface.ui.main.home.views.LuckyFaceFragment
import com.example.luckyface.ui.main.profile.views.ProfileFragment
import com.example.luckyface.ui.main.search.views.SearchFragment
import com.example.luckyface.util.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        setSupportActionBar(toolbar)
//        val logo = layoutInflater.inflate(R.layout.home_toolbar_view, toolbar)

        // To open the first tab as default

        val firstFragment = LuckyFaceFragment()
        openFragment(firstFragment)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_home -> {
                    val firstFragment = LuckyFaceFragment.newInstance()
                    openFragment(firstFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_feed -> {
                    val secondFragment = FeedFragment.newInstance()
                    openFragment(secondFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_search -> {
                    val secondFragment = SearchFragment.newInstance()
                    openFragment(secondFragment)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_profile -> {
                    val secondFragment = ProfileFragment.newInstance()
                    openFragment(secondFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun openFragment(fragment: Fragment) {
        replaceFragment(fragment, R.id.container, true)
    }

}