package app.inisiator.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.models.AvailableTicket
import app.inisiator.myapplication.models.Store
import app.inisiator.myapplication.tab_peluang.TicketItem
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.activity_store.view.*
import kotlinx.android.synthetic.main.fragment_kegiatan.*
import kotlinx.android.synthetic.main.store.*
import kotlinx.android.synthetic.main.store.view.*
import kotlinx.android.synthetic.main.store.view.product
import kotlinx.android.synthetic.main.ticket_event.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap
import kotlin.reflect.typeOf

class Store : AppCompatActivity() {

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val shimmer : ShimmerFrameLayout
        shimmer = findViewById(R.id.shimmer2)
        shimmer.startShimmer()
        fetchdata()
    }

    private fun fetchdata() {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/store.php"
        val strReq = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val adapter = GroupAdapter<ViewHolder>()

                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            val jsonObject = array.getJSONObject(i)

                            val getProduct = jsonObject.getString("product")
                            val getPhoto = jsonObject.getString("photo")
                            val getJenis = jsonObject.getString("jenis")
                            val getHarga = jsonObject.getInt("harga")

                            adapter.add(StoreItem(
                                    Store(
                                            getProduct,
                                            getPhoto,
                                            getJenis,
                                            getHarga
                                    ),
                                    this
                            ))
                        }

                        recyclerview_store.adapter = adapter
                        val main = findViewById<LinearLayout>(R.id.main)
                        main.visibility = View.VISIBLE
                        val shimer = findViewById<RelativeLayout>(R.id.shimmer)
                        shimer.visibility = View.GONE
                        val trueshimmer = findViewById<ShimmerFrameLayout>(R.id.shimmer2)
                        trueshimmer.stopShimmer()
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

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(strReq)
    }
}

class StoreItem(val item: app.inisiator.myapplication.models.Store,val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.store
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.product.text = item.product
        viewHolder.itemView.type.text = item.jenis
        viewHolder.itemView.stock.text = "${item.harga}" + " C / "

        viewHolder.itemView.minus.setOnClickListener{
            val jumlah =  Integer.parseInt(viewHolder.itemView.quantity.text.toString())
            val harga = Integer.parseInt(viewHolder.itemView.price.text.toString())
            val hasil = jumlah + 1
            val total = harga + item.harga
            viewHolder.itemView.quantity.text = hasil.toString()
            viewHolder.itemView.price.text = total.toString()
        }

        viewHolder.itemView.plus.setOnClickListener{
            val jumlah =  Integer.parseInt(viewHolder.itemView.quantity.text.toString())
            if (jumlah == 0)
            {

            }
            else{
                val harga = Integer.parseInt(viewHolder.itemView.price.text.toString())
                val hasil = jumlah - 1
                val total = harga - item.harga
                viewHolder.itemView.quantity.text = hasil.toString()
                viewHolder.itemView.price.text = total.toString()
            }
        }
    }
}
