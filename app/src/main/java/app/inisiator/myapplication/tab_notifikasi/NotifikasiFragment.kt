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
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.*
import app.inisiator.myapplication.models.QrNotif
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
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
        val main = MainActivity()
        main.status(false, activity)
        swipeContainer.setOnRefreshListener {
            fetchNotif()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        var notifikasiSession: NotifikasiSession?
        notifikasiSession = NotifikasiSession(activity)
        val user1: java.util.HashMap<String, String> = notifikasiSession.notifikasiSession
        val ARRAY = user1.get("ARRAY")
        if (ARRAY.equals(null))
        {
            val shimmer : ShimmerFrameLayout
            shimmer = root.findViewById(R.id.shimmer11)
            shimmer.startShimmer()
            fetchNotif()
        }
        else{
            val array = JSONArray(ARRAY)
            val adapter = GroupAdapter<ViewHolder>()
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
            val recyclerView = root.findViewById<RecyclerView>(R.id.fragmentnotifikasi_recyclerview)
            recyclerView.adapter = adapter
            val shimmer : ShimmerFrameLayout
            val shimmerr : RelativeLayout
            val main1 : LinearLayout
            main1 = root.findViewById(R.id.mainn)
            shimmerr = root.findViewById(R.id.shimmerrrr)
            shimmer = root.findViewById(R.id.shimmer11)
            shimmerr.visibility = View.GONE
            main1.visibility = View.VISIBLE
            shimmer.stopShimmer()
            val mainn = MainActivity()
            mainn.status(true, activity)
        }

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
                                var notifikasiSession: NotifikasiSession?
                                notifikasiSession = NotifikasiSession(context!!)
                                notifikasiSession.createSession(array)

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
                        mainn?.visibility = View.VISIBLE
                        shimmerrrr?.visibility = View.GONE
                        shimmer11?.stopShimmer()
                        val main = MainActivity()
                        main.status(true, activity)
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