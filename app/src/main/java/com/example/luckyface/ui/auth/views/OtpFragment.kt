package com.example.luckyface.ui.auth.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.luckyface.R
import com.example.luckyface.util.onChange
import com.example.luckyface.util.replaceFragmeent
import kotlinx.android.synthetic.main.fragment_otp.view.*


class OtpFragment : Fragment(), View.OnClickListener {

    lateinit var rootView: View
    lateinit var mInstructions: TextView
    lateinit var mResend: Button
    lateinit var mSubmit: Button
    lateinit var mOtp: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_otp, container, false)

        // TODO Fetch data and ids
        fetchData()
        animateView()
        countDown()


        //Watcher
        mOtp.onChange {
            if (it.length == 4){
                mOtp.setText("")
                replaceFragmeent(RegisterFragment(), R.id.root_layout, true)}
        }

        return rootView
    }

    @SuppressLint("SetTextI18n")
    private fun fetchData() {

        mInstructions = rootView.tv_instruction
        mResend = rootView.btn_Resend
        mSubmit = rootView.btn_submit
        mOtp = rootView.et_otp
        mSubmit.setOnClickListener(this)
        mResend.isEnabled = false
        mInstructions.text = "We have sent a 4-digit OTP to\n${arguments?.getString("email")}"
    }

    @SuppressLint("SetTextI18n")
    private fun countDown() {
        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mResend.text = "(${millisUntilFinished / 1000})Resend"
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                mResend.text = "Resend"
                mResend.isEnabled = true
                countDown()
            }

        }.start()
    }

    private fun animateView() {

        val bottomUp = AnimationUtils.loadAnimation(
            context,
            R.anim.bottom_up
        )
        val hiddenPanel = rootView.findViewById<LinearLayout>(R.id.ll_layout)
        hiddenPanel.startAnimation(bottomUp)
        hiddenPanel.visibility = View.VISIBLE
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_submit -> {

            }
        }
    }

}
