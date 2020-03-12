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
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.EventDetailActivity
import app.inisiator.myapplication.R
import app.inisiator.myapplication.models.AvailableTicket
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_kegiatan.*
import kotlinx.android.synthetic.main.ticket_event.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class KegiatanFragment : Fragment() {

    private var webView: WebView? = null
    private var spinKitView: SpinKitView? = null
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_kegiatan, container, false)

        swipeContainer = root.findViewById(R.id.swipe_kegiatan_tab)

        swipeContainer.setOnRefreshListener {
//            fetchdata()
            loadWeb()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        webView = root.findViewById(R.id.webView)
        spinKitView = root.findViewById(R.id.spin_kit)
        webView!!.webViewClient = AppWebViewClients()
        loadWeb()

//        fetchdata()

        return root
    }

    private fun loadWeb() {
        webView!!.loadUrl("https://www.inisiator.com/event/")
        val wVSettings = webView!!.settings
        wVSettings.javaScriptEnabled = true
        webView!!.canGoBack()
        webView!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && webView!!.canGoBack()
            ) {
                webView!!.goBack()
                return@OnKeyListener true
            }
            false
        })
    }

//    private fun fetchdata() {
//        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/eventTicket.php"
//        val strReq = object : StringRequest(Method.POST, url,
//                Response.Listener { response ->
//                    val adapter = GroupAdapter<ViewHolder>()
//
//                    try {
//                        val array = JSONArray(response)
//
//                        for (i in 0 until array.length()) {
//                            val jsonObject = array.getJSONObject(i)
//
//                            val getTitle = jsonObject.getString("title")
//                            val getLokasi = jsonObject.getString("lokasi")
//                            val getTanggal = jsonObject.getString("tanggal")
//                            val getWaktu = jsonObject.getString("waktu")
//                            val getHarga = jsonObject.getInt("harga")
//
//                            // Convert Y-m-d to Month, day Year / F, d Y
//
//                            adapter.add(TicketItem(
//                                    AvailableTicket(
//                                            getTitle,
//                                            getLokasi,
//                                            getTanggal,
//                                            getWaktu,
//                                            getHarga
//                                    ),
//                                    context!!
//                            ))
//                        }
//
//                        recyclerview_event_tiket.adapter = adapter
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                        Log.e("Event", e.toString())
//                    }
//
//                }, Response.ErrorListener {  }) {
//
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                return HashMap()
//            }
//        }
//
//        val requestQueue = Volley.newRequestQueue(activity)
//        requestQueue.add(strReq)
//    }

    inner class AppWebViewClients : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            // TODO Auto-generated method stub
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
            spinKitView!!.visibility = View.GONE
            webView!!.visibility = View.VISIBLE
        }
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
        viewHolder.itemView.event_time.text = "${tiket.tanggal} | ${tiket.waktu}"

        viewHolder.itemView.event_detail.setOnClickListener {
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