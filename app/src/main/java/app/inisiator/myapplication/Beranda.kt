package app.inisiator.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*

class Beranda : Fragment() {

    private var txtUserName: TextView? = null
    private var txtUserEmail: TextView? = null
    private var txtBalance: TextView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var root: View
    var sessionManager: SessionManager? = null
    private lateinit var swipeContainer: SwipeRefreshLayout
    var keren = 0
    private val URL_FIND = "https://awalspace.com/app/imbalopunyajangandiganggu/findsend.php"
    private val URL_REQUEST = "https://awalspace.com/app/imbalopunyajangandiganggu/request.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_beranda, container, false)
        val lainnya = root.findViewById<Button>(R.id.btnLainnya)
        var sessionManager = SessionManager(activity)
        val user: HashMap<String, String> = sessionManager.userDetail
        val no = user.get("NO")
        val emaill = user.get("EMAIL")
        val passwordd = user.get("PASSWORD")
        val nama = user.get("NAMA")
        val photo = user.get("PHOTO")
        val balance = user.get("BALANCE")
        val referral = user.get("REFERRAL")
        val point = user.get("POINT")
        sessionManager = SessionManager(activity)
        swipeContainer = root.findViewById(R.id.swipe_home)
        val main = MainActivity()
        main.status(false, activity)
        swipeContainer.setOnRefreshListener {
            takebalance(emaill!!, passwordd!!)
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        lainnya.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(
                    context!!, R.style.BottomSheetDialogTheme
            )
            val bottomSheetView = LayoutInflater.from(context)
                    .inflate(
                            R.layout.layout_bottom_sheet,
                            null
                    )
            bottomSheetView.findViewById<View>(R.id.close).setOnClickListener { bottomSheetDialog.cancel() }
            bottomSheetDialog.setContentView(bottomSheetView)
            val btnprint = bottomSheetView.findViewById<LinearLayout>(R.id.print)
            btnprint.setOnClickListener { startActivity(Intent(activity, Printmenu::class.java)) }
            val btnmutabaah = bottomSheetView.findViewById<LinearLayout>(R.id.mutabaah)
            btnmutabaah.setOnClickListener {
                type(no!!)
            }
            val eshopping = bottomSheetView.findViewById<LinearLayout>(R.id.eshopping)
            eshopping.setOnClickListener {
                startActivity(Intent(activity, Eshopping::class.java))
            }
            bottomSheetDialog.show()
        }

        val btnpay = root.findViewById<Button>(R.id.btnPay)
        btnpay.setOnClickListener {
            startActivity(Intent(activity, ScanAll::class.java))
        }

        val btntopup = root.findViewById<Button>(R.id.btnTopUp)
        btntopup.setOnClickListener {
            startActivity(Intent(activity, Scanner2::class.java))
        }

        val btnrequst = root.findViewById<Button>(R.id.btnRequest)
        btnrequst.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.findphone, null);
            val dialog = Dialog(activity!!, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
            dialog.setContentView(view)
            val close = dialog.findViewById<ImageView>(R.id.close)
            val editText = dialog.findViewById<EditText>(R.id.editText)
            val title = dialog.findViewById<TextView>(R.id.title)
            val explain1 = dialog.findViewById<TextView>(R.id.explain1)
            val explain2 = dialog.findViewById<TextView>(R.id.explain2)
            val bayar = dialog.findViewById<TextView>(R.id.bayar)
            val subs = dialog.findViewById<TextView>(R.id.subs)
            subs.setText("Klik di sini untuk lanjut ke permintaan")
            bayar.setText("Minta ke ")
            explain1.setText("Minta saldo via nomor Handphone!")
            explain2.setText("Fitur ini adalah fitur untuk meeminta saldo kepada temanmu dengan nomor handphone saja.")
            title.setText("Minta via nomor HP")
            editText.setOnFocusChangeListener(object: View.OnFocusChangeListener {
                override fun onFocusChange(view:View, b:Boolean) {
                    val subtitle = dialog.findViewById<LinearLayout>(R.id.subtitle)
                    val hasil = dialog.findViewById<LinearLayout>(R.id.hasil)
                    subtitle.visibility = View.INVISIBLE
                    hasil.visibility = View.VISIBLE
                }
            })
            editText.addTextChangedListener(object: TextWatcher {
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

        txtUserName = root.findViewById(R.id.userName_HomeContent)
        txtUserEmail = root.findViewById(R.id.userEmail_HomeContent)
        txtBalance = root.findViewById(R.id.myBalance)
        recyclerView = root.findViewById(R.id.berandaTopThree_recyclerview)

        if (photo.equals("")) {
        } else {
            val profilee = root.findViewById<View>(R.id.profilePicture_Home) as CircleImageView
            Glide.with(activity!!).load(photo).into(profilee)
        }
        txtUserName?.text = nama
        txtUserEmail?.text = emaill

        val shimmer = root.findViewById<ShimmerFrameLayout>(R.id.shimmer1)
        val userdetails = root.findViewById<RelativeLayout>(R.id.userDetailsHome)
        val spinKitView = root.findViewById<SpinKitView>(R.id.spin_kit)
        txtBalance?.setText(NumberFormat.getNumberInstance(Locale.US).format(balance!!.toInt()))
        shimmer.visibility = View.GONE
        spinKitView.visibility = View.GONE
        recyclerView!!.visibility = View.VISIBLE
        userdetails.visibility = View.VISIBLE
        main.status(true, activity)

        return root
    }

    fun takebalance(email: String, password: String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/login.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("login")

                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                var `object` = jsonArray.getJSONObject(i)
                                val no_hp = `object`.getString("no_hp").trim { it <= ' ' }
                                val name = `object`.getString("name").trim { it <= ' ' }
                                val photo = `object`.getString("photo").trim { it <= ' ' }
                                val balance = `object`.getString("balance").trim { it <= ' ' }
                                val referral = `object`.getString("referral").trim { it <= ' ' }
                                val point = `object`.getString("point").trim { it <= ' ' }
                                val sessionManager = SessionManager(activity)
                                sessionManager.logout()
                                sessionManager.createSession(email, password, no_hp, name, photo, balance, referral, point)
                                txtUserName?.text = name
                                txtUserEmail?.text = email
                                txtBalance?.setText(NumberFormat.getNumberInstance(Locale.US).format(balance!!.toInt()))
                                val shimmer = root.findViewById<ShimmerFrameLayout>(R.id.shimmer1)
                                val userdetails = root.findViewById<RelativeLayout>(R.id.userDetailsHome)
                                val spinKitView = root.findViewById<SpinKitView>(R.id.spin_kit)
                                recyclerView = root.findViewById(R.id.berandaTopThree_recyclerview)
                                shimmer.stopShimmer()
                                spinKitView.visibility = View.GONE
                                recyclerView!!.visibility = View.VISIBLE
                                shimmer.visibility = View.GONE
                                userdetails.visibility = View.VISIBLE
                            }

                            val main = MainActivity()
                            main.status(true, activity)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val bottomSheetDialog = BottomSheetDialog(
                                activity!!, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(activity!!)
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
                }, Response.ErrorListener { error ->
                        val bottomSheetDialog = BottomSheetDialog(
                                activity!!, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(activity!!)
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
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
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
                                val no_hp = `object`.getString("no_hp").trim { it <= ' ' }
                                val name = `object`.getString("name").trim { it <= ' ' }
                                val photo = `object`.getString("photo").trim { it <= ' ' }
                                val view = layoutInflater.inflate(R.layout.merchant, null);
                                val dialog = Dialog(activity!!, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                                dialog.setContentView(view)
                                val txtnama = dialog.findViewById<TextView>(R.id.nama_merchant)
                                if (photo.equals("")){}else{
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
                                var sessionManager = SessionManager(activity!!)
                                var user: HashMap<String, String> = sessionManager.userDetail
                                var no_ = user.get("NO")
                                close.setOnClickListener {
                                    dialog.dismiss()
                                    onResume()
                                }
                                btn1.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("1")}
                                    else{nominal.setText(nominal.text.toString() + "1")}
                                }
                                btn2.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("2")}
                                    else{nominal.setText(nominal.text.toString() + "2")}
                                }
                                btn3.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("3")}
                                    else{nominal.setText(nominal.text.toString() + "3")}
                                }
                                btn4.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("4")}
                                    else{nominal.setText(nominal.text.toString() + "4")}
                                }
                                btn5.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("5")}
                                    else{nominal.setText(nominal.text.toString() + "5")}
                                }
                                btn6.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("6")}
                                    else{nominal.setText(nominal.text.toString() + "6")}
                                }
                                btn7.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("7")}
                                    else{nominal.setText(nominal.text.toString() + "7")}
                                }
                                btn8.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("8")}
                                    else{nominal.setText(nominal.text.toString() + "8")}
                                }
                                btn9.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("9")}
                                    else{nominal.setText(nominal.text.toString() + "9")}
                                }
                                btn0.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("0")}
                                    else{nominal.setText(nominal.text.toString() + "0")}
                                }
                                btn00.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){nominal.setText("00")}
                                    else{nominal.setText(nominal.text.toString() + "00")}
                                }
                                btndelete.setOnClickListener {
                                    if (nominal.text.equals("Rp0")){}
                                    else{nominal.setText(nominal.text.toString().substring(0, nominal.text.toString().length - 1))}
                                }
                                btnsubmit.setOnClickListener {
                                    if (nominal.text.equals("Rp0") && nominal.text.equals("") && nominal.text.length.equals("0")){
                                        val bottomSheetDialog = BottomSheetDialog(
                                                activity!!, R.style.BottomSheetDialogTheme
                                        )
                                        val bottomSheetView = LayoutInflater.from(activity!!)
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
                                        Request(nominal.text.toString(), no_.toString(), no_hp )
                                    }
                                }
                                dialog.show()

                            }
                        } else if (success == "0") {
                            val bottomSheetDialog = BottomSheetDialog(
                                    activity!!, R.style.BottomSheetDialogTheme
                            )
                            val bottomSheetView = LayoutInflater.from(activity!!)
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
                                activity!!, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(activity!!)
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
                            activity!!, R.style.BottomSheetDialogTheme
                    )
                    val bottomSheetView = LayoutInflater.from(activity!!)
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
        val requestQueue = Volley.newRequestQueue(activity!!)
        requestQueue.add(stringRequest)
    }


    private fun Request(nominall: String, no_: String, no_tujuan :String) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, this.URL_REQUEST,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            startActivity(Intent(activity, Success::class.java))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        startActivity(Intent(activity, Failed::class.java))
                    }
                },
                Response.ErrorListener { error ->
                    startActivity(Intent(activity, Failed::class.java))
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["asal"] = no_
                params["tujuan"] = no_tujuan
                params["nominal"] = nominall
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(activity!!)
        requestQueue.add(stringRequest)
    }


    fun type(no: String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/checktype.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            startActivity(Intent(activity, Mutabaah::class.java))
                        }
                        else{
                            Toast.makeText(activity, "Maaf kamu tidak terdaftar sebagai anggota inisiator", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error ->
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params["no"] = no
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }
}