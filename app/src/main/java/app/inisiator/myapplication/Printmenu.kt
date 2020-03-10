package app.inisiator.myapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONArray
import org.json.JSONException

class Printmenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printmenu)
        val topdf = findViewById<Button>(R.id.btntopdf)
        val combine = findViewById<Button>(R.id.btncombine)
        val print = findViewById<Button>(R.id.btnprint)

        topdf.setOnClickListener {
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://topdf.com/"))
            startActivity(browserIntent)
        }

        combine.setOnClickListener {
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://combinepdf.com/"))
            startActivity(browserIntent)
        }

        print.setOnClickListener {
            startActivity(Intent(this, Print::class.java))
        }
    }
}

