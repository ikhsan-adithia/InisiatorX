package app.inisiator.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_pin.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

val URL_KODE = "https://awalspace.com/app/imbalopunyajangandiganggu/kode.php"
val URL_PIN = "https://awalspace.com/app/imbalopunyajangandiganggu/pin.php"
val URL_MERCHANT = "https://awalspace.com/app/imbalopunyajangandiganggu/get_merchant.php"

class Pin : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var list_of_items = arrayOf("Ukuran", "44", "58")
    var list_of_items2 = arrayOf("Jenis Laminasi", "Glossy", "Doff", "Canvas")
    var bitmap: Bitmap? = null
    var isimage = false
    var xx = true
    var arrayList: ArrayList<String>? = null
    private lateinit var profilee: CircleImageView

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        loadmerchant()
        arrayList = ArrayList<String>()
        profilee = findViewById<View>(R.id.profileImageViewRequestPin) as CircleImageView

        profilee.setOnClickListener {
            if (btnsubmit.visibility == View.INVISIBLE) {
                Toast.makeText(this, "Hitung Total Harga Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                chooseFile()
            }
        }

        btncheck.setOnClickListener {

            val kodetext = kode.text.toString()

            if (spinner.selectedItem.toString().equals("Ukuran")) {
                Toast.makeText(this, "Silahkan Isi Ukuran", Toast.LENGTH_SHORT).show()
            } else if (spinner.selectedItem.toString().equals("44")) {
                if (spinner2.selectedItem.toString().equals("Jenis Laminasi")) {
                    Toast.makeText(this, "Silahkan Isi Laminasi", Toast.LENGTH_SHORT).show()
                } else if (spinner2.selectedItem.toString().equals("Glossy")) {
                    if (xx == true) {
                        xx = false
                        Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                    } else {
                        val total: Int
                        val totall: String
                        val value = titJumlah.text.toString()
                        val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                        if (finalValue < 5) {
                            total = finalValue * 3500
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 5 && finalValue < 11) {

                            total = finalValue * 3000
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 10 && finalValue < 21) {

                            total = finalValue * 2500
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 20 && finalValue < 51) {
                            total = finalValue * 1900
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 50 && finalValue < 101) {
                            total = finalValue * 1800
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 100 && finalValue < 201) {
                            total = finalValue * 1600
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 200 && finalValue < 501) {
                            total = finalValue * 1500
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 500) {
                            total = finalValue * 1400
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        }
                    }

                } else {
                    if (xx == true) {
                        xx = false
                        Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                    } else {
                        val total: Int
                        val totall: String
                        val value = titJumlah.text.toString()
                        val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                        if (finalValue < 5) {
                            total = finalValue * 3700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 5 && finalValue < 11) {

                            total = finalValue * 3200
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 10 && finalValue < 21) {

                            total = finalValue * 2700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 20 && finalValue < 51) {
                            total = finalValue * 2100
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 50 && finalValue < 101) {
                            total = finalValue * 2000
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 100 && finalValue < 201) {
                            total = finalValue * 1800
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 200 && finalValue < 501) {
                            total = finalValue * 1700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 500) {
                            total = finalValue * 1600
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        }
                    }
                }
            } else {
                if (spinner2.selectedItem.toString().equals("Jenis Laminasi")) {
                    Toast.makeText(this, "Silahkan Isi Laminasi", Toast.LENGTH_SHORT).show()
                } else if (spinner2.selectedItem.toString().equals("Glossy")) {
                    if (xx == true) {
                        xx = false
                        Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                    } else {
                        val total: Int
                        val totall: String
                        val value = titJumlah.text.toString()
                        val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                        if (finalValue < 5) {
                            total = finalValue * 3500
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 5 && finalValue < 11) {

                            total = finalValue * 3000
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 10 && finalValue < 21) {

                            total = finalValue * 2500
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 20 && finalValue < 51) {
                            total = finalValue * 2100
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 50 && finalValue < 101) {
                            total = finalValue * 2000
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 100 && finalValue < 201) {
                            total = finalValue * 1800
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 200 && finalValue < 501) {
                            total = finalValue * 1700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 500) {
                            total = finalValue * 1600
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        }
                    }
                } else {
                    if (xx == true) {
                        xx = false
                        Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                    } else {
                        val total: Int
                        val totall: String
                        val value = titJumlah.text.toString()
                        val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                        if (finalValue < 5) {
                            total = finalValue * 3700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 5 && finalValue < 11) {

                            total = finalValue * 3200
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 10 && finalValue < 21) {

                            total = finalValue * 2700
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 20 && finalValue < 51) {
                            total = finalValue * 2300
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 50 && finalValue < 101) {
                            total = finalValue * 2200
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 100 && finalValue < 201) {
                            total = finalValue * 2000
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 200 && finalValue < 501) {
                            total = finalValue * 1900
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        } else if (finalValue > 500) {
                            total = finalValue * 1800
                            totall = total.toString()
                            textView9.text = totall
                            spinner.isEnabled = false
                            spinner2.isEnabled = false
                            spinner3.isEnabled = false
                            kode.isEnabled = false
                            titJumlah.isEnabled = false
                            btncheck.visibility = View.GONE
                            linear2.visibility = View.VISIBLE
                            linear3.visibility = View.VISIBLE
                            linear4.visibility = View.VISIBLE
                            btnsubmit.visibility = View.VISIBLE
                            btnedit.visibility = View.VISIBLE
                            hide()
                            checkkode(kodetext, total)
                        }
                    }
                }
            }
        }

        spinner2!!.onItemSelectedListener = this
        val array_adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items2)
        array_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2!!.adapter = array_adapter2

        spinner!!.onItemSelectedListener = this
        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = array_adapter

        btnsubmit.setOnClickListener {
            if (isimage == false) {
                Toast.makeText(this, "Silahkan Upload Desain Kamu", Toast.LENGTH_SHORT).show()
            } else {
                if (textView11.text.equals("0")) {
                    Toast.makeText(this, "Minimal Pemesanan adalah 1 buah", Toast.LENGTH_SHORT).show()
                } else {
                    val main = findViewById<ScrollView>(R.id.main)
                    val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                    main.visibility = View.INVISIBLE
                    spin_kit.visibility = View.VISIBLE
                    var sessionManager = SessionManager(this)
                    var user: HashMap<String, String> = sessionManager.userDetail
                    var emaill = user.get("EMAIL")
                    var harga = textView11.text.toString()
                    var ukuran = spinner.selectedItem.toString()
                    var laminasi = spinner2.selectedItem.toString()
                    var jumlah = titJumlah.text.toString()
                    var merchant = spinner3.selectedItem.toString()
                    var kodes = kode.text.toString()
                    UploadPicture(emaill!!, bitmap?.let { getStringImage(it) }!!, harga, ukuran, laminasi, jumlah, merchant, kodes)
                }
            }
        }

        btnedit.setOnClickListener {
            spinner.isEnabled = true
            spinner2.isEnabled = true
            kode.isEnabled = true
            titJumlah.isEnabled = true
            spinner3.isEnabled = true
            btncheck.visibility = View.VISIBLE
            linear2.visibility = View.INVISIBLE
            linear3.visibility = View.INVISIBLE
            linear4.visibility = View.INVISIBLE
            btnsubmit.visibility = View.INVISIBLE
            btnedit.visibility = View.INVISIBLE
        }
    }

    private fun checkkode(kode: String, totall: Int) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_KODE,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("check")
                        if (success.equals("1")) {
                            for (i in 0 until jsonArray.length()) {
                                var `object` = jsonArray.getJSONObject(i)
                                var potongan = `object`.getInt("potongan")
                                textView10.text = potongan.toString()
                                val mmnt = Integer.parseInt(potongan.toString())
                                hitung(totall, mmnt)
                            }
                        }
                        else if(success.equals("2"))
                        {
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Promo Sudah Tidak Bisa Dipakai", Toast.LENGTH_SHORT).show()
                            textView10.text = "0"
                            hitung(totall, 0)
                        }
                        else if (success.equals("0")) {
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                            textView10.text = "0"
                            hitung(totall, 0)
                        }
                        else if(success.equals("3")){
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Promo Tidak Bisa Dipakai Karena Tidak Mencukupi Batas Minimal Harga", Toast.LENGTH_SHORT).show()
                            textView10.text = "0"
                            hitung(totall, 0)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var sessionManager = SessionManager(this@Pin)
                var user: HashMap<String, String> = sessionManager.userDetail
                var emaill = user.get("EMAIL")
                val params = java.util.HashMap<String, String>()
                params.put("kode", kode)
                params.put("email", emaill.toString())
                params.put("total", totall.toString())
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun hitung(totall: Int, kode: Int) {
        val awal = totall
        val akhir = kode
        val ttlbyr = awal - akhir
        if (ttlbyr <= 0) {
            textView11.text = "0"
        } else {
            textView11.text = ttlbyr.toString()
        }
        textView7.visibility = View.VISIBLE
        linear1.visibility = View.VISIBLE
        Handler().postDelayed({
            val main = findViewById<ScrollView>(R.id.main)
            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.VISIBLE
            spin_kit.visibility = View.INVISIBLE
        }, 1000)
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === Activity.RESULT_OK && data != null && data.data != null) {
            val filepath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(baseContext.contentResolver, filepath)
                profilee.setImageBitmap(bitmap)
                isimage = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var sessionManager = SessionManager(this)
            var user: HashMap<String, String> = sessionManager.userDetail
        }
    }

    private fun UploadPicture(email: String, photo: String, harga: String, ukuran: String, laminasi: String, jumlah: String, merchant: String, kode: String) {


        val stringRequest = object : StringRequest(Request.Method.POST, URL_PIN,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success.equals("1")) {
                            Handler().postDelayed({
                                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                                spin_kit.visibility = View.GONE
                                startActivity(Intent(this, Success::class.java))
                            }, 3000)
                        }
                        else{
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                                main.visibility = View.VISIBLE
                                spin_kit.visibility = View.INVISIBLE
                                Toast.makeText(this, "Saldo Anda Tidak Mencukupi", Toast.LENGTH_SHORT).show()
                            }, 3000)
                        }

                    } catch (e: JSONException) {
                        Handler().postDelayed({
                            e.printStackTrace()
                            val main = findViewById<ScrollView>(R.id.main)
                            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                            main.visibility = View.VISIBLE
                            spin_kit.visibility = View.INVISIBLE
                            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
                        }, 3000)
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params.put("email", email)
                params.put("photo", photo)
                params.put("harga", harga)
                params.put("ukuran", ukuran)
                params.put("laminasi", laminasi)
                params.put("jumlah", jumlah)
                params.put("merchant", merchant)
                params.put("kode", kode)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    fun getStringImage(bitmap: Bitmap): String? {

        val bytearrayoutputstream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream)
        var imageByteArray = bytearrayoutputstream.toByteArray()
        var encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT)


        return encodedImage
    }

    private fun loadmerchant() {
        val stringRequest = object : StringRequest(Request.Method.GET, URL_MERCHANT,
                Response.Listener { response ->
                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                                main.visibility = View.VISIBLE
                                spin_kit.visibility = View.INVISIBLE
                                var `merchant` = array.getJSONObject(i)
                                arrayList!!.add(merchant.getString("nama_merchant"))
                                spinner3!!.onItemSelectedListener = this
                                val array_adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList!!)
                                array_adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinner3!!.adapter = array_adapter3
                            }, 3000)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    private fun hide(){
        val main = findViewById<ScrollView>(R.id.main)
        val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
        main.visibility = View.INVISIBLE
        spin_kit.visibility = View.VISIBLE
    }
}
