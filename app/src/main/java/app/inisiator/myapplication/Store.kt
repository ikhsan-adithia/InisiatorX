package app.inisiator.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.inisiator.myapplication.models.AvailableTicket
import app.inisiator.myapplication.models.Store
import app.inisiator.myapplication.tab_peluang.TicketItem
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.activity_store.view.*
import kotlinx.android.synthetic.main.avail_article_row.view.*
import kotlinx.android.synthetic.main.fragment_kegiatan.*
import kotlinx.android.synthetic.main.fragment_transaksi_tab.*
import kotlinx.android.synthetic.main.store.*
import kotlinx.android.synthetic.main.store.view.*
import kotlinx.android.synthetic.main.store.view.product
import kotlinx.android.synthetic.main.ticket_event.view.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap
import kotlin.reflect.typeOf

var cabe_merah = 0 ; var cabe_rawit_merah = 0 ; var cabe_rawit = 0 ; var cabe_hijau = 0 ; var bawang_merah = 0
var bawang_putih = 0 ; var kol = 0 ; var wortel = 0 ; var kentang = 0 ; var tomat = 0 ; var labu_jipang_kecil = 0
var labu_jipang_besar = 0 ; var kacang_panjang = 0 ; var buncis = 0 ; var brokoli = 0 ; var bunga_kol = 0
var janten = 0 ; var melinjo = 0 ; var kangkung = 0 ; var bayam = 0 ; var sawi_pahit = 0 ; var sawi_manis = 0
var sawi_putih = 0 ; var daun_sop = 0 ; var daun_pre = 0 ; var tauge_petik = 0 ; var tauge = 0 ; var gambas = 0
var timun = 0 ; var terong_ungu = 0 ; var terong_jempol = 0 ; var terong_kare = 0 ; var terong_telunjuk = 0
var ayam_potong = 0 ; var ikan_lele = 0 ; var ikan_nila = 0 ; var ikan_gurami = 0 ; var tahu_sumedang = 0
var tempe_daun = 0 ;var udang_kecepe = 0 ;var teri_belah = 0 ;var teri_nasi = 0 ;var ikan_dencis = 0
var ikan_gembung_aso = 0 ;var ikan_gembung_kuring = 0 ;var ikan_tongkol_sisik = 0 ;var gula_pasir = 0
var gula_merah = 0 ;var garam_halus = 0 ;var kacang_tanah = 0 ;var ifu_mie = 0 ;var bihun_jagung = 0
var mi_lidi = 0 ;var tepung_roti_segitiga_biru = 0 ;var sosis_okey = 0 ;var nugget_okkey = 0 ;var beras_ir_64_10_Kg = 0
var beras_ir_64_5_kg = 0 ;var minyak_goreng = 0 ;var jahe = 0 ;var lengkuas = 0 ;var kunyit = 0 ;var kencur = 0
var sere = 0 ;var daun_salam = 0 ;var daun_kunyit = 0 ;var kemiri = 0 ;var merica = 0 ;var ketumbar = 0
var pala = 0 ;var cengkeh = 0 ;var bunga_lawang = 0 ;var kapulaga = 0 ;var kayu_manis = 0 ;var asam_jawa = 0
var asam_potong = 0 ;var asam_sunti = 0 ;var asam_kandis = 0 ;var andaliman = 0 ;var asam_cikala = 0
var kincong = 0 ;var rimbang = 0; var total_bayar = 0;

class Store : AppCompatActivity() {

    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onBackPressed() {
        cabe_merah = 0;cabe_rawit_merah = 0;cabe_rawit = 0;cabe_hijau = 0;bawang_merah = 0;bawang_putih = 0
        kol = 0;wortel = 0;kentang = 0;tomat = 0;labu_jipang_kecil = 0;labu_jipang_besar = 0;kacang_panjang = 0;buncis = 0
        brokoli = 0;bunga_kol = 0;janten = 0;melinjo = 0;kangkung = 0;bayam = 0;sawi_pahit = 0;sawi_manis = 0;sawi_putih = 0
        daun_sop = 0;daun_pre = 0;tauge_petik = 0;tauge = 0;gambas = 0;timun = 0;terong_ungu = 0;terong_jempol = 0
        terong_kare = 0;terong_telunjuk = 0;ayam_potong = 0;ikan_lele = 0;ikan_nila = 0;ikan_gurami = 0;tahu_sumedang = 0
        tempe_daun = 0;udang_kecepe = 0;teri_belah = 0;teri_nasi = 0;ikan_dencis = 0;ikan_gembung_aso = 0;ikan_gembung_kuring = 0
        ikan_tongkol_sisik = 0;gula_pasir = 0;gula_merah = 0;garam_halus = 0;kacang_tanah = 0;ifu_mie = 0;bihun_jagung = 0;mi_lidi = 0
        tepung_roti_segitiga_biru = 0;sosis_okey = 0;nugget_okkey = 0;beras_ir_64_10_Kg = 0;beras_ir_64_5_kg = 0;minyak_goreng = 0;jahe = 0
        lengkuas = 0;kunyit = 0;kencur = 0;sere = 0;daun_salam = 0;daun_kunyit = 0;kemiri = 0;merica = 0;ketumbar = 0;pala = 0
        cengkeh = 0;bunga_lawang = 0;kapulaga = 0;kayu_manis = 0;asam_jawa = 0;asam_potong = 0;asam_sunti = 0;asam_kandis = 0;
        andaliman = 0;asam_cikala = 0;kincong = 0;rimbang = 0
        super.onBackPressed()
    }

    public fun reset(){
        cabe_merah = 0;cabe_rawit_merah = 0;cabe_rawit = 0;cabe_hijau = 0;bawang_merah = 0;bawang_putih = 0
        kol = 0;wortel = 0;kentang = 0;tomat = 0;labu_jipang_kecil = 0;labu_jipang_besar = 0;kacang_panjang = 0;buncis = 0
        brokoli = 0;bunga_kol = 0;janten = 0;melinjo = 0;kangkung = 0;bayam = 0;sawi_pahit = 0;sawi_manis = 0;sawi_putih = 0
        daun_sop = 0;daun_pre = 0;tauge_petik = 0;tauge = 0;gambas = 0;timun = 0;terong_ungu = 0;terong_jempol = 0
        terong_kare = 0;terong_telunjuk = 0;ayam_potong = 0;ikan_lele = 0;ikan_nila = 0;ikan_gurami = 0;tahu_sumedang = 0
        tempe_daun = 0;udang_kecepe = 0;teri_belah = 0;teri_nasi = 0;ikan_dencis = 0;ikan_gembung_aso = 0;ikan_gembung_kuring = 0
        ikan_tongkol_sisik = 0;gula_pasir = 0;gula_merah = 0;garam_halus = 0;kacang_tanah = 0;ifu_mie = 0;bihun_jagung = 0;mi_lidi = 0
        tepung_roti_segitiga_biru = 0;sosis_okey = 0;nugget_okkey = 0;beras_ir_64_10_Kg = 0;beras_ir_64_5_kg = 0;minyak_goreng = 0;jahe = 0
        lengkuas = 0;kunyit = 0;kencur = 0;sere = 0;daun_salam = 0;daun_kunyit = 0;kemiri = 0;merica = 0;ketumbar = 0;pala = 0
        cengkeh = 0;bunga_lawang = 0;kapulaga = 0;kayu_manis = 0;asam_jawa = 0;asam_potong = 0;asam_sunti = 0;asam_kandis = 0;
        andaliman = 0;asam_cikala = 0;kincong = 0;rimbang = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val shimmer : ShimmerFrameLayout
        shimmer = findViewById(R.id.shimmer2)
        shimmer.startShimmer()
        fetchdata()

        recyclerview_store.setItemViewCacheSize(100)

        val button = findViewById<Button>(R.id.checkout)
        button.setOnClickListener {
            val intent = Intent(this, checkoutnow::class.java)
            intent.putExtra("cabe_merah", cabe_merah)
            intent.putExtra("cabe_rawit_merah", cabe_rawit_merah)
            intent.putExtra("cabe_rawit", cabe_rawit)
            intent.putExtra("cabe_hijau", cabe_hijau)
            intent.putExtra("bawang_merah", bawang_merah)
            intent.putExtra("bawang_putih", bawang_putih)
            intent.putExtra("kol", kol)
            intent.putExtra("wortel", wortel)
            intent.putExtra("kentang", kentang)
            intent.putExtra("tomat", tomat)
            intent.putExtra("labu_jipang_kecil", labu_jipang_kecil)
            intent.putExtra("labu_jipang_besar", labu_jipang_besar)
            intent.putExtra("kacang_panjang", kacang_panjang)
            intent.putExtra("buncis", buncis)
            intent.putExtra("brokoli", brokoli)
            intent.putExtra("bunga_kol", bunga_kol)
            intent.putExtra("janten", janten)
            intent.putExtra("melinjo", melinjo)
            intent.putExtra("kangkung", kangkung)
            intent.putExtra("bayam", bayam)
            intent.putExtra("sawi_pahit", sawi_pahit)
            intent.putExtra("sawi_manis", sawi_manis)
            intent.putExtra("sawi_putih", sawi_putih)
            intent.putExtra("daun_sop", daun_sop)
            intent.putExtra("daun_pre", daun_pre)
            intent.putExtra("tauge_petik", tauge_petik)
            intent.putExtra("tauge", tauge)
            intent.putExtra("gambas", gambas)
            intent.putExtra("timun", timun)
            intent.putExtra("terong_ungu", terong_ungu)
            intent.putExtra("terong_jempol", terong_jempol)
            intent.putExtra("terong_kare", terong_kare)
            intent.putExtra("terong_telunjuk", terong_telunjuk)
            intent.putExtra("ayam_potong", ayam_potong)
            intent.putExtra("ikan_lele", ikan_lele)
            intent.putExtra("ikan_nila", ikan_nila)
            intent.putExtra("ikan_gurami", ikan_gurami)
            intent.putExtra("tahu_sumedang", tahu_sumedang)
            intent.putExtra("tempe_daun", tempe_daun)
            intent.putExtra("udang_kecepe", udang_kecepe)
            intent.putExtra("teri_belah", teri_belah)
            intent.putExtra("teri_nasi", teri_nasi)
            intent.putExtra("ikan_dencis", ikan_dencis)
            intent.putExtra("ikan_gembung_aso", ikan_gembung_aso)
            intent.putExtra("ikan_gembung_kuring", ikan_gembung_kuring)
            intent.putExtra("ikan_tongkol_sisik", ikan_tongkol_sisik)
            intent.putExtra("gula_pasir", gula_pasir)
            intent.putExtra("gula_merah", gula_merah)
            intent.putExtra("garam_halus", garam_halus)
            intent.putExtra("kacang_tanah", kacang_tanah)
            intent.putExtra("ifu_mie", ifu_mie)
            intent.putExtra("bihun_jagung", bihun_jagung)
            intent.putExtra("mi_lidi", mi_lidi)
            intent.putExtra("tepung_roti_segitiga_biru", tepung_roti_segitiga_biru)
            intent.putExtra("sosis_okey", sosis_okey)
            intent.putExtra("nugget_okkey", nugget_okkey)
            intent.putExtra("beras_ir_64_10_Kg", beras_ir_64_10_Kg)
            intent.putExtra("beras_ir_64_5_kg", beras_ir_64_5_kg)
            intent.putExtra("minyak_goreng", minyak_goreng)
            intent.putExtra("jahe", jahe)
            intent.putExtra("lengkuas", lengkuas)
            intent.putExtra("kunyit", kunyit)
            intent.putExtra("kencur", kencur)
            intent.putExtra("sere", sere)
            intent.putExtra("daun_salam", daun_salam)
            intent.putExtra("daun_kunyit", daun_kunyit)
            intent.putExtra("kemiri", kemiri)
            intent.putExtra("merica", merica)
            intent.putExtra("ketumbar", ketumbar)
            intent.putExtra("pala", pala)
            intent.putExtra("cengkeh", cengkeh)
            intent.putExtra("bunga_lawang", bunga_lawang)
            intent.putExtra("kapulaga", kapulaga)
            intent.putExtra("kayu_manis", kayu_manis)
            intent.putExtra("asam_jawa", asam_jawa)
            intent.putExtra("asam_potong", asam_potong)
            intent.putExtra("asam_sunti", asam_sunti)
            intent.putExtra("asam_kandis", asam_kandis)
            intent.putExtra("andaliman", andaliman)
            intent.putExtra("asam_cikala", asam_cikala)
            intent.putExtra("kincong", kincong)
            intent.putExtra("rimbang", rimbang)
            intent.putExtra("total", total_bayar)
            startActivity(intent)
        }
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
                        val menu = findViewById<LinearLayout>(R.id.menu)
                        menu.visibility = View.VISIBLE
                        main.visibility = View.VISIBLE
                        val shimer = findViewById<RelativeLayout>(R.id.shimmer)
                        shimer.visibility = View.GONE
                        val trueshimmer = findViewById<ShimmerFrameLayout>(R.id.shimmer2)
                        trueshimmer.stopShimmer()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        val bottomSheetDialog = BottomSheetDialog(
                               this, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(this)
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

                }, Response.ErrorListener {
                        val bottomSheetDialog = BottomSheetDialog(
                                this, R.style.BottomSheetDialogTheme
                        )
                        val bottomSheetView = LayoutInflater.from(this)
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
            total_bayar = total_bayar + item.harga
            if (position == 0) { cabe_merah = cabe_merah + 1 }
            else if (position == 1){ cabe_rawit_merah = cabe_rawit_merah + 1 }
            else if (position == 2){ cabe_rawit = cabe_rawit + 1 }
            else if (position == 3){ cabe_hijau = cabe_hijau + 1 }
            else if (position == 4){ bawang_merah = bawang_merah + 1 }
            else if (position == 5){ bawang_putih = bawang_putih + 1 }
            else if (position == 6){ kol = kol + 1 }
            else if (position == 7){ wortel = wortel + 1 }
            else if (position == 8){ kentang = kentang + 1 }
            else if (position == 9){ tomat = tomat + 1 }
            else if (position == 10){ labu_jipang_kecil = labu_jipang_kecil + 1 }
            else if (position == 11){ labu_jipang_besar = labu_jipang_besar + 1 }
            else if (position == 12){ kacang_panjang = kacang_panjang + 1 }
            else if (position == 13){ buncis = buncis + 1 }
            else if (position == 14){ brokoli = brokoli + 1 }
            else if (position == 15){ bunga_kol = bunga_kol + 1 }
            else if (position == 16){ janten = janten + 1 }
            else if (position == 17){ melinjo = melinjo + 1 }
            else if (position == 18){ kangkung = kangkung + 1 }
            else if (position == 19){ bayam = bayam + 1 }
            else if (position == 20){ sawi_pahit = sawi_pahit + 1 }
            else if (position == 21){ sawi_manis = sawi_manis + 1 }
            else if (position == 22){ sawi_putih = sawi_putih + 1 }
            else if (position == 23){ daun_sop = daun_sop + 1 }
            else if (position == 24){ daun_pre = daun_pre + 1 }
            else if (position == 25){ tauge_petik = tauge_petik + 1 }
            else if (position == 25){ tauge = tauge + 1 }
            else if (position == 27){ gambas = gambas + 1 }
            else if (position == 28){ timun = timun + 1 }
            else if (position == 29){ terong_ungu = terong_ungu + 1 }
            else if (position == 30){ terong_jempol = terong_jempol + 1 }
            else if (position == 31){ terong_kare = terong_kare + 1 }
            else if (position == 32){ terong_telunjuk = terong_telunjuk + 1 }
            else if (position == 33){ ayam_potong = ayam_potong + 1 }
            else if (position == 34){ ikan_lele = ikan_lele + 1 }
            else if (position == 35){ ikan_nila = ikan_nila + 1 }
            else if (position == 36){ ikan_gurami = ikan_gurami + 1 }
            else if (position == 37){ tahu_sumedang = tahu_sumedang + 1 }
            else if (position == 38){ tempe_daun = tempe_daun + 1 }
            else if (position == 39){ udang_kecepe = udang_kecepe + 1 }
            else if (position == 40){ teri_belah = teri_belah + 1 }
            else if (position == 41){ teri_nasi = teri_nasi + 1 }
            else if (position == 42){ ikan_dencis = ikan_dencis + 1 }
            else if (position == 43){ ikan_gembung_aso = ikan_gembung_aso + 1 }
            else if (position == 44){ ikan_gembung_kuring = ikan_gembung_kuring + 1 }
            else if (position == 45){ ikan_tongkol_sisik = ikan_tongkol_sisik + 1 }
            else if (position == 46){ gula_pasir = gula_pasir + 1 }
            else if (position == 47){ gula_merah = gula_merah + 1 }
            else if (position == 48){ garam_halus = garam_halus + 1 }
            else if (position == 49){ kacang_tanah = kacang_tanah + 1 }
            else if (position == 50){ ifu_mie = ifu_mie + 1 }
            else if (position == 51){ bihun_jagung = bihun_jagung + 1 }
            else if (position == 52){ mi_lidi = mi_lidi + 1 }
            else if (position == 53){ tepung_roti_segitiga_biru = tepung_roti_segitiga_biru + 1 }
            else if (position == 54){ sosis_okey = sosis_okey + 1 }
            else if (position == 55){ nugget_okkey = nugget_okkey + 1 }
            else if (position == 56){ beras_ir_64_10_Kg = beras_ir_64_10_Kg + 1 }
            else if (position == 57){ beras_ir_64_5_kg = beras_ir_64_5_kg + 1 }
            else if (position == 58){ minyak_goreng = minyak_goreng + 1 }
            else if (position == 59){ jahe = jahe + 1 }
            else if (position == 60){ lengkuas = lengkuas + 1 }
            else if (position == 61){ kunyit = kunyit + 1 }
            else if (position == 62){ kencur = kencur + 1 }
            else if (position == 63){ sere = sere + 1 }
            else if (position == 64){ daun_salam = daun_salam + 1 }
            else if (position == 65){ daun_kunyit = daun_kunyit + 1 }
            else if (position == 66){ kemiri = kemiri + 1 }
            else if (position == 67){ merica = merica + 1 }
            else if (position == 68){ ketumbar = ketumbar + 1 }
            else if (position == 69){ pala = pala + 1 }
            else if (position == 70){ cengkeh = cengkeh + 1 }
            else if (position == 71){ bunga_lawang = bunga_lawang + 1 }
            else if (position == 72){ kapulaga = kapulaga + 1 }
            else if (position == 73){ kayu_manis = kayu_manis + 1 }
            else if (position == 74){ asam_jawa = asam_jawa + 1 }
            else if (position == 75){ asam_potong = asam_potong + 1 }
            else if (position == 76){ asam_sunti = asam_sunti + 1 }
            else if (position == 77){ asam_kandis = asam_kandis + 1 }
            else if (position == 78){ andaliman = andaliman + 1 }
            else if (position == 79){ asam_cikala = asam_cikala + 1 }
            else if (position == 80){ kincong = kincong + 1 }
            else if (position == 81){ rimbang = rimbang + 1 }
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
                total_bayar = total_bayar - item.harga
                if (position == 0){ cabe_merah = cabe_merah - 1 }
                else if (position == 1){ cabe_rawit_merah = cabe_rawit_merah - 1 }
                else if (position == 2){ cabe_rawit = cabe_rawit - 1 }
                else if (position == 3){ cabe_hijau = cabe_hijau - 1 }
                else if (position == 4){ bawang_merah = bawang_merah - 1 }
                else if (position == 5){ bawang_putih = bawang_putih - 1 }
                else if (position == 6){ kol = kol - 1 }
                else if (position == 7){ wortel = wortel - 1 }
                else if (position == 8){ kentang = kentang - 1 }
                else if (position == 9){ tomat = tomat - 1 }
                else if (position == 10){ labu_jipang_kecil = labu_jipang_kecil - 1 }
                else if (position == 11){ labu_jipang_besar = labu_jipang_besar - 1 }
                else if (position == 12){ kacang_panjang = kacang_panjang - 1 }
                else if (position == 13){ buncis = buncis - 1 }
                else if (position == 14){ brokoli = brokoli - 1 }
                else if (position == 15){ bunga_kol = bunga_kol - 1 }
                else if (position == 16){ janten = janten - 1 }
                else if (position == 17){ melinjo = melinjo - 1 }
                else if (position == 18){ kangkung = kangkung - 1 }
                else if (position == 19){ bayam = bayam - 1 }
                else if (position == 20){ sawi_pahit = sawi_pahit - 1 }
                else if (position == 21){ sawi_manis = sawi_manis - 1 }
                else if (position == 22){ sawi_putih = sawi_putih - 1 }
                else if (position == 23){ daun_sop = daun_sop - 1 }
                else if (position == 24){ daun_pre = daun_pre - 1 }
                else if (position == 25){ tauge_petik = tauge_petik - 1 }
                else if (position == 25){ tauge = tauge - 1 }
                else if (position == 27){ gambas = gambas - 1 }
                else if (position == 28){ timun = timun - 1 }
                else if (position == 29){ terong_ungu = terong_ungu - 1 }
                else if (position == 30){ terong_jempol = terong_jempol - 1 }
                else if (position == 31){ terong_kare = terong_kare - 1 }
                else if (position == 32){ terong_telunjuk = terong_telunjuk - 1 }
                else if (position == 33){ ayam_potong = ayam_potong - 1 }
                else if (position == 34){ ikan_lele = ikan_lele - 1 }
                else if (position == 35){ ikan_nila = ikan_nila - 1 }
                else if (position == 36){ ikan_gurami = ikan_gurami - 1 }
                else if (position == 37){ tahu_sumedang = tahu_sumedang - 1 }
                else if (position == 38){ tempe_daun = tempe_daun - 1 }
                else if (position == 39){ udang_kecepe = udang_kecepe - 1 }
                else if (position == 40){ teri_belah = teri_belah - 1 }
                else if (position == 41){ teri_nasi = teri_nasi - 1 }
                else if (position == 42){ ikan_dencis = ikan_dencis - 1 }
                else if (position == 43){ ikan_gembung_aso = ikan_gembung_aso - 1 }
                else if (position == 44){ ikan_gembung_kuring = ikan_gembung_kuring - 1 }
                else if (position == 45){ ikan_tongkol_sisik = ikan_tongkol_sisik - 1 }
                else if (position == 46){ gula_pasir = gula_pasir - 1 }
                else if (position == 47){ gula_merah = gula_merah - 1 }
                else if (position == 48){ garam_halus = garam_halus - 1 }
                else if (position == 49){ kacang_tanah = kacang_tanah - 1 }
                else if (position == 50){ ifu_mie = ifu_mie - 1 }
                else if (position == 51){ bihun_jagung = bihun_jagung - 1 }
                else if (position == 52){ mi_lidi = mi_lidi - 1 }
                else if (position == 53){ tepung_roti_segitiga_biru = tepung_roti_segitiga_biru - 1 }
                else if (position == 54){ sosis_okey = sosis_okey - 1 }
                else if (position == 55){ nugget_okkey = nugget_okkey - 1 }
                else if (position == 56){ beras_ir_64_10_Kg = beras_ir_64_10_Kg - 1 }
                else if (position == 57){ beras_ir_64_5_kg = beras_ir_64_5_kg - 1 }
                else if (position == 58){ minyak_goreng = minyak_goreng - 1 }
                else if (position == 59){ jahe = jahe - 1 }
                else if (position == 60){ lengkuas = lengkuas - 1 }
                else if (position == 61){ kunyit = kunyit - 1 }
                else if (position == 62){ kencur = kencur - 1 }
                else if (position == 63){ sere = sere - 1 }
                else if (position == 64){ daun_salam = daun_salam - 1 }
                else if (position == 65){ daun_kunyit = daun_kunyit - 1 }
                else if (position == 66){ kemiri = kemiri - 1 }
                else if (position == 67){ merica = merica - 1 }
                else if (position == 68){ ketumbar = ketumbar - 1 }
                else if (position == 69){ pala = pala - 1 }
                else if (position == 70){ cengkeh = cengkeh - 1 }
                else if (position == 71){ bunga_lawang = bunga_lawang - 1 }
                else if (position == 72){ kapulaga = kapulaga - 1 }
                else if (position == 73){ kayu_manis = kayu_manis - 1 }
                else if (position == 74){ asam_jawa = asam_jawa - 1 }
                else if (position == 75){ asam_potong = asam_potong - 1 }
                else if (position == 76){ asam_sunti = asam_sunti - 1 }
                else if (position == 77){ asam_kandis = asam_kandis - 1 }
                else if (position == 78){ andaliman = andaliman - 1 }
                else if (position == 79){ asam_cikala = asam_cikala - 1 }
                else if (position == 80){ kincong = kincong - 1 }
                else if (position == 81){ rimbang = rimbang - 1 }
            }
        }
    }
}
