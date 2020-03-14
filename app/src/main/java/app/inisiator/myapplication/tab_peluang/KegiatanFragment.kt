package app.inisiator.myapplication.tab_peluang

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.ArtikelSession
import app.inisiator.myapplication.EventDetailActivity
import app.inisiator.myapplication.KegiatanSession
import app.inisiator.myapplication.R
import app.inisiator.myapplication.models.AvailableTicket
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_artikel.*
import kotlinx.android.synthetic.main.fragment_kegiatan.*
import kotlinx.android.synthetic.main.fragment_kegiatan.main
import kotlinx.android.synthetic.main.fragment_kegiatan.shimmer1
import kotlinx.android.synthetic.main.fragment_kegiatan.shimmerrr
import kotlinx.android.synthetic.main.ticket_event.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class KegiatanFragment : Fragment() {

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_kegiatan, container, false)

        swipeContainer = root.findViewById(R.id.swipe_kegiatan_tab)

        swipeContainer.setOnRefreshListener {
            fetchdata()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        var kegiatanSession: KegiatanSession? = null
        kegiatanSession = KegiatanSession(activity)
        val user1: java.util.HashMap<String, String> = kegiatanSession!!.kegiatanSession
        val ARRAY = user1.get("ARRAY")
        if (ARRAY.equals(null))
        {
            val shimmer : ShimmerFrameLayout
            shimmer = root.findViewById(R.id.shimmer1)
            shimmer.startShimmer()
            fetchdata()
        }
        else{
            val array = JSONArray(ARRAY)
            val adapter = GroupAdapter<ViewHolder>()
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val getTitle = jsonObject.getString("title")
                val getLokasi = jsonObject.getString("lokasi")
                val getTanggal = jsonObject.getString("tanggal")
                val getWaktu = jsonObject.getString("waktu")
                val getHarga = jsonObject.getInt("harga")
                adapter.add(TicketItem(
                        AvailableTicket(
                                getTitle,
                                getLokasi,
                                getTanggal,
                                getWaktu,
                                getHarga
                        ),
                        context!!
                ))
            }
            val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_event_tiket)
            recyclerView.adapter = adapter
            val shimmer : ShimmerFrameLayout
            val shimmerr : RelativeLayout
            val main : LinearLayout
            main = root.findViewById(R.id.main)
            shimmerr = root.findViewById(R.id.shimmerrr)
            shimmer = root.findViewById(R.id.shimmer1)
            shimmerr.visibility = View.GONE
            main.visibility = View.VISIBLE
            shimmer.stopShimmer()
        }

        return root
    }

    private fun fetchdata() {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/eventTicket.php"
        val strReq = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val adapter = GroupAdapter<ViewHolder>()

                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            val jsonObject = array.getJSONObject(i)

                            val getTitle = jsonObject.getString("title")
                            val getLokasi = jsonObject.getString("lokasi")
                            val getTanggal = jsonObject.getString("tanggal")
                            val getWaktu = jsonObject.getString("waktu")
                            val getHarga = jsonObject.getInt("harga")

                            // Convert Y-m-d to Month, day Year / F, d Y
                            var kegiatanSession: KegiatanSession? = null
                            kegiatanSession = KegiatanSession(context!!)
                            kegiatanSession.createSession(array)

                            adapter.add(TicketItem(
                                    AvailableTicket(
                                            getTitle,
                                            getLokasi,
                                            getTanggal,
                                            getWaktu,
                                            getHarga
                                    ),
                                    context!!
                            ))
                        }

                        recyclerview_event_tiket.adapter = adapter
                        main?.visibility = View.VISIBLE
                        shimmerrr?.visibility = View.GONE
                        shimmer1?.stopShimmer()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("Event", e.toString())
                    }

                }, Response.ErrorListener {  }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return HashMap()
            }
        }

        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(strReq)
    }

}

class TicketItem(val tiket: AvailableTicket,val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.ticket_event
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.event_title.text = tiket.title
        viewHolder.itemView.event_loc.text = tiket.lokasi
        viewHolder.itemView.event_time.text = "${tiket.tanggal}"

        viewHolder.itemView.utama.setOnClickListener {
            val intent = Intent(context, EventDetailActivity::class.java)
            intent.putExtra("EVENT_TITLE", tiket.title)
            intent.putExtra("EVENT_LOKASI", tiket.lokasi)
            intent.putExtra("EVENT_TANGGAL", tiket.tanggal)
            intent.putExtra("EVENT_WAKTU", tiket.waktu)
            intent.putExtra("EVENT_HARGA", tiket.harga.toInt())
            context.startActivity(intent)
        }
    }
}