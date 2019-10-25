package com.example.luckyface.ui.main.home.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.luckyface.R
import com.example.luckyface.ui.main.profile.views.ProfileFragment

class LuckyFaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {

        fun newInstance(): LuckyFaceFragment {
            val fragment = LuckyFaceFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
