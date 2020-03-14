package app.inisiator.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val sessionManager = SessionManager(this)
        val currentUser = sessionManager.userDetail
        val userEmail = currentUser["EMAIL"]
        val userPass = currentUser["PASSWORD"]

        val eventTitle = intent.getStringExtra("EVENT_TITLE")
        val eventLokasi = intent.getStringExtra("EVENT_LOKASI")
        val eventTanggal = intent.getStringExtra("EVENT_TANGGAL")
        val eventWaktu = intent.getStringExtra("EVENT_WAKTU")
        val eventHarga = intent.getIntExtra("EVENT_HARGA", 0)

        fetchDetail(eventTitle, eventLokasi, eventTanggal, eventWaktu, eventHarga)

        rsvp.setOnClickListener {
            checkForBio(
                userEmail!!,
                userPass!!,
                eventTitle,
                eventLokasi,
                eventTanggal,
                eventWaktu,
                eventHarga
            )
        }
    }

    private fun checkForBio(
        userEmail: String?,
        userPass: String?,
        title: String?,
        lokasi: String?,
        tanggal: String?,
        waktu: String?,
        harga: Int?
    ) {
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
                                Toast.makeText(this, "Silahkan Lengkapi Biodata Anda Untuk Memesan Tiket", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, CompleteBioActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, TicketPaymentActivity::class.java)
                                intent.putExtra("EVENT_TITLE", title)
                                intent.putExtra("EVENT_LOKASI", lokasi)
                                intent.putExtra("EVENT_TANGGAL", tanggal)
                                intent.putExtra("EVENT_WAKTU", waktu)
                                intent.putExtra("EVENT_HARGA", harga)
                                intent.putExtra("MY_BALANCE", balance.toInt())
                                startActivity(intent)
                                finish()
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

    private fun fetchDetail(
        eventTitle: String?,
        eventLokasi: String?,
        eventTanggal: String?,
        eventWaktu: String?,
        eventHarga: Int?
    ) {
        title_eventdetail.text = eventTitle
        lokasi_eventdetail.text = eventLokasi
        tanggal_eventdetail.text = eventTanggal
        waktu_eventdetail.text = eventWaktu
        event_harga.text = "Rp. " + NumberFormat.getNumberInstance(Locale.US).format(eventHarga)
    }
}
