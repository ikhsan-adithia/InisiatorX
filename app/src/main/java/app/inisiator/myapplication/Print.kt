package app.inisiator.myapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.ybq.android.spinkit.SpinKitView
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_print.*
import net.gotev.uploadservice.MultipartUploadRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

val URL_KODE2 = "https://awalspace.com/app/imbalopunyajangandiganggu/kode.php"
val URL_EPRINT = "https://awalspace.com/app/imbalopunyajangandiganggu/eprint.php"
val URL_MERCHANT2 = "https://awalspace.com/app/imbalopunyajangandiganggu/get_merchant.php"
val URL_CHECK = "https://awalspace.com/app/imbalopunyajangandiganggu/check.php"
var totalpages = 0
var total = 0
var total2 = 0

class Print : AppCompatActivity(),  SingleUploadBroadcastReceiver.Delegate, AdapterView.OnItemSelectedListener, OnLoadCompleteListener {
    override fun onCancelled() {
        Toast.makeText(this, "Try Again Later!", Toast.LENGTH_LONG).show()
    }

    override fun onProgress(progress: Int) {
    }

    override fun onProgress(uploadedBytes: Long, totalBytes: Long) {
    }

    override fun onError(exception: java.lang.Exception?) {
    }

    override fun onCompleted(serverResponseCode: Int, serverResponseBody: ByteArray?) {
        var sessionManager = SessionManager(this)
        var user: HashMap<String, String> = sessionManager.userDetail
        var emaill = user.get("EMAIL")
        checksuccess2(emaill!!)
    }

    var isifile = false
    var filePath: Uri? = null
    var arrayList: ArrayList<String>? = null
    private lateinit var file: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)

        AllowRunTimePermission()
        val sessionManager = SessionManager(this)
        val user: HashMap<String, String> = sessionManager.userDetail
        val emaill = user["EMAIL"]
        checksuccess(emaill!!)
        loadmerchant()
        arrayList = ArrayList()
        file = findViewById<View>(R.id.btnfile) as Button

        file.setOnClickListener {
            chooseFile()
        }

        close.setOnClickListener {
            finish()
        }

        btncheck.setOnClickListener {

            val kodetext = titPromo.text.toString()
            if (isifile == false) {
                Toast.makeText(this, "Silahkan Upload File Kamu", Toast.LENGTH_SHORT).show()
            } else {

                    val total: Int
                    val totall: String
                    val value = titJumlah.text.toString()
                    val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
                    var pdfView = findViewById<PDFView>(R.id.pdfView)
                    pdfView.fromUri(filePath).defaultPage(0).enableAnnotationRendering(true).onLoad(this).load()
                    total = 200 * totalpages * finalValue
                    totall = total.toString()
                    textView9.text = totall
                    val main = findViewById<ScrollView>(R.id.main)
                    val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                    main.visibility = View.INVISIBLE
                    spin_kit.visibility = View.VISIBLE
                    checkkode(kodetext, total)
            }
        }

        btnsubmit.setOnClickListener {
            btnsubmit.isEnabled == false
            if (isifile == false) {
                Toast.makeText(this, "Silahkan Upload File Kamu", Toast.LENGTH_SHORT).show()
            } else {
                if (textView11.text.equals("0")) {
                    Toast.makeText(this, "Minimal Pemesanan adalah 1 buah", Toast.LENGTH_SHORT).show()
                } else {
                    checkpin()
                }
            }
        }

        btnedit.setOnClickListener {
            titPromo.isEnabled = true
            merchant.isEnabled = true
            file.isEnabled = true
            btncheck.visibility = View.VISIBLE
            linear2.visibility = View.INVISIBLE
            linear3.visibility = View.INVISIBLE
            linear4.visibility = View.INVISIBLE
            btnsubmit.visibility = View.INVISIBLE
            btnedit.visibility = View.INVISIBLE
            btnsubmit.isEnabled = true
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun loadComplete(nbPages: Int) {
        totalpages = nbPages
    }

    private val uploadReceiver = SingleUploadBroadcastReceiver()

    override fun onResume() {
        super.onResume()
        uploadReceiver.register(this)
    }

    override fun onPause() {
        super.onPause()
        uploadReceiver.unregister(this)
    }

    private fun checkkode(kode: String, totall: Int) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_KODE2,
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
                        } else if(success.equals("2"))
                        {
                            val txtkode = findViewById<EditText>(R.id.titPromo)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Promo Sudah Tidak Bisa Dipakai", Toast.LENGTH_SHORT).show()
                            textView10.text = "0"
                            hitung(totall, 0)
                        }
                        else if (success.equals("0")) {
                            val txtkode = findViewById<EditText>(R.id.titPromo)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                            textView10.text = "0"
                            hitung(totall, 0)
                        }
                        else if(success.equals("3")){
                            val txtkode = findViewById<EditText>(R.id.titPromo)
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
                }, Response.ErrorListener { _ -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var sessionManager = SessionManager(this@Print)
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
        merchant.isEnabled = false
        titPromo.isEnabled = false
        file.isEnabled = false
        btncheck.visibility = View.GONE
        linear2.visibility = View.VISIBLE
        linear3.visibility = View.VISIBLE
        linear4.visibility = View.VISIBLE
        btnsubmit.visibility = View.VISIBLE
        btnedit.visibility = View.VISIBLE
        Handler().postDelayed({
            val main = findViewById<ScrollView>(R.id.main)
            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.VISIBLE
            spin_kit.visibility = View.INVISIBLE
        }, 1000)
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih PDF Anda"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            file.text = "PDF is Selected"
            isifile = true
            val total: Int
            val totall: String
            val value = titJumlah.text.toString()
            val finalValue = if (value == "") 0 else Integer.parseInt(value, 10)
            var pdfView = findViewById<PDFView>(R.id.pdfView)
            pdfView.fromUri(filePath).defaultPage(0).enableAnnotationRendering(true).onLoad(this).load()
            total = 200 * totalpages * finalValue
            totall = total.toString()
            textView9.text = totall
        }
    }

    private fun uploadMultipart() {

        val PdfPathHolder = FilePath.getPath(this, filePath)
        if (PdfPathHolder == null) {
            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show()
        } else {
            try {
                val PdfNameHolder = "Eprint"
                var sessionManager = SessionManager(this)
                var user: HashMap<String, String> = sessionManager.userDetail
                var emaill = user.get("EMAIL")
                var harga = textView11.text.toString()
                var jumlah = titJumlah.text.toString()
                var merchant = merchant.selectedItem.toString()
                var kodes = titPromo.text.toString()
                val PdfID = UUID.randomUUID().toString()
                uploadReceiver.setDelegate(this)
                uploadReceiver.setUploadID(PdfID)
                MultipartUploadRequest(this, PdfID, URL_EPRINT)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", PdfNameHolder)
                        .addParameter("email", emaill)
                        .addParameter("jumlah", jumlah)
                        .addParameter("merchant", merchant)
                        .addParameter("harga", harga)
                        .addParameter("kode", kodes)
                        .setMaxRetries(2)
                        .startUpload()
            } catch (exception: Exception) {
                Handler().postDelayed({
                    val main = findViewById<ScrollView>(R.id.main)
                    val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                    main.visibility = View.VISIBLE
                    spin_kit.visibility = View.INVISIBLE
                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                }, 3000)
            }
        }
    }

    private fun loadmerchant() {
        val stringRequest = object : StringRequest(Request.Method.GET, URL_MERCHANT2,
                Response.Listener { response ->
                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                                main.visibility = View.VISIBLE
                                spin_kit.visibility = View.INVISIBLE
                                val merchantt = array.getJSONObject(i)
                                arrayList!!.add(merchantt.getString("nama_merchant"))
                                merchant!!.onItemSelectedListener = this
                                val array_adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList!!)
                                array_adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                merchant!!.adapter = array_adapter3
                            }, 3000)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val main = findViewById<ScrollView>(R.id.main)
                        val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                        main.visibility = View.VISIBLE
                        spin_kit.visibility = View.INVISIBLE
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
            val main = findViewById<ScrollView>(R.id.main)
            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.VISIBLE
            spin_kit.visibility = View.INVISIBLE
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
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    fun AllowRunTimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onRequestPermissionsResult(RC: Int, per: Array<String>, Result: IntArray) {
        when (RC) {
            1 ->
                if (Result.size > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission Canceled", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun checksuccess(email: String) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_CHECK,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success.equals("1")) {
                            val jumlah = jsonObject.getInt("jumlah")
                            total = jumlah
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { _ -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params.put("email", email)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun checksuccess2(email: String) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_CHECK,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success.equals("1")) {
                            val jumlah = jsonObject.getInt("jumlah")
                            total2 = jumlah
                            last()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { _ -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params.put("email", email)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun last() {
        if (total == total2) {
            Handler().postDelayed({
                val main = findViewById<ScrollView>(R.id.main)
                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                main.visibility = View.VISIBLE
                spin_kit.visibility = View.INVISIBLE
                Toast.makeText(this, "Maaf Gagal Request Print, SIlahkan Coba Lagi", Toast.LENGTH_SHORT).show()
            }, 3000)

        } else {
            Handler().postDelayed({
                val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
                spin_kit.visibility = View.GONE
                startActivity(Intent(this, Success::class.java))
            }, 3000)

        }
    }

    private fun checkpin() {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { _, _ -> //Make api calls here or what not
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
                uploadMultipart()
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
