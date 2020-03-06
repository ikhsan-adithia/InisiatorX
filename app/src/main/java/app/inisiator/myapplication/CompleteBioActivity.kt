package app.inisiator.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.inisiator.myapplication.R
import app.inisiator.myapplication.SessionManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_complete_bio.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class CompleteBioActivity : AppCompatActivity() {

    private val calender: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_bio)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, month)
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        date_picker_cbio.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show()
        }

        submit_cbio.setOnClickListener {
            val checkTempatLahir = tempat_lahir_cbio.selectedItem.toString() == "Pilih Kota"
            val checkTanggalLahir = dot_text_cbio.text == "-- / -- / ----"
            val checkTempatTinggal = kota_cbio.selectedItem.toString() == "Pilih Kota"
            val checkAlamat = alamat_cbio.text.toString() == ""
            val checkKelamin = jenis_kelamin_cbio.selectedItem.toString() == "Pilih Jenis Kelamin"
            val checkAgama = agama_cbio.selectedItem.toString() == "Pilih Agama"
            val status = status_cbio.selectedItem.toString()
            if (checkTempatLahir) {
                Toast.makeText(this, "Silahkan Pilih Kota Lahir Anda", Toast.LENGTH_SHORT).show()
            } else {
                if (checkTanggalLahir) {
                    Toast.makeText(this, "Silahkan Pilih Tanggal Lahir Anda", Toast.LENGTH_SHORT).show()
                } else {
                    if (checkTempatTinggal) {
                        Toast.makeText(this, "Silahkan Pilih Kota Tempat Tinggal", Toast.LENGTH_SHORT).show()
                    } else {
                        if (checkAlamat) {
                            Toast.makeText(this, "Silahkan Masukkan Alamat Anda", Toast.LENGTH_SHORT).show()
                        } else {
                            if (checkKelamin) {
                                Toast.makeText(this, "Silahkan Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show()
                            } else {
                                if (checkAgama) {
                                    Toast.makeText(this, "Silahkan Pilih Agama", Toast.LENGTH_SHORT).show()
                                } else {
                                    sendDataToDatabase(status)
//                                    if (checkStatus) {
//                                        android.widget.Toast.makeText(this, "Silahkan Pilih Status", android.widget.Toast.LENGTH_SHORT).show()
//                                    } else if (!checkStatus) {
//                                        sendDataToDatabase()
//                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sendDataToDatabase(status: String) {
        val tempatLahir = tempat_lahir_cbio.selectedItem.toString()
        val tanggalLahir = dot_text_cbio.text.toString()
        val tempatTinggal = kota_cbio.selectedItem.toString()
        val alamat = alamat_cbio.text.toString()
        val kelamin = jenis_kelamin_cbio.selectedItem.toString()
        val agama = agama_cbio.selectedItem.toString()

        var currentStatus: String
        if (status == "Pilih Status") {
            currentStatus = "Umum"
        } else {
            currentStatus = status
        }

        val sessionManager = SessionManager(this)
        val user: HashMap<String, String> = sessionManager.userDetail
        val userEmail = user["EMAIL"]

        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/insertBio.php"
        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener {
                try {
                    val jsonObject = JSONObject(it)
                    val success = jsonObject.getString("success")
                    if (success == "1") {
                        finish()
                        Toast.makeText(this, "Biodata telah diperbaharui", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {

            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = userEmail!!
                params["pob"] = tempatLahir
                params["dob"] = tanggalLahir
                params["kota"] = tempatTinggal
                params["alamat"] = alamat
                params["jenis_kelamin"] = kelamin
                params["agama"] = agama
                params["status"] = currentStatus
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dot_text_cbio.text = sdf.format(calender.time)
    }
}
