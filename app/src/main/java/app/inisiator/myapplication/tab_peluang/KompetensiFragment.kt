package app.inisiator.myapplication.tab_peluang

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.inisiator.myapplication.DetailKompetisi
import app.inisiator.myapplication.R
import app.inisiator.myapplication.models.AvailableKompetisi
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_kompetensi.*
import kotlinx.android.synthetic.main.row_kompetisi.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.concurrent.thread

class KompetensiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_kompetensi, container, false)

        Thread(Runnable {
            activity!!.runOnUiThread {
                fetchKompetisi()
            }
        }).start()

        return root
    }

    private fun fetchKompetisi() {
        val url = "https://awalspace.com/app/imbalopunyajangandiganggu/availkompetisi.php"
        val strRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val adapter = GroupAdapter<ViewHolder>()

                    try {
                        val array = JSONArray(response)

                        for (i in 0 until array.length()) {
                            val obj = array.getJSONObject(i)

                            val getNamaKomp = obj.getString("nama_komp")
                            val getPenyelenggara = obj.getString("penyelenggara")
                            val getBatasWaktu = obj.getString("batas_waktu")
                            val getJenis = obj.getString("jenis")
                            val getLokasi = obj.getString("lokasi")
                            val getKeterangan = obj.getString("keterangan")
                            val getImgUrl = obj.getString("img_url")

                            adapter.add(
                                    AvailKompetisi(
                                            AvailableKompetisi(
                                                    getNamaKomp,
                                                    getPenyelenggara,
                                                    getBatasWaktu,
                                                    getJenis,
                                                    getLokasi,
                                                    getKeterangan,
                                                    getImgUrl
                                            ), context!!
                                    )
                            )

                        }
                        rv_kompetisi.adapter = adapter
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

class AvailKompetisi(private val komp: AvailableKompetisi, val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_kompetisi
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.kompetisi_title.text = komp.nama_komp
        viewHolder.itemView.kompetisi_penyelenggara.text = komp.penyelenggara
        viewHolder.itemView.kompetisi_batassub.text = "Batas waktu registrasi : ${komp.batas_waktu}"

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, DetailKompetisi::class.java)
            intent.putExtra("NAMA_KOMP", komp.nama_komp)
            intent.putExtra("PENYELENGGARA", komp.penyelenggara)
            intent.putExtra("BATAS_WAKTU", komp.batas_waktu)
            intent.putExtra("JENIS_KOMP", komp.jenis)
            intent.putExtra("LOKASI_KOMP", komp.lokasi)
            intent.putExtra("KETERANGAN", komp.keterangan)
            intent.putExtra("IMG_KOMP", komp.img_url)
            context.startActivity(intent)
        }
    }
}
