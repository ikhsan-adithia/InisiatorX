package app.inisiator.myapplication

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast (msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun logD (tag: String, msg: String) {
    Log.d(tag, msg)
}

fun logE (tag: String, msg: String) {
    Log.e(tag, msg)
}