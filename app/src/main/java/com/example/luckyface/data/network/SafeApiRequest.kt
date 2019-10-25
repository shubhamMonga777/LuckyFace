package com.example.luckyface.data.network

import com.example.luckyface.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequst(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {

            val error = response.errorBody()?.string()

            val builder = StringBuilder()
            try {

                error?.let {

                    builder.append(JSONObject(it).getString("ecode") +"\n")
                    builder.append(JSONObject(it).getString("message"))

                }
            } catch (e: JSONException) {

                builder.append("\n")
                builder.append("Response Code : ${response.code()}")

            }

            throw ApiException(builder.toString())
        }
    }

}