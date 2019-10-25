package com.example.luckyface.ui.auth.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.luckyface.data.repositories.AuthUserRepository
import com.example.luckyface.ui.auth.listner.AuthListner
import com.example.luckyface.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class AuthViewModel(
    private val repository: AuthUserRepository
) : ViewModel() {

    var email: String? = null
    var authListner: AuthListner? = null
    var city: String? = null
    var state: String? = null
    var pinCode: String? = null
    var password: String? = null

    fun getLoggedInUser() = repository.getUser()

    fun sendEmailButtonClicked(view: View) {
        authListner?.onStarted()

        if (email.isNullOrEmpty()) {
            authListner?.onFailure("EMPTY_REQUEST")
            return
        }

        if (!emailValidator(email!!)) {
            authListner?.onFailure("EMPTY_REQUEST")
            return
        }

        Coroutines.main {

            try {
                val response = repository.checkEmail("654321", email!!, "123456")
                response.status?.let {
                    if (response.status)
                        authListner?.onSucess()
                    return@main
                }
                authListner?.onFailure(response.message!!)

            } catch (e: ApiException) {
                authListner?.onFailure(e.message!!)

            } catch (e: NoInternetException) {
                authListner?.onFailure(e.message!!)
            }
        }
    }

    fun submitLoginButtonClicked(view: View) {

        authListner?.onStarted()

        if (password.isNullOrEmpty()) {
            authListner?.onFailure("EMPTY_REQUEST")
            return
        }

        Coroutines.main {

            try {
                val response = repository.loginUser("654321", OneForAll.getInstance().email!!, password!!)
                response.status?.let {
                    if (response.status)
                        authListner?.onSucess()
                    response.data?.let {
                        repository.saveUser(response.data)
                    }
                    return@main
                }
                authListner?.onFailure(response.message!!)

            } catch (e: ApiException) {
                authListner?.onFailure(e.message!!)

            } catch (e: NoInternetException) {
                authListner?.onFailure(e.message!!)
            }
        }


    }

    fun submitButtonClicked(view: View) {

        authListner?.onStarted()

        if (OneForAll.getInstance().email.isNullOrEmpty() || OneForAll.getInstance().name.isNullOrEmpty()
            || OneForAll.getInstance().password.isNullOrEmpty() || OneForAll.getInstance().path.isNullOrEmpty()
        ) {
            authListner?.onFailure("EMPTY_REQUEST")
            return
        }

        if (city.isNullOrEmpty() || state.isNullOrEmpty() || pinCode.isNullOrEmpty()) {
            authListner?.onFailure("EMPTY_REQUEST")
            return
        }

        //string
        val emailBody = createTextBody(OneForAll.getInstance().email!!)
        val nameBody = createTextBody(OneForAll.getInstance().name!!)
        val passwordBody = createTextBody(OneForAll.getInstance().password!!)
        val cityBody = createTextBody(city!!)
        val stateBody = createTextBody(state!!)
        val pinCodeBody = createTextBody(pinCode!!)
        val authBody = createTextBody("654321")
        val countryBody = createTextBody("India")
        val userImage = createMultiPart(OneForAll.getInstance().path!!, "pimage")

        Coroutines.main {
            try {
                val response = repository.userRegister(
                    authBody, nameBody, countryBody, stateBody, cityBody,
                    pinCodeBody, emailBody, passwordBody, userImage
                )

                response.status?.let {
                    if (response.status)
                        authListner?.onSucess()
                    response.data?.let {
                        repository.saveUser(response.data)
                    }
                    return@main
                }
                authListner?.onFailure(response.message!!)

            } catch (e: ApiException) {
                authListner?.onFailure(e.message!!)

            } catch (e: Exception) {
                authListner?.onFailure(e.message!!)
            }
        }
    }

    fun emailValidator(email: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }
}