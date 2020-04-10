package app.inisiator.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Scanner2 : AppCompatActivity() {
    var codeScanner: CodeScanner? = null
    var codeScannerView: CodeScannerView? = null
    var phone: ImageView? = null
    var isOpen = false
    val URL_TOPUP = "https://awalspace.com/app/imbalopunyajangandiganggu/topup.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner2)

        codeScannerView = findViewById(R.id.scannerView)
        codeScanner = CodeScanner(this, codeScannerView!!)
        codeScanner!!.decodeCallback = DecodeCallback { result -> runOnUiThread { login(result.text) } }
        codeScannerView!!.setOnClickListener(View.OnClickListener { codeScanner!!.startPreview() })
        phone = findViewById(R.id.phone)
    }

    override fun onResume() {
        super.onResume()
        requestforcamera()
    }

    private fun requestforcamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                codeScanner!!.startPreview()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                val bottomSheetDialog = BottomSheetDialog(
                        this@Scanner2, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this@Scanner2)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("Izin Diperlukan!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Untuk menggunakan fitur ini kamu harus mengizinkan InisiatorX mengakses kamera kamu.")
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

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                token.cancelPermissionRequest()
            }
        }).check()
    }

    private fun login(result: String) {
        val stringRequest: StringRequest = @SuppressLint("ResourceType")
        object : StringRequest(Method.POST, Scanner2.URL_FIND_MERCHANT,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("findmerchant")
                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                val `object` = jsonArray.getJSONObject(i)
                                val nama_merchant = `object`.getString("nama_merchant").trim { it <= ' ' }
                                val id_merchant = `object`.getString("merchant_id").trim()

                                val view = layoutInflater.inflate(R.layout.topup, null);
                                val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                dialog.setContentView(view)
                                val txtnama = dialog.findViewById<TextView>(R.id.nama_merchant)
                                txtnama.setText(nama_merchant)
                                val nominal = dialog.findViewById<EditText>(R.id.editText2)
                                val close = dialog.findViewById<ImageView>(R.id.close)
                                val btnsubmit = dialog.findViewById<Button>(R.id.btnsubmit)
                                val sessionManager = SessionManager(this)
                                val user: HashMap<String, String> = sessionManager.userDetail
                                val no_ = user.get("NO")
                                val btn5rebu = dialog.findViewById<LinearLayout>(R.id.btn5rebu)
                                val btn10rebu = dialog.findViewById<LinearLayout>(R.id.btn10rebu)
                                val btn15rebu = dialog.findViewById<LinearLayout>(R.id.btn15rebu)
                                val btn20rebu = dialog.findViewById<LinearLayout>(R.id.btn20rebu)
                                btn5rebu.setOnClickListener {
                                    nominal.setText("5000")
                                }
                                btn10rebu.setOnClickListener {
                                    nominal.setText("10000")
                                }
                                btn15rebu.setOnClickListener {
                                    nominal.setText("15000")
                                }
                                btn20rebu.setOnClickListener {
                                    nominal.setText("20000")
                                }
                                close.setOnClickListener {
                                    dialog.dismiss()
                                    onResume()
                                }
                                btnsubmit.setOnClickListener {
                                    if (nominal.text.isEmpty()){
                                        val bottomSheetDialog = BottomSheetDialog(
                                                this, R.style.BottomSheetDialogTheme
                                        )
                                        val bottomSheetView = LayoutInflater.from(this)
                                                .inflate(
                                                        R.layout.layout_bottom_notif,
                                                        null
                                                )
                                        bottomSheetView.findViewById<TextView>(R.id.title).setText("Nominal Masih Kosong!")
                                        bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Silahkan isi nominal terlebih dahulu sebelum anda melanjutkan transaksi anda")
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
                                    else{
                                        checkpin(no_!!, id_merchant, nominal.text.toString())
//                                        topup(no_!!, id_merchant, nominal.text.toString())
                                    }
                                }
                                dialog.show()


                            }
                        } else if (success == "0") {
                            val bottomSheetDialog = BottomSheetDialog(
                                    this, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(this)
                                    .inflate(
                                            R.layout.layout_bottom_notif,
                                            null
                                    )
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
                },
                Response.ErrorListener {
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
                val params: MutableMap<String, String> = HashMap()
                params["result"] = result
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun checkpin(no: String, merchant_id: String, nominal: String) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { _, _ -> //Make api calls here or what not
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
                topup(no, merchant_id, nominal)
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

    private fun topup(no: String, merchant_id: String, nominal: String) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_TOPUP,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success == "1") {
                            startActivity(Intent(this, Success::class.java))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        startActivity(Intent(this, Failed::class.java))
                    }
                }, Response.ErrorListener { _ -> startActivity(Intent(this, Failed::class.java)) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["no"] = no
                params["merchant_id"] = merchant_id
                params["nominal"] = nominal
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    companion object {
        private const val URL_FIND_MERCHANT = "https://awalspace.com/app/imbalopunyajangandiganggu/findmerchant.php"
    }
}
