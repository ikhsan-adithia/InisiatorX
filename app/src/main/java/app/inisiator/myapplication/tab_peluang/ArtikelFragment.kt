package app.inisiator.myapplication.tab_peluang

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.R
import app.inisiator.myapplication.ReadActivity
import app.inisiator.myapplication.models.AvailArticle
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.avail_article_row.view.*
import kotlinx.android.synthetic.main.fragment_artikel.*
import kotlinx.android.synthetic.main.fragment_notifikasi_tab.*
import org.json.JSONArray
import org.json.JSONException

class ArtikelFragment : Fragment() {

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_artikel, container, false)

        swipeContainer = root.findViewById(R.id.swipe_artikel_tab)

        swipeContainer.setOnRefreshListener {
            fetchArtikel()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        fetchArtikel()

        return root
    }

    fun fetchArtikel() {
//        Toast.makeText(context!!, "Call fetchArticle()", Toast.LENGTH_SHORT).show()
        val strRequest = object : StringRequest(Method.POST, "https://awalspace.com/app/imbalopunyajangandiganggu/availablearticle.php",
            Response.Listener { response ->

                val adapter = GroupAdapter<ViewHolder>()

                try {
                    val array = JSONArray(response)

                    for (i in 0 until array.length()) {
                        val objects = array.getJSONObject(i)

                        val title = objects.getString("title")
                        val thumbnail = objects.getString("thumbnail")
                        val preview = objects.getString("preview")
                        val time = objects.getString("time")
                        val url = objects.getString("url")

                        adapter.add(ArticleItem(
                            context!!,
                            AvailArticle(
                                title,
                                thumbnail,
                                preview,
                                time,
                                url
                            )
                        )
                        )
                    }
                    recyclerview_artikel_fragment.adapter = adapter

                } catch (e: JSONException) {
                    Log.e("ArticleFragment", e.toString())
                }

            }, Response.ErrorListener {  }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return HashMap()
            }
        }

        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(strRequest)
    }
}

class ArticleItem(val context: Context, val item: AvailArticle): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.avail_article_row
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        if (item.thumbnail != "") {
            Glide.with(context).load(item.thumbnail).into(viewHolder.itemView.imgThumbnail_articlefrag)
        } else {
            viewHolder.itemView.imgThumbnail_articlefrag.setImageResource(R.drawable.ic_profile)
        }
        viewHolder.itemView.txtTitle_articlefrag.text = item.title
        viewHolder.itemView.txtPrev_articlefrag.text = item.preview
        viewHolder.itemView.txtTime_articlefrag.text = item.time + "\nmins"

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra("url", item.url)
            intent.putExtra("time", item.time)
            context.startActivity(intent)
        }
    }
}