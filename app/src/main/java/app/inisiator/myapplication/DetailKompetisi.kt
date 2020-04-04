package app.inisiator.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import app.inisiator.myapplication.models.QrNotif
import app.inisiator.myapplication.tab_notifikasi.NotifItem
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_detail_kompetisi.*
import kotlinx.android.synthetic.main.fragment_notifikasi_tab.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DetailKompetisi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kompetisi)

        val sessionManager = SessionManager(this)
        val currentUser = sessionManager.userDetail
        val userEmail = currentUser["EMAIL"]
        val userPass = currentUser["PASSWORD"]

        val namaKompetisi = intent.getStringExtra("NAMA_KOMP")

        init()

        submit_register_komp.setOnClickListener {
            checkForRegistrant(userEmail, userPass, namaKompetisi)
//            checkForBio(userEmail, userPass)
        }
    }

    private fun checkForRegistrant(userEmail: String?, userPass: String?, namaKompetisi: String?) {
        Log.d("DetailKomp", "Konfirmasi")
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/checkForRegistrant.php"
        val strRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            val obj = array.getJSONObject(i)
                            val email = obj.getString("email")
                            val namaKomp = obj.getString("nama_komp")
                            Log.d("DetailKomp", "Konfirmasi2")
                            if (email != userEmail && namaKomp != namaKompetisi ) {
                                checkForBio(userEmail, userPass)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener {
            it.printStackTrace()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return java.util.HashMap()
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(strRequest)
    }

    private fun checkForBio(userEmail: String?, userPass: String?) {
        Log.d("DetailKomp", "Check bio")
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/profile.php"
        val strReq = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val status = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("profile")

                        if (status == "1") {
                            for (i in 0 until jsonArray.length()) {
                                val obj = jsonArray.getJSONObject(i)
                                val balance = obj.getString("balance")
                                val bioStatus = obj.getString("bio_status")

                                if (bioStatus == "not filled") {
                                    Toast.makeText(this, "Silahkan Lengkapi Biodata Anda", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, CompleteBioActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, InsertFileKompetisi::class.java)
                                    startActivity(intent)
                                    finish()
                                    Log.d("DetailKomp", "silahkan insert file")
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }, Response.ErrorListener {  }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = userEmail!!
                params["password"] = userPass!!
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(strReq)
    }

    private fun init() {
        val namaKompetisi = intent.getStringExtra("NAMA_KOMP")
        val penyelenggara = intent.getStringExtra("PENYELENGGARA")
        val batasWaktuKomp = intent.getStringExtra("BATAS_WAKTU")
        val jenisKompetisi = intent.getStringExtra("JENIS_KOMP")
        val lokasiKompetisi = intent.getStringExtra("LOKASI_KOMP")
        val keteranganKomp = intent.getStringExtra("KETERANGAN")
        val imgkompetisi = intent.getStringExtra("IMG_KOMP")

        nama_komp_detail.text = namaKompetisi
        penyelenggara_komp_detail.text = penyelenggara

        if (jenisKompetisi == "gratis") {
            jenis_komp_detail.setTextColor(Color.GREEN)
            jenis_komp_detail.text = jenisKompetisi
        } else {
            jenis_komp_detail.text = jenisKompetisi
            jenis_komp_detail.setTextColor(Color.WHITE)
        }

        if (lokasiKompetisi == "") {
            lokasi_komp_detail.text = "-"
        } else {
            lokasi_komp_detail.text = lokasiKompetisi
        }

        bataswaktu_komp_detail.text = batasWaktuKomp
        keterangan_komp_detail.text = keteranganKomp
    }
}
