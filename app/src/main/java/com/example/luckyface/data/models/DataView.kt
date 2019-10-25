package com.example.luckyface.data.models

import com.example.luckyface.util.Constant
import java.util.ArrayList

class DataView {

    var name: String = ""
    var viewType: Int = Constant.VIEW_TYPE_ITEM

    companion object {

        fun getDataViews(size: Int): MutableList<DataView> {
            val dataViews = ArrayList<DataView>()
            for (i in 0 until size) {
                val dataView = DataView()
                dataView.name = "Test: $i"
                dataViews.add(dataView)
            }
            return dataViews
        }
    }
}