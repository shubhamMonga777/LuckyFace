package com.example.luckyface.ui.main.feed.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luckyface.R
import com.example.luckyface.ui.main.feed.adaptor.FeedAdaptor
import kotlinx.android.synthetic.main.fragment_feed.view.*


class FeedFragment : Fragment() {

    private var mRecyclerView: RecyclerView? = null
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feed, container, false)
        mRecyclerView = rootView.recycler_view

        mRecyclerView?.layoutManager = LinearLayoutManager(activity)
        mRecyclerView?.adapter = FeedAdaptor(context!!)
        return rootView
    }

    companion object {

        fun newInstance(): FeedFragment {
            val fragment = FeedFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

