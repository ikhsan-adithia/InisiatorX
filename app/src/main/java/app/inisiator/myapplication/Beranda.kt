package app.inisiator.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.models.LeaderBoard
import app.inisiator.myapplication.models.TopThree
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.alertdialog_leaderboard.view.*
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.item_big_3.view.*
import kotlinx.android.synthetic.main.item_leaderboard.view.*
import org.json.JSONArray
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
    var temporarySession: TemporarySession? = null
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
        val btnRequest = root.findViewById<Button>(R.id.btnRequest)
        var sessionManager = SessionManager(activity)
        val user: HashMap<String, String> = sessionManager.userDetail
        val no = user.get("NO")
        val emaill = user.get("EMAIL")
        val passwordd = user.get("PASSWORD")
        val nama = user.get("NAMA")
        val photo = user.get("PHOTO")
        temporarySession = TemporarySession(activity)
        swipeContainer = root.findViewById(R.id.swipe_home)

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
//            btnprint.setOnClickListener { startActivity(Intent(activity, Printmenu::class.java)) }
            val btnmutabaah = bottomSheetView.findViewById<LinearLayout>(R.id.mutabaah)
            btnmutabaah.setOnClickListener {
                type(no!!)
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
        val user1: HashMap<String, String> = temporarySession!!.temporarySession
        val balance = user1.get("BALANCE")
        val nama1 = user1.get("NAMA1")
        val nama2 = user1.get("NAMA2")
        val nama3 = user1.get("NAMA3")
        val point1 = user1.get("POINT1")
        val point2 = user1.get("POINT2")
        val point3 = user1.get("POINT3")
        val photo1 = user1.get("PHOTO1")
        val photo2 = user1.get("PHOTO2")
        val photo3 = user1.get("PHOTO3")
        val shimmer = root.findViewById<ShimmerFrameLayout>(R.id.shimmer1)
        val userdetails = root.findViewById<RelativeLayout>(R.id.userDetailsHome)
        val spinKitView = root.findViewById<SpinKitView>(R.id.spin_kit)
        if (balance == null){
            shimmer.startShimmer()
            takebalance(emaill!!, passwordd!!)
        }
        else{
            txtBalance?.setText(balance)
            val adapter: GroupAdapter<*> = GroupAdapter<ViewHolder>()
            adapter.add(UserItem(TopThree(nama1, point1, photo1), 1, context!!) )
            adapter.add(UserItem(TopThree(nama2, point2, photo2), 2, context!!))
            adapter.add(UserItem(TopThree(nama3, point3, photo3), 3, context!!))
            recyclerView!!.adapter = adapter
            shimmer.visibility = View.GONE
            spinKitView.visibility = View.GONE
            recyclerView!!.visibility = View.VISIBLE
            userdetails.visibility = View.VISIBLE
        }

        return root
    }

    fun takebalance(email: String, password: String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/balance.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        val jsonArray = jsonObject.getJSONArray("profile")

                        if (success == "1") {
                            for (i in 0 until jsonArray.length()) {
                                var `object` = jsonArray.getJSONObject(i)
                                var balance = `object`.getString("balance").trim()
                                var balanceint = Integer.parseInt(balance, 10)
                                myBalance?.text = NumberFormat.getNumberInstance(Locale.US).format(balanceint)
                                var referral = `object`.getString("referral").trim()
                                var point = `object`.getString("point").trim()
                                fetchTopThree(balanceint.toString(), referral , point)
                            }

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

    private fun fetchTopThree(balance:String, referral:String, point:String) {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/leaderboard.php"
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val adapter: GroupAdapter<*> = GroupAdapter<ViewHolder>()
                    var index = 0
                    try {
                        val array = JSONArray(response)
                        for (i in 0 until array.length()) {
                            val jsonObject = array.getJSONObject(i)
                            index++
                            val getNama = jsonObject.getString("name")
                            val getPoint = jsonObject.getString("point")
                            val getPhoto = jsonObject.getString("photo")
                            if (index < 4) {
                                adapter.add(UserItem(
                                        TopThree(getNama, getPoint, getPhoto), index,context!!
                                ))
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
                        }

                        val arrayIndex1 = array.getJSONObject(0)
                        val arrayIndex2 = array.getJSONObject(1)
                        val arrayIndex3 = array.getJSONObject(2)
                        var namaArray = arrayOf(
                                arrayIndex1.getString("name"),
                                arrayIndex2.getString("name"),
                                arrayIndex3.getString("name")
                        )

                        var pointArray = arrayOf(
                                arrayIndex1.getString("point"),
                                arrayIndex2.getString("point"),
                                arrayIndex3.getString("point")
                        )

                        var photoArray = arrayOf(
                                arrayIndex1.getString("photo"),
                                arrayIndex2.getString("photo"),
                                arrayIndex3.getString("photo")
                        )


                        temporarySession!!.createSession(balance, namaArray[0],namaArray[1],namaArray[2], pointArray[0], pointArray[1], pointArray[2], photoArray[0], photoArray[1], photoArray[2], referral , point, "Periksa")

                        berandaTopThree_recyclerview.adapter = adapter
                    } catch (e: JSONException) {
                    }
                }, Response.ErrorListener {
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return HashMap()
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

internal class UserItem(val topThree: TopThree, val index: Int?, val context: Context) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        if (topThree.photo != "") {
            Glide.with(context).load(topThree.photo).into(viewHolder.itemView.profilePicture_lb)
        } else {
            viewHolder.itemView.profilePicture_lb.setImageResource(R.drawable.profile_inisiator)
        }


        viewHolder.itemView.peringkat.text = "Top ${index.toString()} Global"
        viewHolder.itemView.menu_label.text = "${index.toString()}"
        viewHolder.itemView.name.text = topThree.nama
        viewHolder.itemView.item_big_point.text = "Point " + topThree.point.toString()

        viewHolder.itemView.showLeaderboard.setOnClickListener {
            // TODO: Open Leaderboard Dialog
            val dialogView = LayoutInflater.from(context).inflate(R.layout.alertdialog_leaderboard, null)
            val builder = AlertDialog.Builder(context).setView(dialogView).show()
            builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var sessionManager = SessionManager(context)
            val user: HashMap<String, String> = sessionManager.userDetail
            val nama = user.get("NAMA")

            val stringRequest = object : StringRequest(Method.POST, "https://awalspace.com/app/imbalopunyajangandiganggu/leaderboardlist.php",
                    Response.Listener { response ->
                        val adapter = GroupAdapter<ViewHolder>()
                        var index = 0

                        try {
                            val array = JSONArray(response)

                            for (i in 0 until array.length()) {
                                val jsonObject = array.getJSONObject(i)

                                index++

                                val getName = jsonObject.getString("name")
                                val getPoint = jsonObject.getString("point")
                                val getPhoto = jsonObject.getString("photo")

                                adapter.add(LeaderboardItem(
                                        LeaderBoard(
                                                getName,
                                                getPoint,
                                                getPhoto
                                        ), index
                                ))

                                if (getName == nama) {
                                    dialogView.txtMyPosition_dialogleaderboad.text = index.toString()
                                    when (index % 10) {
                                        1 -> dialogView.txtMyPositionSufix_dialogleaderboad.text = "st"
                                        2 -> dialogView.txtMyPositionSufix_dialogleaderboad.text = "nd"
                                        3 -> dialogView.txtMyPositionSufix_dialogleaderboad.text = "rd"
                                        else -> dialogView.txtMyPositionSufix_dialogleaderboad.text = "th"
                                    }
                                    if (getPhoto != "") {
                                        Glide.with(context).load(getPhoto).into(dialogView.imgMyPhoto_diaogleaderboard)
                                    } else {
                                        dialogView.imgMyPhoto_diaogleaderboard.setImageResource(R.drawable.profile_inisiator)
                                    }
                                    dialogView.txtMyPoints_dialogleaderboad.text = getPoint

                                    val temporarySession = TemporarySession(context)
                                    val user1: HashMap<String, String> = temporarySession!!.temporarySession
                                    val balance = user1.get("BALANCE")
                                    val nama1 = user1.get("NAMA1")
                                    val nama2 = user1.get("NAMA2")
                                    val nama3 = user1.get("NAMA3")
                                    val point1 = user1.get("POINT1")
                                    val point2 = user1.get("POINT2")
                                    val point3 = user1.get("POINT3")
                                    val photo1 = user1.get("PHOTO1")
                                    val photo2 = user1.get("PHOTO2")
                                    val photo3 = user1.get("PHOTO3")
                                    val referral = user1.get("REFERRAl")
                                    val point = user1.get("POINT")
                                    temporarySession.logout()
                                    temporarySession.createSession(balance, nama1,nama2,nama3, point1, point2, point3, photo1, photo2, photo3, referral , point, index.toString())
                                }
                            }

                            dialogView.rv_dialogleaderboard.adapter = adapter
                        } catch (e: JSONException) {
                            Log.e("Leaderboard", e.toString())
                        }

                    }, Response.ErrorListener { }) {

                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    return HashMap()
                }
            }

            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)

        }
    }

override fun getLayout(): Int {
    return R.layout.item_big_3
}
}

class LeaderboardItem(private val user: LeaderBoard, private val indexPosition: Int): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.item_leaderboard
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (user.photo != "") {
            Glide.with(viewHolder.itemView).load(user.photo).into(viewHolder.itemView.imgProfile_leaderboarditem)
        } else {
            viewHolder.itemView.imgProfile_leaderboarditem.setImageResource(R.drawable.profile_inisiator)
        }
        viewHolder.itemView.txtPosition_leaderboarditem.text = indexPosition.toString()
        viewHolder.itemView.txtName_leaderboarditem.text = user.name
        viewHolder.itemView.txtPoints_leaderboarditem.text = user.point.toString()
    }
}