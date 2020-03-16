package app.inisiator.myapplication.tab_notifikasi

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.*
import app.inisiator.myapplication.models.QrNotif
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_notifikasi_tab.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class TransaksiFragment : Fragment() {

    private val URL_USERNOTIF = "https://awalspace.com/app/imbalopunyajangandiganggu/getusernotif.php"
    var userNotifList: MutableList<UserNotif>? = null
    var recyclerView: RecyclerView? = null
    private lateinit var swipeContainer: SwipeRefreshLayout
    var sessionManager: SessionManager? = null

    fun Notif() {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root: View = inflater.inflate(R.layout.fragment_transaksi_tab, container, false)
        val main = MainActivity()
        main.status(false, activity)
        sessionManager = SessionManager(context)
        val userr: HashMap<String, String> = sessionManager!!.userDetail
        val no_ = userr["NO"]
        swipeContainer = root.findViewById(R.id.swipe_transaksi_tab)
        userNotifList = ArrayList()
        recyclerView = root.findViewById(R.id.recylcerView)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(root.context))
        swipeContainer.setOnRefreshListener {
            loadnotif(no_)
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        var transaksiSession: TransaksiSession? = null
        transaksiSession = TransaksiSession(activity)
        val user1: java.util.HashMap<String, String> = transaksiSession!!.transaksiSession
        val ARRAY = user1.get("ARRAY")
        if (ARRAY.equals(null))
        {
            val shimmer : ShimmerFrameLayout
            shimmer = root.findViewById(R.id.shimmer11)
            shimmer.startShimmer()
            loadnotif(no_)
        }
        else{
            val array = JSONArray(ARRAY)

            //traversing through all the object
            for (i in 0 until array.length()) {

                //getting product object from json array
                val notif = array.getJSONObject(i)

                //adding the product to product list
                userNotifList!!.add(UserNotif(
                        notif.getInt("id"),
                        notif.getString("isi_notif"),
                        notif.getString("asal_notif"),
                        notif.getString("type_notif"),
                        notif.getString("catatan")
                ))
                recyclerView!!.visibility = View.VISIBLE
            }

            //creating adapter object and setting it to recyclerview
            val adapter = UsernotifAdapter(context, userNotifList)
            recyclerView!!.adapter = adapter
            val shimmer : ShimmerFrameLayout
            val shimmerr : RelativeLayout
            val main : LinearLayout
            main = root.findViewById(R.id.mainn)
            shimmerr = root.findViewById(R.id.shimmerrrr)
            shimmer = root.findViewById(R.id.shimmer11)
            shimmerr.visibility = View.GONE
            main.visibility = View.VISIBLE
            shimmer.stopShimmer()
            val mainn = MainActivity()
            mainn.status(true, activity)
        }
        return root
    }

    private fun loadnotif(no: String?) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_USERNOTIF,
                Response.Listener { response ->
                    try {
                        //converting the string to json array object
                        val array = JSONArray(response)

                        var transaksiSession: TransaksiSession? = null
                        transaksiSession = TransaksiSession(context!!)
                        transaksiSession.createSession(array)
                        //traversing through all the object
                        for (i in 0 until array.length()) {

                            //getting product object from json array
                            val notif = array.getJSONObject(i)

                            //adding the product to product list
                            userNotifList!!.add(UserNotif(
                                    notif.getInt("id"),
                                    notif.getString("isi_notif"),
                                    notif.getString("asal_notif"),
                                    notif.getString("type_notif"),
                                    notif.getString("catatan")
                            ))
                            recyclerView!!.visibility = View.VISIBLE
                        }

                        //creating adapter object and setting it to recyclerview
                        val adapter = UsernotifAdapter(context, userNotifList)
                        recyclerView!!.adapter = adapter
                        mainn?.visibility = View.VISIBLE
                        shimmerrrr?.visibility = View.GONE
                        shimmer11?.stopShimmer()
                        val main = MainActivity()
                        main.status(true, activity)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        recyclerView!!.visibility = View.VISIBLE
                    }
                },
                Response.ErrorListener { //                        Toast.makeText(getContext(), "Error "+error.toString(),Toast.LENGTH_SHORT).show();
                    recyclerView!!.visibility = View.VISIBLE
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["no"] = no!!
                return params
            }
        }

        //adding our stringrequest to queue
        Volley.newRequestQueue(context).add(stringRequest)
    }

}