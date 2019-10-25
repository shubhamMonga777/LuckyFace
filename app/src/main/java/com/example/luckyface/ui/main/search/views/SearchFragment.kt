package com.example.luckyface.ui.main.search.views

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.luckyface.R
import com.example.luckyface.data.models.DataView
import com.example.luckyface.ui.main.search.adaptor.StaggeredAdapter
import com.example.luckyface.ui.main.search.listner.OnLoadMoreListener
import com.example.luckyface.ui.main.search.listner.RecyclerViewLoadMoreScroll
import com.example.luckyface.ui.main.search.viewmodel.SearchViewModel
import com.example.luckyface.util.replaceFragmeent
import com.example.luckyface.widget.StaggerdSpacesItemDecoration
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    private var rootView: View? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerview: RecyclerView? = null
    private var gridLayoutManager: StaggeredGridLayoutManager? = null
    private var staggeredAdapter: StaggeredAdapter? = null
    private var scrollListener: RecyclerViewLoadMoreScroll? = null
    private  lateinit var  mSearch : TextView


    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.search_fragment, container, false)
        initialize()


        mSearch = this.tv_openSearch
        mSearch.setOnClickListener {

            replaceFragmeent(SearchListFragment(), R.id.llparent, true)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //   viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()

        swipeRefresh!!.setOnRefreshListener {
            Handler().postDelayed({
                setAdapter()
                swipeRefresh!!.isRefreshing = false
            }, 5000)
        }
    }

    private fun initialize() {
        swipeRefresh = rootView!!.findViewById<View>(R.id.swipeRefresh) as SwipeRefreshLayout
        recyclerview = rootView!!.findViewById<View>(R.id.recyclerview) as RecyclerView
        recyclerview!!.setHasFixedSize(true)
        val spacingInPixels = 5
        recyclerview!!.addItemDecoration(StaggerdSpacesItemDecoration(spacingInPixels))

        setAdapter()

        scrollListener = RecyclerViewLoadMoreScroll(gridLayoutManager!!)
        scrollListener!!.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })

        recyclerview!!.addOnScrollListener(scrollListener!!)
    }

    private fun setAdapter() {
        gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerview!!.layoutManager = gridLayoutManager
        staggeredAdapter = StaggeredAdapter(DataView.getDataViews(15), context!!)
        recyclerview!!.adapter = staggeredAdapter
    }

    private fun LoadMoreData() {
        staggeredAdapter!!.addLoadingView()
        Handler().postDelayed({
            val dataViews = DataView.getDataViews(15)
            staggeredAdapter!!.removeLoadingView()
            staggeredAdapter!!.addData(dataViews)
            staggeredAdapter!!.notifyDataSetChanged()
            scrollListener!!.setLoaded()
        }, 5000)

    }

    companion object {

        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
