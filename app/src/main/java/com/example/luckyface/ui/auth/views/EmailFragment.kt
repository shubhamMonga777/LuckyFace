package com.example.luckyface.ui.auth.views

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.luckyface.R
import com.example.luckyface.databinding.FragmentEmailBinding
import com.example.luckyface.ui.auth.listner.AuthListner
import com.example.luckyface.ui.auth.viewmodel.AuthViewModel
import com.example.luckyface.ui.auth.viewmodel.AuthViewModelFactory
import com.example.luckyface.ui.main.MainActivity
import com.example.luckyface.util.*
import kotlinx.android.synthetic.main.fragment_email.*
import kotlinx.android.synthetic.main.fragment_email.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class EmailFragment : Fragment(), AuthListner, KodeinAware {


    override val kodein by kodein()

    lateinit var rootView: View
    lateinit var mTermsConditions: TextView

    private var viewModel: AuthViewModel? = null
    private val factory: AuthViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentEmailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        rootView = binding.root
        binding.viewmodel = viewModel
        viewModel?.authListner = this
        animateView()

        viewModel?.getLoggedInUser()?.observe(this, Observer { user ->
            user?.let {
                Intent(activity, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        return rootView
    }

    private fun animateView() {

        mTermsConditions = rootView.tv_termsConditions
        changeColor(mTermsConditions)
        val bottomUp = AnimationUtils.loadAnimation(
            context,
            R.anim.bottom_up
        )
        val hiddenPanel = rootView.findViewById<LinearLayout>(R.id.ll_layout)
        hiddenPanel.startAnimation(bottomUp)
        hiddenPanel.visibility = View.VISIBLE

    }

    private fun changeColor(mTermsConditions: TextView?) {

        val spannable = SpannableString(mTermsConditions?.text)
        spannable.setSpan(

            ForegroundColorSpan(resources.getColor(R.color.colorAccent)),
            41, 62,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        mTermsConditions?.setText(spannable, TextView.BufferType.SPANNABLE)

    }

    private fun openOtpFragment(email: String) {
        val bundle = Bundle()
        bundle.putString("email", email)
        val otpFragment = OtpFragment()
        otpFragment.arguments = bundle
        replaceFragmeent(otpFragment, R.id.root_layout, true)
    }

    private fun openPasswordFragment(){
        val passwordFragment = PasswordFragment()
        replaceFragmeent(passwordFragment, R.id.root_layout, true)
    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSucess() {
        //TODO Open password frgament
        progress_bar.hide()
        OneForAll.getInstance().email = viewModel?.email!!
        openPasswordFragment()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        when (message.split("\n")[0]) {

            "EMPTY_REQUEST" -> {
                showVaildationError()
            }
            "INVALID_REQUEST" -> {
                showVaildationError()
            }
            "NOT_FOUND" -> { //TODO Open otp fragment
                OneForAll.getInstance().email = viewModel?.email!!
                openOtpFragment(viewModel?.email!!)
            }
            else -> {
                showToast(message)
            }
        }
    }

    fun showVaildationError() {
        mTermsConditions.setText(resources.getString(R.string.valid_email))
        rootView.tv_instruction.setText(resources.getString(R.string.wrong_email))
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        rootView.ll_layout.startAnimation(shake)
    }

}