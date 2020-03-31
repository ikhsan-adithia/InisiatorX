package app.inisiator.myapplication

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_ganci.*
import kotlinx.android.synthetic.main.activity_ganci.btncheck
import kotlinx.android.synthetic.main.activity_ganci.btnedit
import kotlinx.android.synthetic.main.activity_ganci.btnsubmit
import kotlinx.android.synthetic.main.activity_ganci.kode
import kotlinx.android.synthetic.main.activity_ganci.linear1
import kotlinx.android.synthetic.main.activity_ganci.linear2
import kotlinx.android.synthetic.main.activity_ganci.linear3
import kotlinx.android.synthetic.main.activity_ganci.linear4
import kotlinx.android.synthetic.main.activity_ganci.spinner
import kotlinx.android.synthetic.main.activity_ganci.spinner2
import kotlinx.android.synthetic.main.activity_ganci.spinner3
import kotlinx.android.synthetic.main.activity_ganci.textView10
import kotlinx.android.synthetic.main.activity_ganci.textView11
import kotlinx.android.synthetic.main.activity_ganci.textView7
import kotlinx.android.synthetic.main.activity_ganci.titJumlah
import kotlinx.android.synthetic.main.activity_pin.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

const val URL_KODE1 = "https://awalspace.com/app/imbalopunyajangandiganggu/kode.php"
const val URL_GANCI = "https://awalspace.com/app/imbalopunyajangandiganggu/ganci.php"
const val URL_GANCI2 = "https://awalspace.com/app/imbalopunyajangandiganggu/ganci2.php"
const val URL_MERCHANT1 = "https://awalspace.com/app/imbalopunyajangandiganggu/get_merchant.php"

class Ganci : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var arrayList: ArrayList<String>? = null

    private var listOfItemsSize = arrayOf("Ukuran", "44", "58")
    private var listOfItemsType = arrayOf("Jenis Laminasi", "Glossy", "Doff", "Canvas")
    private var listOfItemsCategory = arrayOf("Jenis Ganci", "Biasa", "Botol", "Boneka")
    private var bitmap3: Bitmap? = null
    private var bitmap2: Bitmap? = null
    private var isimage = false
    private var isimage2 = false
    private var xx = true
    private lateinit var profilee: CircleImageView
    private lateinit var profilee2: CircleImageView

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganci)
        loadMerchant()
        arrayList = ArrayList()
        profilee = findViewById<View>(R.id.profileImageViewRequestPin1) as CircleImageView
        profilee2 = findViewById<View>(R.id.profileImageViewRequestPin2) as CircleImageView

        profileImageViewRequestPin1.setOnClickListener {
            if (btnsubmit.visibility == View.INVISIBLE) {
                Toast.makeText(this, "Hitung Total Harga Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                chooseFile()
            }
        }
        profileImageViewRequestPin2.setOnClickListener {
            if (!isimage) {
                Toast.makeText(this, "Hitung Total Harga Terlebih Dahulu", Toast.LENGTH_SHORT).show()
            } else {
                chooseFile1()
            }
        }
        switchh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                profilee2.visibility = View.VISIBLE
                val kodetext = kode.text.toString()
                val total: Int
                val totall: String
                val value = titJumlah.text.toString()
                val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                when {
                    finalValue <= 5 -> {
                        total = finalValue * 3500
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 6..10 -> {
                        total = finalValue * 3000
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 11..20 -> {
                        total = finalValue * 2500
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 21..50 -> {
                        total = finalValue * 2600
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 51..100 -> {
                        total = finalValue * 2400
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 101..200 -> {
                        total = finalValue * 2300
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue in 201..500 -> {
                        total = finalValue * 2200
                        hide()
                        doit(kodetext,total)
                    }
                    finalValue > 500 ->{
                        total = finalValue * 1900
                        hide()
                        doit(kodetext,total)
                    }
                }
            }
            else {
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
                profilee2.visibility = View.GONE
            }
        }
        btncheck.setOnClickListener {

            val kodetext = kode.text.toString()

            if (spinner.selectedItem.toString() == "Ukuran") {
                Toast.makeText(this, "Silahkan Isi Ukuran", Toast.LENGTH_SHORT).show()
            }
            else if (spinner.selectedItem.toString() == "44") {
                if (spinner2.selectedItem.toString() == "Jenis Laminasi") {
                    Toast.makeText(this, "Silahkan Isi Laminasi", Toast.LENGTH_SHORT).show()
                }
                else if (spinner2.selectedItem.toString() == "Glossy") {
                    if (spinner4.selectedItem.toString() == "Jenis Ganci") {
                        Toast.makeText(this, "Silahkan Isi Jenis Ganci", Toast.LENGTH_SHORT).show()
                    }
                    else if (spinner4.selectedItem.toString() == "Biasa") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 1800
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 1700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 1600
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1500
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Botol") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 2100
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 2000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1800
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Boneka") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 7500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 7000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 6500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 4500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 4200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 2800
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                } else {
                    if (spinner4.selectedItem.toString() == "Jenis Ganci") {
                        Toast.makeText(this, "Silahkan Isi Jenis Ganci", Toast.LENGTH_SHORT).show()
                    } else if (spinner4.selectedItem.toString() == "Biasa") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 3200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 1900
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 1800
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1700
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Botol") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2600
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 2300
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 2200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 2000
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Boneka") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 7700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 7200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 6700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 4700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 4400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 3200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                }
            } else if (spinner.selectedItem.toString() == "58") {
                if (spinner2.selectedItem.toString() == "Jenis Laminasi") {
                    Toast.makeText(this, "Silahkan Isi Laminasi", Toast.LENGTH_SHORT).show()
                } else if (spinner2.selectedItem.toString() == "Glossy") {
                    if (spinner4.selectedItem.toString() == "Jenis Ganci") {
                        Toast.makeText(this, "Silahkan Isi Jenis Ganci", Toast.LENGTH_SHORT).show()
                    }
                    else if (spinner4.selectedItem.toString() == "Biasa") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 1900
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 1750
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1650
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Botol") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2600
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 2300
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 2200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1900
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    } else if (spinner4.selectedItem.toString() == "Boneka") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 7500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 7000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 6500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 5000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 4800
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 4200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 3500
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                } else {
                    if (spinner4.selectedItem.toString() == "Jenis Ganci") {
                        Toast.makeText(this, "Silahkan Isi Jenis Ganci", Toast.LENGTH_SHORT).show()
                    }
                    else if (spinner4.selectedItem.toString() == "Biasa") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..201 -> {
                                    total = finalValue * 2100
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 1950
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 1850
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                    else if (spinner4.selectedItem.toString() == "Botol") {
                        if (this.xx) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 3200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 2700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 2800
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 2600
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 2500
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 2400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 2100
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                    else if (spinner4.selectedItem.toString() == "Boneka") {
                        if (xx == true) {
                            xx = false
                            Toast.makeText(this, "Anda Yakin mengirim ke " + spinner3.selectedItem.toString() + " ?", Toast.LENGTH_SHORT).show()
                        } else {
                            val total: Int
                            val value = titJumlah.text.toString()
                            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                            when {
                                finalValue <= 5 -> {
                                    total = finalValue * 7700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 6..10 -> {
                                    total = finalValue * 7200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 11..20 -> {
                                    total = finalValue * 6700
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 21..50 -> {
                                    total = finalValue * 5200
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 51..100 -> {
                                    total = finalValue * 5000
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 101..200 -> {
                                    total = finalValue * 4400
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue in 201..500 -> {
                                    total = finalValue * 3900
                                    hide()
                                    doit(kodetext,total)
                                }
                                finalValue > 500 -> {
                                    total = finalValue * 3700
                                    hide()
                                    doit(kodetext,total)
                                }
                            }
                        }
                    }
                }
            }
        }

        spinner4?.onItemSelectedListener = this
        val array_adapter4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsCategory)
        array_adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner4?.adapter = array_adapter4

        spinner2!!.onItemSelectedListener = this
        val array_adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsType)
        array_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2!!.adapter = array_adapter2

        spinner!!.onItemSelectedListener = this
        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfItemsSize)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = array_adapter

        btnsubmit.setOnClickListener {
            if (spinner2.selectedItem.toString() == "Jenis Laminasi") {
                Toast.makeText(this, "Silahkan isi Laminasi", Toast.LENGTH_SHORT).show()
            } else {
                if (switchh.isChecked) {
                    if (!isimage) {
                        Toast.makeText(this, "Silahkan Upload Desain Kamu", Toast.LENGTH_SHORT).show()
                    } else {
                        if (!isimage2) {
                            Toast.makeText(this, "Silahkan Upload Desain Kamu", Toast.LENGTH_SHORT).show()
                        } else {
                            if (textView11.text == "0") {
                                Toast.makeText(this, "Minimal Pemesanan adalah 1 buah", Toast.LENGTH_SHORT).show()
                            } else {
                                val sessionManager = SessionManager(this)
                                val user: HashMap<String, String> = sessionManager.userDetail
                                val emaill = user["EMAIL"]
                                val harga = textView11.text.toString()
                                val ukuran = "45"
                                val laminasi = spinner2.selectedItem.toString()
                                val jumlah = titJumlah.text.toString()
                                val merchant = spinner3.selectedItem.toString()
                                val kodes = kode.text.toString()
                                val jenis = "Dua Sisi"
                                checkpin1(emaill!!, bitmap3?.let { getStringImage(it) }!!, bitmap2?.let { getStringImage(it) }!!, harga, ukuran, laminasi, jenis, jumlah, merchant, kodes)
                            }
                        }
                    }
                } else {
                    if (!isimage) {
                        Toast.makeText(this, "Silahkan Upload Desain Kamu", Toast.LENGTH_SHORT).show()
                    } else {
                        if (textView11.text == "0") {
                            Toast.makeText(this, "Minimal Pemesanan adalah 1 buah", Toast.LENGTH_SHORT).show()
                        } else {
                            val sessionManager = SessionManager(this)
                            val user: HashMap<String, String> = sessionManager.userDetail
                            val emaill = user["EMAIL"]
                            val harga = textView11.text.toString()
                            val ukuran = spinner.selectedItem.toString()
                            val laminasi = spinner2.selectedItem.toString()
                            val jumlah = titJumlah.text.toString()
                            val merchant = spinner3.selectedItem.toString()
                            val kodes = kode.text.toString()
                            val jenis = spinner4.selectedItem.toString()
                            val photo = "Ada Kok"
                            checkpin2(emaill!!, bitmap3?.let { getStringImage(it) }!!, harga, ukuran, laminasi, jenis, jumlah, merchant, kodes)
                        }
                    }
                }
            }
        }

        btnedit.setOnClickListener {
            spinner.isEnabled = true
            spinner2.isEnabled = true
            kode.isEnabled = true
            titJumlah.isEnabled = true
            spinner3.isEnabled = true
            spinner4.isEnabled = true
            switchh.isEnabled = true
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
                var sessionManager = SessionManager(this@Ganci)
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
        val ttlbyr = totall - kode
        if (ttlbyr <= 0) {
            textView11.text = "0"
        } else {
            textView11.text = ttlbyr.toString()
        }

        switchh.visibility = View.VISIBLE
        textView7.visibility = View.VISIBLE
        linear1.visibility = View.VISIBLE
        Handler().postDelayed({
            val main = findViewById<ScrollView>(R.id.main)
            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.VISIBLE
            spin_kit.visibility = View.INVISIBLE
        }, 1000)
    }

    private fun chooseFile1() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 2)
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filepath = data.data
            try {
                bitmap3 = MediaStore.Images.Media.getBitmap(baseContext.contentResolver, filepath)
                profilee.setImageBitmap(bitmap3)
                isimage = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filepath2 = data.data
            try {
                bitmap2 = MediaStore.Images.Media.getBitmap(baseContext.contentResolver, filepath2)
                profilee2.setImageBitmap(bitmap2)
                isimage2 = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadPicture(email: String, photo: String, photo2: String, harga: String, ukuran: String, laminasi: String, jenis: String, jumlah: String, merchant: String, kode: String) {


        val stringRequest = object : StringRequest(Method.POST, URL_GANCI,
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
                params["email"] = email
                params["photo"] = photo
                params["photo2"] = photo2
                params["harga"] = harga
                params["ukuran"] = ukuran
                params["laminasi"] = laminasi
                params["jenis"] = jenis
                params["jumlah"] = jumlah
                params["merchant"] = merchant
                params["kode"] = kode
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun UploadPicture1(email: String, photo: String, harga: String, ukuran: String, laminasi: String, jenis: String, jumlah: String, merchant: String, kode: String) {
        val stringRequest = object : StringRequest(Method.POST, URL_GANCI2,
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
                params["email"] = email
                params["photo"] = photo
                params["harga"] = harga
                params["ukuran"] = ukuran
                params["laminasi"] = laminasi
                params["jenis"] = jenis
                params["jumlah"] = jumlah
                params["merchant"] = merchant
                params["kode"] = kode
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    fun getStringImage(bitmap: Bitmap): String? {

        val bytearrayoutputstream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream)
        val imageByteArray = bytearrayoutputstream.toByteArray()


        return Base64.encodeToString(imageByteArray, Base64.DEFAULT)
    }

    private fun loadMerchant() {
        val stringRequest = object : StringRequest(Method.GET, URL_MERCHANT1,
                Response.Listener { response ->
                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                                main.visibility = View.VISIBLE
                                spin_kit.visibility = View.INVISIBLE
                                val merchant = array.getJSONObject(i)
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
                return java.util.HashMap()
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    private fun doit(kodetext:String, total:Int){
        val totall: String
        totall = total.toString()
        asiap.setText(totall)
        spinner.isEnabled = false
        spinner2.isEnabled = false
        spinner3.isEnabled = false
        spinner4.isEnabled = false
        kode.isEnabled = false
        titJumlah.isEnabled = false
        btncheck.visibility = View.GONE
        linear2.visibility = View.VISIBLE
        linear3.visibility = View.VISIBLE
        linear4.visibility = View.VISIBLE
        btnsubmit.visibility = View.VISIBLE
        btnedit.visibility = View.VISIBLE
        checkkode(kodetext, total)
    }

    private fun hide(){
        val main = findViewById<ScrollView>(R.id.main)
        val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
        main.visibility = View.INVISIBLE
        spin_kit.visibility = View.VISIBLE
    }

    private fun checkpin1(email: String, photo: String, photo2: String, harga: String, ukuran: String, laminasi: String, jenis: String, jumlah: String, merchant: String, kode: String) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { pinview, fromUser -> //Make api calls here or what not
            val intPIN = pinView.value
            val sessionManager = SessionManager(this)
            val user = sessionManager.userDetail
            val pin = user["PIN"]
            if (pin == "123456") {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Masih Default!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN anda masih dalam keadaan default dari sistem, silahkan ganti PIN anda pada menu AKUN.")
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
            } else if (pin == intPIN) {
                val main = findViewById<ScrollView>(R.id.main)
                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                dialog.cancel()
                main.visibility = View.INVISIBLE
                spin_kit.visibility = View.VISIBLE
                uploadPicture(email, photo, photo2, harga, ukuran, laminasi, jenis, jumlah, merchant, kode)
            } else if (pin != intPIN) {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Salah!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN yang anda masukkan salah. Silahkan coba lagi.")
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
        })
        dialog.show()
    }

    private fun checkpin2(email: String, photo: String, harga: String, ukuran: String, laminasi: String, jenis: String, jumlah: String, merchant: String, kode: String) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { pinview, fromUser -> //Make api calls here or what not
            val intPIN = pinView.value
            val sessionManager = SessionManager(this)
            val user = sessionManager.userDetail
            val pin = user["PIN"]
            if (pin == "123456") {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Masih Default!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN anda masih dalam keadaan default dari sistem, silahkan ganti PIN anda pada menu AKUN.")
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
            } else if (pin == intPIN) {
                val main = findViewById<ScrollView>(R.id.main)
                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                dialog.cancel()
                main.visibility = View.INVISIBLE
                spin_kit.visibility = View.VISIBLE
                UploadPicture1(email, photo, harga, ukuran, laminasi, jenis, jumlah, merchant, kode)
            } else if (pin != intPIN) {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Salah!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN yang anda masukkan salah. Silahkan coba lagi.")
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
        })
        dialog.show()
    }
}
