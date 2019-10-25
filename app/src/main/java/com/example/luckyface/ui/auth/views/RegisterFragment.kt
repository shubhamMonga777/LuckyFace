package com.example.luckyface.ui.auth.views


import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.luckyface.R
import com.example.luckyface.databinding.FragmentRegisterBinding
import com.example.luckyface.ui.auth.listner.AuthListner
import com.example.luckyface.util.ManagePermissions
import com.example.luckyface.util.OneForAll
import com.example.luckyface.util.replaceFragmeent
import com.example.luckyface.util.showToast
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

class RegisterFragment : Fragment(), View.OnClickListener,
    BottomSheetImagePicker.OnImagesSelectedListener, KodeinAware, AuthListner {


    private var mImage: CircleImageView? = null
    private lateinit var rootView: View
    private lateinit var mNext: Button

    override val kodein by kodein()
    private var managePermissions: ManagePermissions? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        rootView = binding.root
        managePermissions = ManagePermissions(activity!!)
        mImage = rootView.profile_image
        mImage?.setOnClickListener(this)
        binding.handler = DataHandler()
        DataHandler.authListner = this
        animateView()

        return rootView
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.profile_image -> {
                permissionSafetyMethod()
            }
        }
    }


    private fun permissionSafetyMethod() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (managePermissions!!.checkPermissions()) openPicker()
        } else openPicker()

    }

    private fun openPicker() {

        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .cameraButton(ButtonType.Button)       //style of the camera link (Button in header, Image tile, None)
            .galleryButton(ButtonType.Button)     //style of the gallery link
            .singleSelectTitle(R.string.pick_single)    //header text
            .requestTag("single")     //tag can be used if multiple pickers are used
            .show(childFragmentManager)
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

    // Receive the permissions request result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                val isPermissionsGranted = managePermissions!!
                    .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    openPicker()
                } else {
                    managePermissions!!.requestPermissions()
                }
                return
            }
        }
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {

        mImage?.setImageURI(uris[0])
        DataHandler.path = uris[0].path!!
    }

    fun showVaildationError() {

        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        rootView.ll_layout.startAnimation(shake)
    }

    override fun onStarted() {}

    override fun onSucess() {
        replaceFragmeent(RegisterP2Fragment(), R.id.root_layout, true)
    }

    override fun onFailure(message: String) {
        showVaildationError()
        showToast(message)
    }

    class DataHandler {

        var name: String? = null
        var password: String? = null
        var confirmPassword: String? = null

        fun nextButtonClicked(view: View) {
            if (name.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                authListner?.onFailure("All fields are mandetory")
                return
            }

            if (path.isNullOrEmpty()) {
                authListner?.onFailure("Please select profile image")
                return
            }

            if (password != confirmPassword) {
                authListner?.onFailure("Passwords did not match")
                return
            }

            OneForAll.getInstance().name = this.name
            OneForAll.getInstance().password = this.password
            OneForAll.getInstance().path =
                path
            authListner?.onSucess()

        }

        companion object {
            var path: String? = null
            var authListner: AuthListner? = null

        }
    }

}
