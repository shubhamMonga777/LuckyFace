package com.example.luckyface.data.repositories

import com.example.luckyface.data.db.AppDatabase
import com.example.luckyface.data.db.entities.User
import com.example.luckyface.data.network.Myapi
import com.example.luckyface.data.network.SafeApiRequest
import com.example.luckyface.data.network.responses.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AuthUserRepository(

    private val db: AppDatabase,
    private val api: Myapi
) : SafeApiRequest() {
    suspend fun checkEmail(auth: String, email: String, otp: String): AuthResponse {
        return apiRequst { api.checkEmail(auth, email, otp) }
    }

    suspend fun userRegister(
        auth: RequestBody, name: RequestBody, country: RequestBody
        , state: RequestBody, city: RequestBody, pincode: RequestBody, email: RequestBody,
        password: RequestBody, image: MultipartBody.Part
    ): AuthResponse {
        return apiRequst {
            api.registerUser(
                auth, name, country, state, city, pincode,
                email, password, image
            )
        }
    }

    suspend fun loginUser(auth: String, email: String, password: String) :AuthResponse{
        return apiRequst { api.loginUser(auth,email,password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

   suspend fun deleteUser() = db.getUserDao().deleteUser()


}