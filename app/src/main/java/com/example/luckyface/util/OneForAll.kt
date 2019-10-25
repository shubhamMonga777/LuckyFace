package com.example.luckyface.util

data class OneForAll(
    var email: String? = null,
    var name: String? = null,
    var password: String? = null,
    var path: String? = null
) {
    companion object {
        @Volatile
        private var INSTANCE: OneForAll? = null

        infix fun show(number :Int) : Int{

            return number+10
        }

        fun getInstance(): OneForAll {
            return INSTANCE ?: synchronized(this) {
                OneForAll().also {
                    INSTANCE = it
                }
            }
        }
    }
}