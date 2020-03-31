package app.inisiator.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import app.inisiator.myapplication.R
import app.inisiator.myapplication.SessionManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_pin.*
import kotlinx.android.synthetic.main.activity_ticket_payment.*
import kotlinx.android.synthetic.main.activity_ticket_payment.kode
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.HashMap

class TicketPaymentActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null
    var totalbayar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_payment)

        supportActionBar?.hide()

        val eventTitle = intent.getStringExtra("EVENT_TITLE")
        val eventLokasi = intent.getStringExtra("EVENT_LOKASI")
        val eventTanggal = intent.getStringExtra("EVENT_TANGGAL")
        val eventWaktu = intent.getStringExtra("EVENT_WAKTU")
        val eventHarga = intent.getIntExtra("EVENT_HARGA", 0)
        val myBalance = intent.getIntExtra("MY_BALANCE", 0)

        val myBalanceAfterTransaction = myBalance - eventHarga
        totalbayar = myBalanceAfterTransaction
        val sessionManager =  SessionManager(this)
        val user: HashMap<String, String> = sessionManager.userDetail
        val email = user["EMAIL"]

        var buttonBayar = true
        if (myBalanceAfterTransaction < 0) {
            btnBayar_tiket.setBackgroundColor(Color.GRAY)
            buttonBayar = false
        }

        init(
            eventTitle,
            eventLokasi,
            eventTanggal,
            eventWaktu,
            eventHarga,
            myBalance,
            myBalanceAfterTransaction
        )

        check.setOnClickListener{
            checkkode(kode.text.toString(), eventHarga)
            val main = findViewById<ScrollView>(R.id.main)
            val spinkit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.INVISIBLE
            spinkit.visibility = View.VISIBLE
        }

        btnBayar_tiket.setOnClickListener {
            if (!buttonBayar) {
                Toast.makeText(this, "Saldo anda tidak cukup", Toast.LENGTH_SHORT).show()
            } else {
                val charPool : List<Char> = ('A'..'Z') + ('0'..'9')

                val filenameQr = (1..8)
                    .map { kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")

                try {
                    checkpin(filenameQr, eventTanggal, eventTitle, eventLokasi, eventWaktu, email, Integer.parseInt(my_balance_aftertransaction_tiket.text.toString()))
//                    uploadValidTicketToDatabase(filenameQr, eventTanggal, eventTitle, eventLokasi, eventWaktu, email, Integer.parseInt(my_balance_aftertransaction_tiket.text.toString()))

//                    showToast("QRCode saved to -> $path")
//                    Log.d("TicketPayment Dir", path)
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun uploadValidTicketToDatabase(filenameQr: String, eventTanggal: String?, eventTitle: String?, eventLokasi: String?, eventWaktu: String?, email: String?, myBalanceAfterTransaction: Int) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/insertValidQr.php"
        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener {
                try {
                    val jsonObject = JSONObject(it)
                    val success = jsonObject.getString("success")
                    if (success == "1") {
                        val main = findViewById<ScrollView>(R.id.main)
                        val spinkit = findViewById<SpinKitView>(R.id.spin_kit)
                        main.visibility = View.INVISIBLE
                        spinkit.visibility = View.VISIBLE
                        bitmap = textToImageEncode(filenameQr)
                        val path = saveImage(bitmap, filenameQr)
                        val intent = Intent(this, QrTicketPreviewActivity::class.java)
                        intent.putExtra("QR_URI", path)
                        intent.putExtra("EVENT_TITLE", eventTitle)
                        intent.putExtra("EVENT_LOKASI", eventLokasi)
                        intent.putExtra("EVENT_TANGGAL", eventTanggal)
                        intent.putExtra("EVENT_WAKTU", eventWaktu)
                        startActivity(intent)
                        finish()
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
                params["qrcode"] = filenameQr
                params["exp_date"] = eventTanggal!!
                params["event_title"] = eventTitle!!
                params["lokasi"] = eventLokasi!!
                params["waktu"] = eventWaktu!!
                params["email"] = email!!
                params["balance"] = myBalanceAfterTransaction.toString()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun saveImage(myBitmap: Bitmap?, randomString: String): String {
        val bytes = ByteArrayOutputStream()
        myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            applicationContext.getExternalFilesDir(null)?.absolutePath + IMAGE_DIRECTORY)

        if (!wallpaperDirectory.exists()) {
//            Log.d("TicketPaymentActivity", "" + wallpaperDirectory.mkdirs())
            wallpaperDirectory.mkdirs()
        }

        try {
            val file = File(wallpaperDirectory, "$randomString.png")
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(file.path),
                arrayOf("image/jpeg"), null)
            fileOutputStream.close()
            Log.d("TicketPaymentActivity", "File Saved::--->" + file.absolutePath)

            return file.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    @Throws(WriterException::class)
    private fun textToImageEncode(Value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                QRcodeWidth, QRcodeWidth, null
            )

        } catch (illegalArgumentException: IllegalArgumentException) {

            return null
        }

        val bitMatrixWidth = bitMatrix.width

        val bitMatrixHeight = bitMatrix.height

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    ContextCompat.getColor(this, R.color.black)
                else
                    ContextCompat.getColor(this, R.color.colorWhite)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.RGB_565)

        bitmap.setPixels(pixels, 0, 300, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun init(eventTitle: String?, eventLokasi: String?, eventTanggal: String?, eventWaktu: String?, eventHarga: Int, myBalance: Int, myBalanceAfterTransaction: Int) {
        ticketpayment_spin_kit.visibility = View.GONE
        ticketPaymentLayout.visibility = View.VISIBLE

        event_title_pay.text = eventTitle
        event_loc_pay.text = eventLokasi
        event_tanggal_pay.text = eventTanggal
        event_time_pay.text = eventWaktu
        event_price_pay.text = eventHarga.toString()
        event_price_.text = "Harga -"+eventHarga.toString()
        my_balance_pay_tiket.text = myBalance.toString()

        my_balance_aftertransaction_tiket.text = myBalanceAfterTransaction.toString()
    }

    companion object {
        const val QRcodeWidth = 300
        private const val IMAGE_DIRECTORY = "/inisiatorX/qrTicketEvent"
    }

    private fun checkkode(kode: String, totall: Int) {
        val URL_KODE = "https://awalspace.com/app/imbalopunyajangandiganggu/kode.php"
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
                                event_diskon.text = "Diskon -"+potongan.toString()
                                val mmnt = Integer.parseInt(potongan.toString())
                                hitung(totall, mmnt)
                            }
                        }
                        else if(success.equals("2"))
                        {
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Promo Sudah Tidak Bisa Dipakai", Toast.LENGTH_SHORT).show()
                            event_diskon.text = "Diskon -0"
                            hitung(totall, 0)
                        }
                        else if (success.equals("0")) {
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                            event_diskon.text = "Diskon -0"
                            hitung(totall, 0)
                        }
                        else if(success.equals("3")){
                            val txtkode = findViewById<EditText>(R.id.kode)
                            txtkode.setText("")
                            Toast.makeText(this, "Kode Promo Tidak Bisa Dipakai Karena Tidak Mencukupi Batas Minimal Harga", Toast.LENGTH_SHORT).show()
                            event_diskon.text = "Diskon -0"
                            hitung(totall, 0)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val main = findViewById<ScrollView>(R.id.main)
                        val spinkit = findViewById<SpinKitView>(R.id.spin_kit)
                        main.visibility = View.VISIBLE
                        spinkit.visibility = View.INVISIBLE
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                var sessionManager = SessionManager(this@TicketPaymentActivity)
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
        val awal = Integer.parseInt(my_balance_pay_tiket.text.toString())
        val akhir = kode
        val ttlbyr = awal - (totall - akhir)
        totalbayar = ttlbyr
        if (ttlbyr <= 0) {
            event_price_.text = my_balance_pay_tiket.toString()
        } else {
            my_balance_aftertransaction_tiket.text = ttlbyr.toString()
        }
        Handler().postDelayed({
            val main = findViewById<ScrollView>(R.id.main)
            val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
            main.visibility = View.VISIBLE
            spin_kit.visibility = View.INVISIBLE
        }, 1000)
    }

    private fun checkpin(filenameQr: String, eventTanggal: String?, eventTitle: String?, eventLokasi: String?, eventWaktu: String?, email: String?, myBalanceAfterTransaction: Int) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { pinview, fromUser -> //Make api calls here or what not
            val intPIN = pinView.value
            val sessionManager = SessionManager(applicationContext)
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
                val spinkit = findViewById<SpinKitView>(R.id.spin_kit)
                main.visibility = View.INVISIBLE
                spinkit.visibility = View.VISIBLE
                uploadValidTicketToDatabase(filenameQr, eventTanggal, eventTitle, eventLokasi, eventWaktu, email, myBalanceAfterTransaction)
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
