package com.example.luckyface.ui.auth.views


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
import com.example.luckyface.databinding.FragmentPasswordBinding
import com.example.luckyface.ui.auth.listner.AuthListner
import com.example.luckyface.ui.auth.viewmodel.AuthViewModel
import com.example.luckyface.ui.auth.viewmodel.AuthViewModelFactory
import com.example.luckyface.ui.main.MainActivity
import com.example.luckyface.util.hide
import com.example.luckyface.util.show
import com.example.luckyface.util.showToast
import kotlinx.android.synthetic.main.fragment_password.progress_bar
import kotlinx.android.synthetic.main.fragment_password.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class PasswordFragment : Fragment(), KodeinAware, AuthListner {

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
        val binding: FragmentPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        rootView = binding.root
        mTermsConditions = rootView.tv_instruction
        binding.viewModel = viewModel
        viewModel?.authListner = this
        animateView()

        return rootView
    }


    fun showVaildationError(message: String) {

        mTermsConditions.setText(message)
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        rootView.ll_layout.startAnimation(shake)
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
                showVaildationError("Please enter Password")
            }
            "INVALID_REQUEST" -> {
                showVaildationError("Please enter valid Password")
            }
            else -> {
                showToast(message)
            }
        }
    }

}
