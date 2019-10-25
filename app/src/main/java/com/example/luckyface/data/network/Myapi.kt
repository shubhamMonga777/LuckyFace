package com.example.luckyface.data.network

import com.example.luckyface.data.network.responses.AuthResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface Myapi {

    @FormUrlEncoded
    @POST("check/email")
    suspend fun checkEmail(
        @Field("auth") auth: String,
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Response<AuthResponse>

    @Multipart
    @POST("new/user2")
    suspend fun registerUser(
        @Part("auth") auth: RequestBody,
        @Part("display_name") display_name: RequestBody,
        @Part("country") country: RequestBody,
        @Part("state") state: RequestBody,
        @Part("city") city: RequestBody,
        @Part("pincode") pincode: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("login/email")
    suspend fun loginUser(
        @Field("auth") auth: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    companion object {

        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): Myapi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://3.130.107.198/LF/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Myapi::class.java)
        }
    }

}