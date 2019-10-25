package com.example.luckyface.util


import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.luckyface.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


inline fun FragmentManager.inTransaction(boolean: Boolean, name: String, func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
    if (boolean) {
        fragmentTransaction.addToBackStack(name)
    }
    fragmentTransaction.commit()

}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean) {
    supportFragmentManager.inTransaction(addToBackStack, fragment.javaClass.name) { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean) {
    supportFragmentManager.inTransaction(addToBackStack, fragment.javaClass.name) { replace(frameId, fragment) }
}

fun Fragment.replaceFragmeent(fragment: Fragment, frameId: Int, addToBackStack: Boolean) {
    fragmentManager?.inTransaction(addToBackStack, fragment.javaClass.name) { replace(frameId, fragment) }
}

fun Fragment.remove(fragment: Fragment) {
    fragmentManager?.inTransaction(false, fragment.javaClass.name) { remove(fragment) }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun Fragment.initToolbar(toolbar: Toolbar, titleResId: Int, backEnabled: Boolean) {
    val appCompatActivity = activity as AppCompatActivity
    appCompatActivity.setSupportActionBar(toolbar)
    appCompatActivity.supportActionBar?.setTitle(titleResId)
    appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
}

fun createTextBody(text: String): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), text)
}

fun createMultiPart(path: String, key: String): MultipartBody.Part {
    val file = File(path)
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    return MultipartBody.Part.createFormData(key, file.getName(), requestFile)
}

fun Context.loadImage(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .into(imageView)
}
fun Fragment.loadImage(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .into(imageView)
}



