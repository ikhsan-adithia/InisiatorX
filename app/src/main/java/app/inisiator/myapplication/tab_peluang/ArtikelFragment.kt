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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.*
import app.inisiator.myapplication.UserItem
import app.inisiator.myapplication.models.AvailArticle
import app.inisiator.myapplication.models.TopThree
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
        var artikelSession: ArtikelSession? = null
        artikelSession = ArtikelSession(activity)
        swipeContainer = root.findViewById(R.id.swipe_artikel_tab)

        swipeContainer.setOnRefreshListener {
            artikelSession.logout()
            fetchArtikel()
            Handler().postDelayed({
                if (swipeContainer.isRefreshing) {
                    swipeContainer.isRefreshing = false
                }
            }, 1000)
        }

        val user1: java.util.HashMap<String, String> = artikelSession!!.artikelSession
        val TITLE1 = user1.get("TITLE1")
        val TITLE2 = user1.get("TITLE2")
        val TITLE3 = user1.get("TITLE3")
        val TITLE4 = user1.get("TITLE4")
        val TITLE5 = user1.get("TITLE5")
        val PREVIEW1 = user1.get("PREVIEW1")
        val PREVIEW2 = user1.get("PREVIEW2")
        val PREVIEW3 = user1.get("PREVIEW3")
        val PREVIEW4 = user1.get("PREVIEW4")
        val PREVIEW5 = user1.get("PREVIEW5")
        val TIME1 = user1.get("TIME1")
        val TIME2 = user1.get("TIME2")
        val TIME3 = user1.get("TIME3")
        val TIME4 = user1.get("TIME4")
        val TIME5 = user1.get("TIME5")
        val URL1 = user1.get("URL1")
        val URL2 = user1.get("URL2")
        val URL3 = user1.get("URL3")
        val URL4 = user1.get("URL4")
        val URL5 = user1.get("URL5")
        if (URL1 == null)
        {
            fetchArtikel()
        }
        else{
            val adapter: GroupAdapter<*> = GroupAdapter<ViewHolder>()
            adapter.add(ArticleItem(context!!, AvailArticle(TITLE1, "", PREVIEW1, TIME1, URL1)))
            adapter.add(ArticleItem(context!!, AvailArticle(TITLE2, "", PREVIEW2, TIME2, URL2)))
            adapter.add(ArticleItem(context!!, AvailArticle(TITLE3, "", PREVIEW3, TIME3, URL3)))
            adapter.add(ArticleItem(context!!, AvailArticle(TITLE4, "", PREVIEW4, TIME4, URL4)))
            adapter.add(ArticleItem(context!!, AvailArticle(TITLE5, "", PREVIEW5, TIME5, URL5)))
            val recyler = root.findViewById<RecyclerView>(R.id.recyclerview_artikel_fragment)
            recyler.adapter = adapter
        }
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

                        val arrayIndex1 = array.getJSONObject(0)
                        val arrayIndex2 = array.getJSONObject(1)
                        val arrayIndex3 = array.getJSONObject(2)
                        val arrayIndex4 = array.getJSONObject(3)
                        val arrayIndex5 = array.getJSONObject(4)

                        var titleArray = arrayOf(
                                arrayIndex1.getString("title"),
                                arrayIndex2.getString("title"),
                                arrayIndex3.getString("title"),
                                arrayIndex4.getString("title"),
                                arrayIndex5.getString("title")
                        )

                        var previewArray = arrayOf(
                                arrayIndex1.getString("preview"),
                                arrayIndex2.getString("preview"),
                                arrayIndex3.getString("preview"),
                                arrayIndex4.getString("preview"),
                                arrayIndex5.getString("preview")
                        )

                        var timeArray = arrayOf(
                                arrayIndex1.getString("time"),
                                arrayIndex2.getString("time"),
                                arrayIndex3.getString("time"),
                                arrayIndex4.getString("time"),
                                arrayIndex5.getString("time")
                        )

                        var urlArray = arrayOf(
                                arrayIndex1.getString("url"),
                                arrayIndex2.getString("url"),
                                arrayIndex3.getString("url"),
                                arrayIndex4.getString("url"),
                                arrayIndex5.getString("url")
                        )

                        var artikelSession: ArtikelSession? = null
                        artikelSession = ArtikelSession(context!!)
                        artikelSession.createSession(titleArray[0], titleArray[1], titleArray[2], titleArray[3], titleArray[4], previewArray[0], previewArray[1], previewArray[2], previewArray[3], previewArray[4], timeArray[0], timeArray[1], timeArray[2], timeArray[3], timeArray[4], urlArray[0], urlArray[1], urlArray[2], urlArray[3], urlArray[4])

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