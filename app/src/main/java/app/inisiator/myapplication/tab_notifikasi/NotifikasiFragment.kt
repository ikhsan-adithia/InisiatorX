package app.inisiator.myapplication.tab_notifikasi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.QrTicketPreviewActivity
import app.inisiator.myapplication.R
import app.inisiator.myapplication.SessionManager
import app.inisiator.myapplication.models.QrNotif
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_notifikasi_tab.*
import kotlinx.android.synthetic.main. notif_item.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class NotifikasiFragment : Fragment() {

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_notifikasi_tab, container, false)

        swipeContainer = root.findViewById(R.id.swipe_notif_tab)

        swipeContainer.setOnRefreshListener {
            fetchNotif()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        fetchNotif()

        return root
    }

    private fun fetchNotif() {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/fetchNotif.php"
        val strRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val adapter = GroupAdapter<ViewHolder>()

                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            val obj = array.getJSONObject(i)

                            val getQrcode = obj.getString("qrcode")
                            val getExpDate = obj.getString("exp_date")
                            val getTitle = obj.getString("event_title")
                            val getLokasi = obj.getString("lokasi")
                            val getWaktu = obj.getString("waktu")
                            val getEmail = obj.getString("buyer_email")
                            val getStatus = obj.getString("status")

                            val sessionManager =  SessionManager(activity)
                            val user: HashMap<String, String> = sessionManager.userDetail
                            val email = user["EMAIL"]

                            if (getEmail == email) {
                                adapter.add(
                                        NotifItem(
                                                QrNotif(
                                                        getQrcode,
                                                        getExpDate,
                                                        getTitle,
                                                        getLokasi,
                                                        getWaktu,
                                                        getEmail,
                                                        getStatus
                                                ), context!!
                                        )
                                )
                            }
                        }

                        fragmentnotifikasi_recyclerview.adapter = adapter
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener {
            it.printStackTrace()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return HashMap()
            }
        }

        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(strRequest)
    }
}

class NotifItem(private val notif: QrNotif, val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notif_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.notif_item_title.text = notif.event_title
        viewHolder.itemView.notif_item_lokasi.text = notif.lokasi
        viewHolder.itemView.notif_item_date.text = notif.exp_date

        if (notif.status == "not valid") {
            viewHolder.itemView.notif_item_status.text = notif.status
            viewHolder.itemView.notif_item_status.setTextColor(Color.RED)
        }

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, QrTicketPreviewActivity::class.java)
            val uri = context.getExternalFilesDir(null)?.absolutePath + "/inisiatorX/qrTicketEvent/" + notif.qrcode.toString() + ".png"
            Log.d("Notifikasi", uri)
            intent.putExtra("QR_URI", uri)
            intent.putExtra("EVENT_TITLE", notif.event_title)
            intent.putExtra("EVENT_LOKASI", notif.lokasi)
            intent.putExtra("EVENT_TANGGAL", notif.exp_date)
            intent.putExtra("EVENT_WAKTU", notif.waktu)
            context.startActivity(intent)
        }
    }
}