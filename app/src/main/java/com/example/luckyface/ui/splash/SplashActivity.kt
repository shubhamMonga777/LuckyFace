package com.example.luckyface.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.luckyface.R
import com.example.luckyface.ui.auth.views.AuthActivity
import com.example.luckyface.util.OneForAll
import com.example.luckyface.util.Result
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    var mName: TextView? = null
    var mProceed: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mName = this.tv_name
        mProceed = this.btn_procced

        animateView()

        mProceed?.setOnClickListener {

            Intent(this, AuthActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        Result("ddd")
    }

    fun sum(): Int {
        return 1
    }

    private fun animateView() {

        val aniFade = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        mProceed?.startAnimation(aniFade)

        val bottomUp = AnimationUtils.loadAnimation(
            this,
            R.anim.bottom_up
        )
        val hiddenPanel = findViewById<LinearLayout>(R.id.ll_layout)
        hiddenPanel.startAnimation(bottomUp)
        hiddenPanel.visibility = View.VISIBLE

    }
}
