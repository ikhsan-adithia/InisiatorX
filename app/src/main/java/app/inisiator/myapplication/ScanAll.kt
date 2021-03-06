package app.inisiator.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.bumptech.glide.Glide
import com.goodiebag.pinview.Pinview
import com.goodiebag.pinview.Pinview.PinViewEventListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ScanAll : AppCompatActivity() {
    var codeScanner: CodeScanner? = null
    var codeScannerView: CodeScannerView? = null
    var phone: ImageView? = null
    val URL_PAY = "https://awalspace.com/app/imbalopunyajangandiganggu/pay.php"
    private val URL_FIND = "https://awalspace.com/app/imbalopunyajangandiganggu/findsend.php"
    private val URL_SEND = "https://awalspace.com/app/imbalopunyajangandiganggu/send.php"
    var isOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scanner)
        codeScannerView = findViewById(R.id.scannerView)
        codeScanner = CodeScanner(this, codeScannerView!!)
        codeScanner!!.decodeCallback = DecodeCallback { result -> runOnUiThread { login(result.text) } }
        codeScannerView!!.setOnClickListener(View.OnClickListener { codeScanner!!.startPreview() })
        phone = findViewById(R.id.phone)
        phone!!.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.findphone, null);
            val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            dialog.setContentView(view)
            val close = dialog.findViewById<ImageView>(R.id.close)
            val editText = dialog.findViewById<EditText>(R.id.editText)
            editText.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                override fun onFocusChange(view: View, b: Boolean) {
                    val subtitle = dialog.findViewById<LinearLayout>(R.id.subtitle)
                    val hasil = dialog.findViewById<LinearLayout>(R.id.hasil)
                    subtitle.visibility = View.INVISIBLE
                    hasil.visibility = View.VISIBLE
                }
            })
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val nomor = dialog.findViewById<TextView>(R.id.nomor)
                    val subtitle = dialog.findViewById<LinearLayout>(R.id.subtitle)
                    val hasil = dialog.findViewById<LinearLayout>(R.id.hasil)
                    subtitle.visibility = View.INVISIBLE
                    hasil.visibility = View.VISIBLE
                    nomor.setText(p0)
                }

            })
            val hasil = dialog.findViewById<LinearLayout>(R.id.hasil)
            hasil.setOnClickListener {
                val nomor = dialog.findViewById<TextView>(R.id.nomor)
                find(nomor.text.toString())
            }

            close.setOnClickListener {
                dialog.dismiss()
                onResume()
            }
            dialog.show()
        }
    }

    companion object {
        private const val URL_FIND_MERCHANT = "https://awalspace.com/app/imbalopunyajangandiganggu/findmerchant.php"
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
                        this@ScanAll, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this@ScanAll)
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

    private fun find(no_hp: String) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, this.URL_FIND,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("find")
                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                val `object` = jsonArray.getJSONObject(i)
                                val noHp = `object`.getString("no_hp").trim { it <= ' ' }
                                val name = `object`.getString("name").trim { it <= ' ' }
                                val photo = `object`.getString("photo").trim { it <= ' ' }
                                val view = layoutInflater.inflate(R.layout.merchant, null);
                                val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                dialog.setContentView(view)
                                val txtnama = dialog.findViewById<TextView>(R.id.nama_merchant)
                                if (photo.equals("")) {
                                } else {
                                    val profilee = dialog.findViewById<CircleImageView>(R.id.profilePicture_Home)
                                    Glide.with(this).load(photo).into(profilee)
                                }
                                txtnama.setText(name)
                                val nominal = dialog.findViewById<TextView>(R.id.nominal)
                                val close = dialog.findViewById<ImageView>(R.id.close)
                                val btn1 = dialog.findViewById<Button>(R.id.btn1)
                                val btn2 = dialog.findViewById<Button>(R.id.btn2)
                                val btn3 = dialog.findViewById<Button>(R.id.btn3)
                                val btn4 = dialog.findViewById<Button>(R.id.btn4)
                                val btn5 = dialog.findViewById<Button>(R.id.btn5)
                                val btn6 = dialog.findViewById<Button>(R.id.btn6)
                                val btn7 = dialog.findViewById<Button>(R.id.btn7)
                                val btn8 = dialog.findViewById<Button>(R.id.btn8)
                                val btn9 = dialog.findViewById<Button>(R.id.btn9)
                                val btn0 = dialog.findViewById<Button>(R.id.btn0)
                                val btn00 = dialog.findViewById<Button>(R.id.btn00)
                                val btnsubmit = dialog.findViewById<Button>(R.id.btnsubmit)
                                val btndelete = dialog.findViewById<ImageView>(R.id.btndelete)
                                var sessionManager = SessionManager(this)
                                var user: HashMap<String, String> = sessionManager.userDetail
                                var no_ = user.get("NO")
                                close.setOnClickListener {
                                    dialog.dismiss()
                                    onResume()
                                }
                                btn1.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("1")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "1")
                                    }
                                }
                                btn2.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("2")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "2")
                                    }
                                }
                                btn3.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("3")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "3")
                                    }
                                }
                                btn4.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("4")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "4")
                                    }
                                }
                                btn5.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("5")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "5")
                                    }
                                }
                                btn6.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("6")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "6")
                                    }
                                }
                                btn7.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("7")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "7")
                                    }
                                }
                                btn8.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("8")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "8")
                                    }
                                }
                                btn9.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("9")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "9")
                                    }
                                }
                                btn0.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("0")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "0")
                                    }
                                }
                                btn00.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("00")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "00")
                                    }
                                }
                                btndelete.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                    } else {
                                        nominal.setText(nominal.text.toString().substring(0, nominal.text.toString().length - 1))
                                    }
                                }
                                btnsubmit.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
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
                                    } else {
                                        checkpinsend(nominal.text.toString(), no_.toString(), noHp)
//                                        Send(nominal.text.toString(), no_.toString(), no_hp)
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
                            bottomSheetView.findViewById<TextView>(R.id.title).setText("Nomornya belum terdaftar")
                            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Nomor tujuan belum terdaftar di InisiatorX. Ajak daftar InisiatorX yuk!")
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
                params["no_hp"] = no_hp
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun login(result: String) {
        val stringRequest: StringRequest = @SuppressLint("ResourceType")
        object : StringRequest(Method.POST, URL_FIND_MERCHANT,
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

                                val view = layoutInflater.inflate(R.layout.merchant, null);
                                val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                dialog.setContentView(view)
                                val txtnama = dialog.findViewById<TextView>(R.id.nama_merchant)
                                txtnama.setText(nama_merchant)
                                val nominal = dialog.findViewById<TextView>(R.id.nominal)
                                val close = dialog.findViewById<ImageView>(R.id.close)
                                val btn1 = dialog.findViewById<Button>(R.id.btn1)
                                val btn2 = dialog.findViewById<Button>(R.id.btn2)
                                val btn3 = dialog.findViewById<Button>(R.id.btn3)
                                val btn4 = dialog.findViewById<Button>(R.id.btn4)
                                val btn5 = dialog.findViewById<Button>(R.id.btn5)
                                val btn6 = dialog.findViewById<Button>(R.id.btn6)
                                val btn7 = dialog.findViewById<Button>(R.id.btn7)
                                val btn8 = dialog.findViewById<Button>(R.id.btn8)
                                val btn9 = dialog.findViewById<Button>(R.id.btn9)
                                val btn0 = dialog.findViewById<Button>(R.id.btn0)
                                val btn00 = dialog.findViewById<Button>(R.id.btn00)
                                val btnsubmit = dialog.findViewById<Button>(R.id.btnsubmit)
                                val btndelete = dialog.findViewById<ImageView>(R.id.btndelete)
                                var sessionManager = SessionManager(this)
                                var user: HashMap<String, String> = sessionManager.userDetail
                                var no_ = user.get("NO")
                                close.setOnClickListener {
                                    dialog.dismiss()
                                    onResume()
                                }
                                btn1.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("1")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "1")
                                    }
                                }
                                btn2.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("2")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "2")
                                    }
                                }
                                btn3.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("3")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "3")
                                    }
                                }
                                btn4.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("4")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "4")
                                    }
                                }
                                btn5.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("5")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "5")
                                    }
                                }
                                btn6.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("6")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "6")
                                    }
                                }
                                btn7.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("7")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "7")
                                    }
                                }
                                btn8.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("8")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "8")
                                    }
                                }
                                btn9.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("9")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "9")
                                    }
                                }
                                btn0.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("0")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "0")
                                    }
                                }
                                btn00.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                        nominal.setText("00")
                                    } else {
                                        nominal.setText(nominal.text.toString() + "00")
                                    }
                                }
                                btndelete.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
                                    } else {
                                        nominal.setText(nominal.text.toString().substring(0, nominal.text.toString().length - 1))
                                    }
                                }
                                btnsubmit.setOnClickListener {
                                    if (nominal.text.equals("Rp0")) {
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
                                    } else {
                                        checkpin(no_!!, id_merchant, nominal.text.toString())
//                                        pay(no_!!, id_merchant, nominal.text.toString())
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

    private fun Send(nominall: String, no_: String, tujuan: String) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, this.URL_SEND,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            startActivity(Intent(this, Success::class.java))
                        } else {
                            val bottomSheetDialog = BottomSheetDialog(
                                    this, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(this)
                                    .inflate(
                                            R.layout.layout_bottom_notif,
                                            null
                                    )
                            bottomSheetView.findViewById<TextView>(R.id.title).setText("Saldo Tidak Cukup!")
                            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Saldo Kamu Tidak Cukup dan Pastikan Nominal Diatas 100")
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
                        startActivity(Intent(this, Failed::class.java))
                    }
                },
                Response.ErrorListener {
                    startActivity(Intent(this, Failed::class.java))
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["asal"] = no_
                params["tujuan"] = tujuan
                params["nominal"] = nominall
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    private fun pay(no: String, merchant_id: String, nominal: String) {
        val stringRequest = object : StringRequest(Request.Method.POST, URL_PAY,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success == "1") {
                            startActivity(Intent(this, Success::class.java))
                        } else if (success == "0") {
                            val bottomSheetDialog = BottomSheetDialog(
                                    this, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(this)
                                    .inflate(
                                            R.layout.layout_bottom_notif,
                                            null
                                    )
                            bottomSheetView.findViewById<TextView>(R.id.title).setText("Saldo Tidak Cukup!")
                            bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Saldo Kamu Tidak Cukup. Silahkan lakukan topup segera agar kamu bisa bayar ini itu sekarang juga.")
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
                        startActivity(Intent(this, Failed::class.java))
                    }
                }, Response.ErrorListener { _ ->
            startActivity(Intent(this, Failed::class.java))
        }) {
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

    private fun checkpin(no: String, merchant_id: String, nominal: String) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(PinViewEventListener { _, _ -> //Make api calls here or what not
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
                pay(no, merchant_id, nominal)
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

    private fun checkpinsend(nominall: String, no_: String, tujuan: String) {
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(PinViewEventListener { _, _ -> //Make api calls here or what not
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
                Send(nominall, no_, tujuan)
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
