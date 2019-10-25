package com.example.luckyface.ui.auth.views

import `in`.galaxyofandroid.spinerdialog.SpinnerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.luckyface.R
import com.example.luckyface.databinding.FragmentRegisterP2Binding
import com.example.luckyface.ui.auth.listner.AuthListner
import com.example.luckyface.ui.auth.viewmodel.AuthViewModel
import com.example.luckyface.ui.auth.viewmodel.AuthViewModelFactory
import com.example.luckyface.ui.main.MainActivity
import com.example.luckyface.util.hide
import com.example.luckyface.util.show
import com.example.luckyface.util.showToast
import kotlinx.android.synthetic.main.fragment_register_p2.*
import kotlinx.android.synthetic.main.fragment_register_p2.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class RegisterP2Fragment : Fragment(), View.OnClickListener, AuthListner, KodeinAware {

    override val kodein by kodein()

    private lateinit var rootView: View
    private var spinnerDialog: SpinnerDialog? = null
    private var items: ArrayList<String> = ArrayList()
    private lateinit var mState: TextView
    private lateinit var mCity: TextView
    private lateinit var mSubmit: TextView
    var stateFlag: Boolean? = null
    private var viewModel: AuthViewModel? = null
    private val factory: AuthViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegisterP2Binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register_p2, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        rootView = binding.root
        binding.viewmodel = viewModel
        viewModel?.authListner = this
        fetchIds()
        animateView()

        return rootView
    }

    private fun fetchIds() {
        mState = rootView.tv_state
        mCity = rootView.tv_city
        mSubmit = rootView.btn_submit

        mState.setOnClickListener(this)
        mCity.setOnClickListener(this)
        mSubmit.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_state -> {

                stateFlag = true

                items.add("Mumbai")
                items.add("Delhi")
                items.add("Bengaluru")
                items.add("Hyderabad")
                items.add("Ahmedabad")
                items.add("Chennai")
                items.add("Kolkata")
                items.add("Surat")
                items.add("Pune")
                items.add("Jaipur")
                items.add("Lucknow")
                items.add("Kanpur")

                showSpinnerDialog(items)
            }

            R.id.tv_city -> {

                stateFlag = false

                items.clear()
                items.add("Mansa")
                items.add("Barnama")
                items.add("Bhikhi")
                items.add("Cheema")
                items.add("Patiala")
                items.add("Rajpura")
                items.add("Mohali")
                items.add("Khrar")

                showSpinnerDialog(items)
            }

        }
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

    private fun showSpinnerDialog(items: ArrayList<String>) {
        spinnerDialog = SpinnerDialog(
            activity,
            items,
            "Select or Search City",
            R.style.DialogAnimations_SmileWindow,
            "Close"
        )// With 	Animation

        spinnerDialog?.setCancellable(true) // for cancellable
        spinnerDialog?.setShowKeyboard(false)// for open keyboard by default

        spinnerDialog?.showSpinerDialog()

        spinnerDialog?.bindOnSpinerListener { item, position ->

            if (stateFlag!!)
                mState.text = item
            else
                mCity.text = item

        }
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSucess() {
        progress_bar.hide()
        Intent(activity, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity?.startActivity(it)
        }
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        when (message.split("\n")[0]) {

            "EMPTY_REQUEST" -> {
                showVaildationError()
                showToast("All fields are mandetory")
            }

            "INVALID_REQUEST" -> {
                showVaildationError()
                showToast(message)
            }

            else -> {
                showToast(message)
            }
        }
    }

    private fun showVaildationError() {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        rootView.ll_layout.startAnimation(shake)
    }

}
