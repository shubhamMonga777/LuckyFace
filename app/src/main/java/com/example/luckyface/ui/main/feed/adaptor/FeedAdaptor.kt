package com.example.luckyface.ui.main.feed.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.luckyface.R
import com.example.luckyface.util.loadImage
import kotlinx.android.synthetic.main.item_post.view.*

class FeedAdaptor(
    private val context: Context
) :
    RecyclerView.Adapter<FeedAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        context.loadImage(
            "http://3.130.107.198/LF/web_root/users/3f59ef4b5faa082b39bf82f0e266c88943cd0a2dae5c1af386/profile_pic/c315932d2b67a80d723a3ae4ed2e0907.jpeg",
            holder.image
        )

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = view.iv_post

    }
}