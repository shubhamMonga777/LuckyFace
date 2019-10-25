package com.example.luckyface.ui.auth.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.luckyface.R
import com.example.luckyface.util.addFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)



        addFragment(EmailFragment(), R.id.root_layout, false)

    }


}
