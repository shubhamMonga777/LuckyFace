package com.example.luckyface.ui.main.profile.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luckyface.R
import com.example.luckyface.databinding.ActivityProfileBinding
import com.example.luckyface.ui.auth.views.AuthActivity
import com.example.luckyface.ui.main.profile.adaptor.PostsAdaptor
import com.example.luckyface.ui.main.profile.viewmodel.ProfileViewModel
import com.example.luckyface.ui.main.profile.viewmodel.ProfileViewModelFactory
import com.example.luckyface.widget.GridItemDecoration
import com.example.luckyface.util.loadImage
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class ProfileActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private var viewModel: ProfileViewModel? = null
    private val factory: ProfileViewModelFactory by instance()
    private var layoutState: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel

        llAccountInfo.setOnClickListener {
            if (layoutState) {

                expandable_layout.collapse(true)
                ivArrow.animate().rotation(0f).start()

                layoutState = false
            } else {
                layoutState = true
                expandable_layout.expand(true)
                ivArrow.animate().rotation(180F).start()


            }

        }

        viewModel?.getLoggedInUser()?.observe(this, Observer { user ->

            user?.let {

                tv_name.text = user.display_name
                tv_email.text = user.email
                nameTextView.text = user.display_name
                emailTextView.text = user.email
                addressTextView.text = "${user.state} ${user.city}  ${user.pincode}"
                loadImage(user.org_image!!, profile_image)
            }
        })

        rl_logout.setOnClickListener {
            showPopup()
        }

        initView()
        setSupportActionBar(toolbar)
    }

    // first step helper function
    private fun showPopup() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage("Are you sure?")
            .setPositiveButton("Logout") { _, _ ->
                logout() // Last step. Logout function
            }.setNegativeButton("Cancel", null)

        val alert1 = alert.create()
        alert1.show()
    }

    private fun logout() {

        viewModel?.deleteUser()
        Intent(this, AuthActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun initView() {

        recycler_view.isNestedScrollingEnabled = false
        recycler_view.layoutManager = GridLayoutManager(this, 3)

        //This will for default android divider
        recycler_view.addItemDecoration(GridItemDecoration(5, 3))

        val movieListAdapter = PostsAdaptor(this)
        recycler_view.adapter = movieListAdapter

    }
}