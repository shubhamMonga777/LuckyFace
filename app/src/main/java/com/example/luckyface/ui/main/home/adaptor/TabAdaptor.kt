package com.example.luckyface.ui.main.home.adaptor

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.fragment.app.FragmentPagerAdapter
import com.example.luckyface.ui.main.feed.views.FeedFragment
import com.example.luckyface.ui.main.home.views.LuckyFaceFragment
import com.example.luckyface.ui.main.profile.views.ProfileFragment

class TabAdaptor(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) :
    FragmentPagerAdapter(fm) {


    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LuckyFaceFragment()
            }
            1 -> {
                FeedFragment()
            }
            2 -> {
                ProfileFragment()
            }

            else -> LuckyFaceFragment()
        }
    }
}