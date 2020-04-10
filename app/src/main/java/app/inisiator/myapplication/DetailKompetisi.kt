package app.inisiator.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detail_kompetisi.*
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
        val jenisKompetisi = intent.getStringExtra("JENIS_KOMP")

        init()

        submit_register_komp.setOnClickListener {
            checkForRegistrant(userEmail, userPass, namaKompetisi, jenisKompetisi)
        }
    }

    private fun checkForRegistrant(userEmail: String?, userPass: String?, namaKompetisi: String?, jenisKompetisi: String?) {
        Log.d("DetailKomp", "Konfirmasi")
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/checkForRegistrant.php"
        val strReq = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val status = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("profile")

                        if (status == "1") {
                            Log.d("DetailKomp", "1")
                            for (i in 0 until jsonArray.length()) {
                                val obj = jsonArray.getJSONObject(i)
                                val email = obj.getString("email")
                                val namaKomp = obj.getString("nama_komp")

                                Log.d("DetailKomp", "check registrant")

                                if (email != userEmail && namaKomp != namaKompetisi) {
                                    Log.d("DetailKomp", "Check bio")
                                    checkForBio(userEmail, userPass, jenisKompetisi)
                                } else {
                                    Log.d("DetailKomp", "Sudah register")
                                    Toast.makeText(this, "Sudah Registrasi", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else if (status == "0") {
                            Log.d("DetailKomp", "0")
                            checkForBio(userEmail, userPass, jenisKompetisi)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }, Response.ErrorListener {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = userEmail!!
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(strReq)
    }

    private fun checkForBio(userEmail: String?, userPass: String?, jenisKompetisi: String?) {
        Log.d("DetailKomp", "Check bio")
//        var harga: Int= 0
//        if (jenisKompetisi != "gratis") {
//            harga = jenisKompetisi!!.toInt()
//        }

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
                                    if (jenisKompetisi != "gratis") {
                                        val harga: Int = jenisKompetisi!!.toInt()
                                        if (balance.toInt() < harga) {
                                            Toast.makeText(this, "CoinX anda tidak cukup", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        val namaKompetisi = intent.getStringExtra("NAMA_KOMP")
                                        val intent = Intent(this, InsertFileKompetisi::class.java)
                                        intent.putExtra("NAMA_KOMP", namaKompetisi)
                                        startActivity(intent)
                                        finish()
                                        Log.d("DetailKomp", "silahkan insert file")
                                    }
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
//        val imgkompetisi = intent.getStringExtra("IMG_KOMP")

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
