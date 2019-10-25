package com.example.luckyface.ui.main.profile.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luckyface.R
import com.example.luckyface.databinding.FragmentProfileBinding
import com.example.luckyface.ui.auth.views.AuthActivity
import com.example.luckyface.ui.main.profile.adaptor.PostsAdaptor
import com.example.luckyface.ui.main.profile.viewmodel.ProfileViewModel
import com.example.luckyface.ui.main.profile.viewmodel.ProfileViewModelFactory
import com.example.luckyface.ui.main.search.views.SearchFragment
import com.example.luckyface.widget.GridItemDecoration
import com.example.luckyface.util.loadImage
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.content_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private var viewModel: ProfileViewModel? = null
    private val factory: ProfileViewModelFactory by instance()
    private var layoutState: Boolean = false
    lateinit var rootView: View
    lateinit var llAccountInfo: LinearLayout
    lateinit var rl_logout: RelativeLayout
    lateinit var recycler_view: RecyclerView



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        rootView = binding.root
        binding.viewmodel = viewModel
        llAccountInfo = rootView.llAccountInfo
        rl_logout = rootView.rl_logout
        recycler_view = rootView.recycler_view

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
//        val activity = activity as MainActivity?
//        activity!!.setSupportActionBar(toolbar)

        return rootView
    }

    // first step helper function
    private fun showPopup() {
        val alert = AlertDialog.Builder(context!!)
        alert.setMessage("Are you sure?")
            .setPositiveButton("Logout") { _, _ ->
                logout() // Last step. Logout function
            }.setNegativeButton("Cancel", null)

        val alert1 = alert.create()
        alert1.show()
    }

    private fun logout() {

        viewModel?.deleteUser()
        Intent(activity, AuthActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun initView() {

        recycler_view.isNestedScrollingEnabled = false
        recycler_view.layoutManager = GridLayoutManager(activity!!, 3)

        //This will for default android divider
        recycler_view.addItemDecoration(GridItemDecoration(5, 3))

        val movieListAdapter = PostsAdaptor(context!!)
        recycler_view.adapter = movieListAdapter

    }

    companion object {

        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
