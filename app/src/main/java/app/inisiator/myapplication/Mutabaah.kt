package app.inisiator.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_beranda.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class Mutabaah : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutabaah)

        val date = findViewById<TextView>(R.id.subsub)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("d MMM yyyy")
        val datee = dateFormat.format(calendar.getTime())
        date.setText("TODAY TASK TO DO  "+datee)

        var sessionManager = SessionManager(this)
        val user: HashMap<String, String> = sessionManager.userDetail
        val no = user.get("EMAIL")
        check(no!!)

        val dhuha = findViewById<LinearLayout>(R.id.dhuha)
        val tahajud = findViewById<LinearLayout>(R.id.tahajud)
        val infaq = findViewById<LinearLayout>(R.id.infaq)
        val tilawah = findViewById<LinearLayout>(R.id.tilawah)
        val puasa = findViewById<LinearLayout>(R.id.puasa)

        dhuha.setOnClickListener {
            if (dhuha.isEnabled == false) {}
            else{
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Jujur Dong!")
                        .setMessage("Kamu yakin sudah melaksanakannya?, kalau bohong dosa loh!. Masa sama diri sendiri bohong")
                        .setPositiveButton("Yakin") { _, _ ->
                            add("dhuha", no)
                        }
                        .setNegativeButton("Tidak") { _, _ -> }
                alertDialog.show()
            }
        }

        tahajud.setOnClickListener {
            if (tahajud.isEnabled == false) {}
            else{
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Jujur Dong!")
                        .setMessage("Kamu yakin sudah melaksanakannya?, kalau bohong dosa loh!. Masa sama diri sendiri bohong")
                        .setPositiveButton("Yakin") { _, _ ->
                            add("tahajud", no)
                        }
                        .setNegativeButton("Tidak") { _, _ -> }
                alertDialog.show()
            }
        }

        puasa.setOnClickListener {
            if (puasa.isEnabled == false) {}
            else{
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Jujur Dong!")
                        .setMessage("Kamu yakin sudah melaksanakannya?, kalau bohong dosa loh!. Masa sama diri sendiri bohong")
                        .setPositiveButton("Yakin") { _, _ ->
                            add("puasa", no)
                        }
                        .setNegativeButton("Tidak") { _, _ -> }
                alertDialog.show()
            }
        }

        tilawah.setOnClickListener {
            if (tilawah.isEnabled == false) {}
            else{
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Jujur Dong!")
                        .setMessage("Kamu yakin sudah melaksanakannya?, kalau bohong dosa loh!. Masa sama diri sendiri bohong")
                        .setPositiveButton("Yakin") { _, _ ->
                            add("tilawah", no)
                        }
                        .setNegativeButton("Tidak") { _, _ -> }
                alertDialog.show()
            }
        }

        infaq.setOnClickListener {
            if (infaq.isEnabled == false) {}
            else{
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Jujur Dong!")
                        .setMessage("Kamu yakin sudah melaksanakannya?, kalau bohong dosa loh!. Masa sama diri sendiri bohong")
                        .setPositiveButton("Yakin") { _, _ ->
                            add("infaq", no)
                        }
                        .setNegativeButton("Tidak") { _, _ -> }
                alertDialog.show()
            }
        }
    }

    fun check(no: String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/mutabaah.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("mutabaah")
                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                val `object` = jsonArray.getJSONObject(i)
                                val isdhuha = `object`.getString("isdhuha").trim { it <= ' ' }
                                val istahajud = `object`.getString("istahajud").trim { it <= ' ' }
                                val isinfaq = `object`.getString("isinfaq").trim { it <= ' ' }
                                val istilawah = `object`.getString("istilawah").trim { it <= ' ' }
                                val ispuasa = `object`.getString("ispuasa").trim { it <= ' ' }

                                if (isdhuha.equals("true"))
                                {
                                    val dhuha = findViewById<LinearLayout>(R.id.dhuha)
                                    val checkdhuha = findViewById<ImageView>(R.id.checkdhuha)
                                    dhuha.isEnabled = false
                                    checkdhuha.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (istahajud.equals("true"))
                                {
                                    val tahajud = findViewById<LinearLayout>(R.id.tahajud)
                                    val checktahajud = findViewById<ImageView>(R.id.checktahajud)
                                    tahajud.isEnabled = false
                                    checktahajud.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (isinfaq.equals("true"))
                                {
                                    val infaq = findViewById<LinearLayout>(R.id.infaq)
                                    val checkinfaq = findViewById<ImageView>(R.id.checkinfaq)
                                    infaq.isEnabled = false
                                    checkinfaq.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (istilawah.equals("true"))
                                {
                                    val tilawah = findViewById<LinearLayout>(R.id.tilawah)
                                    val checktilawah = findViewById<ImageView>(R.id.checktilawah)
                                    tilawah.isEnabled = false
                                    checktilawah.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }
                                if (ispuasa.equals("true"))
                                {
                                    val puasa = findViewById<LinearLayout>(R.id.puasa)
                                    val checkpuasa = findViewById<ImageView>(R.id.checkpuasa)
                                    puasa.isEnabled = false
                                    checkpuasa.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }
                            }
                        }
                        else if(success == "2")
                        {
                        }
                        else{
                            val bottomSheetDialog = BottomSheetDialog(
                                    this, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(this)
                                    .inflate(
                                            R.layout.layout_bottom_notif,
                                            null
                                    )
                            bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
                            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
                            bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                                bottomSheetDialog.cancel()
                                onResume()
                            }
                            bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                                bottomSheetDialog.cancel()
                                onResume()
                            }
                            bottomSheetDialog.setContentView(bottomSheetView)
                            bottomSheetDialog.show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val bottomSheetDialog = BottomSheetDialog(
                                this, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(this)
                                .inflate(
                                        R.layout.layout_bottom_notif,
                                        null
                                )
                        bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
                        bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
                        bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                            bottomSheetDialog.cancel()
                            onResume()
                        }
                        bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                            bottomSheetDialog.cancel()
                            onResume()
                        }
                        bottomSheetDialog.setContentView(bottomSheetView)
                        bottomSheetDialog.show()
                    }
                }, Response.ErrorListener {
            val bottomSheetDialog = BottomSheetDialog(
                    this, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this)
                    .inflate(
                            R.layout.layout_bottom_notif,
                            null
                    )
            bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
            bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                bottomSheetDialog.cancel()
                onResume()
            }
            bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                bottomSheetDialog.cancel()
                onResume()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["no"] = no
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }



    fun add(type: String, no: String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/addmutabaah.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {

                                if (type.equals("dhuha"))
                                {
                                    val dhuha = findViewById<LinearLayout>(R.id.dhuha)
                                    val checkdhuha = findViewById<ImageView>(R.id.checkdhuha)
                                    dhuha.isEnabled = false
                                    checkdhuha.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (type.equals("tahajud"))
                                {
                                    val tahajud = findViewById<LinearLayout>(R.id.tahajud)
                                    val checktahajud = findViewById<ImageView>(R.id.checktahajud)
                                    tahajud.isEnabled = false
                                    checktahajud.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (type.equals("infaq"))
                                {
                                    val infaq = findViewById<LinearLayout>(R.id.infaq)
                                    val checkinfaq = findViewById<ImageView>(R.id.checkinfaq)
                                    infaq.isEnabled = false
                                    checkinfaq.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }

                                if (type.equals("tilawah"))
                                {
                                    val tilawah = findViewById<LinearLayout>(R.id.tilawah)
                                    val checktilawah = findViewById<ImageView>(R.id.checktilawah)
                                    tilawah.isEnabled = false
                                    checktilawah.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }
                                if (type.equals("puasa"))
                                {
                                    val puasa = findViewById<LinearLayout>(R.id.puasa)
                                    val checkpuasa = findViewById<ImageView>(R.id.checkpuasa)
                                    puasa.isEnabled = false
                                    checkpuasa.setImageDrawable(resources.getDrawable(R.drawable.ic_listcheck))
                                }
                        }
                        else{
                            val bottomSheetDialog = BottomSheetDialog(
                                    this, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(this)
                                    .inflate(
                                            R.layout.layout_bottom_notif,
                                            null
                                    )
                            bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
                            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
                            bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                                bottomSheetDialog.cancel()
                                onResume()
                            }
                            bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                                bottomSheetDialog.cancel()
                                onResume()
                            }
                            bottomSheetDialog.setContentView(bottomSheetView)
                            bottomSheetDialog.show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val bottomSheetDialog = BottomSheetDialog(
                                this, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(this)
                                .inflate(
                                        R.layout.layout_bottom_notif,
                                        null
                                )
                        bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
                        bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
                        bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                            bottomSheetDialog.cancel()
                            onResume()
                        }
                        bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                            bottomSheetDialog.cancel()
                            onResume()
                        }
                        bottomSheetDialog.setContentView(bottomSheetView)
                        bottomSheetDialog.show()
                    }
                }, Response.ErrorListener { _ ->
            val bottomSheetDialog = BottomSheetDialog(
                    this, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(this)
                    .inflate(
                            R.layout.layout_bottom_notif,
                            null
                    )
            bottomSheetView.findViewById<TextView>(R.id.title).setText("Terjadi Kesalahan!")
            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, telah terjadi kesalahan pada sistem kami. Silahkan tunggu beberapa saat dan coba lagi.")
            bottomSheetView.findViewById<View>(R.id.close).setOnClickListener {
                bottomSheetDialog.cancel()
                onResume()
            }
            bottomSheetView.findViewById<View>(R.id.close2).setOnClickListener {
                bottomSheetDialog.cancel()
                onResume()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["type"] = type
                params["no"] = no
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
