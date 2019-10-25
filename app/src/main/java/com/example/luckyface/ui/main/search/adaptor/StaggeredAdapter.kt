package com.example.luckyface.ui.main.search.adaptor

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.luckyface.R
import com.example.luckyface.data.models.DataView
import com.example.luckyface.util.Constant
import com.example.luckyface.util.loadImage

class StaggeredAdapter(
    internal var dataViews: MutableList<DataView>?,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addData(dataViews: List<DataView>) {
        this.dataViews!!.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): DataView {
        return dataViews!![position]
    }

    fun addLoadingView() {
        //add loading item
        Handler().post {
            var data = DataView()
            data.viewType = Constant.VIEW_TYPE_LOADING
            dataViews!!.add(data)
            notifyItemInserted(dataViews!!.size - 1)
        }
    }

    fun removeLoadingView() {
        //Remove loading item
        dataViews!!.removeAt(dataViews!!.size - 1)
        notifyItemRemoved(dataViews!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Constant.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.staggered_row, parent, false)
            return StaggeredHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.progress_loading, parent, false)
            return LoadingHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataView = dataViews!![position]
        if (holder is StaggeredHolder) {
            val staggeredHolder = holder
            context.loadImage(
                "http://3.130.107.198/LF/web_root/users/3f59ef4b5faa082b39bf82f0e266c88943cd0a2dae5c1af386/profile_pic/c315932d2b67a80d723a3ae4ed2e0907.jpeg",
                staggeredHolder.image
            )


        } else if (holder is LoadingHolder) {
            val loadingHolder = holder
        }

        if (dataView.viewType == Constant.VIEW_TYPE_LOADING) {
            // Span the item if active
            val lp = holder.itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
                holder.itemView.layoutParams = lp
            }
        } else {
            // Span the item if active
            val lp = holder.itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = false
                holder.itemView.layoutParams = lp
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dataViews == null) 0 else dataViews!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataViews!![position].viewType == Constant.VIEW_TYPE_LOADING) Constant.VIEW_TYPE_LOADING else Constant.VIEW_TYPE_ITEM
    }
}