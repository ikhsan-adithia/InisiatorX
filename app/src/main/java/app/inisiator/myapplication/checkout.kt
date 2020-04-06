package app.inisiator.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import app.inisiator.myapplication.models.Checkout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.goodiebag.pinview.Pinview
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.checkout.view.*
import org.json.JSONException
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class checkoutnow : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        checkout_re.setItemViewCacheSize(100)

        val cabe_merah = intent.getIntExtra("cabe_merah", 0)
        val cabe_rawit_merah = intent.getIntExtra("cabe_rawit_merah",0)
        val cabe_rawit = intent.getIntExtra("cabe_rawit",0)
        val cabe_hijau= intent.getIntExtra("cabe_hijau",0)
        val bawang_merah = intent.getIntExtra("bawang_merah",0)
        val bawang_putih = intent.getIntExtra("bawang_putih",0)
        val kol = intent.getIntExtra("kol",0)
        val wortel = intent.getIntExtra("wortel",0)
        val kentang = intent.getIntExtra("kentang",0)
        val tomat = intent.getIntExtra("tomat",0)
        val labu_jipang_kecil = intent.getIntExtra("labu_jipang_kecil",0)
        val labu_jipang_besar = intent.getIntExtra("labu_jipang_besar",0)
        val kacang_panjang = intent.getIntExtra("kacang_panjang",0)
        val buncis = intent.getIntExtra("buncis",0)
        val brokoli = intent.getIntExtra("brokoli",0)
        val bunga_kol = intent.getIntExtra("bunga_kol",0)
        val janten = intent.getIntExtra("janten",0)
        val melinjo = intent.getIntExtra("melinjo",0)
        val kangkung = intent.getIntExtra("kangkung",0)
        val bayam = intent.getIntExtra("bayam",0)
        val sawi_pahit = intent.getIntExtra("sawi_pahit",0)
        val sawi_manis = intent.getIntExtra("sawi_manis",0)
        val sawi_putih = intent.getIntExtra("sawi_putih",0)
        val daun_sop = intent.getIntExtra("daun_sop",0)
        val daun_pre = intent.getIntExtra("daun_pre",0)
        val tauge_petik = intent.getIntExtra("tauge_petik",0)
        val tauge = intent.getIntExtra("tauge",0)
        val gambas = intent.getIntExtra("gambas",0)
        val timun = intent.getIntExtra("timun",0)
        val terong_ungu = intent.getIntExtra("terong_ungu",0)
        val terong_jempol = intent.getIntExtra("terong_jempol",0)
        val terong_kare = intent.getIntExtra("terong_kare",0)
        val terong_telunjuk = intent.getIntExtra("terong_telunjuk",0)
        val ayam_potong = intent.getIntExtra("ayam_potong",0)
        val ikan_lele = intent.getIntExtra("ikan_lele",0)
        val ikan_nila = intent.getIntExtra("ikan_nila",0)
        val ikan_gurami = intent.getIntExtra("ikan_gurami",0)
        val tahu_sumedang = intent.getIntExtra("tahu_sumedang",0)
        val tempe_daun = intent.getIntExtra("tempe_daun",0)
        val udang_kecepe = intent.getIntExtra("udang_kecepe",0)
        val teri_belah = intent.getIntExtra("teri_belah",0)
        val teri_nasi = intent.getIntExtra("teri_nasi",0)
        val ikan_dencis = intent.getIntExtra("ikan_dencis",0)
        val ikan_gembung_aso = intent.getIntExtra("ikan_gembung_aso",0)
        val ikan_gembung_kuring = intent.getIntExtra("ikan_gembung_kuring",0)
        val ikan_tongkol_sisik = intent.getIntExtra("ikan_tongkol_sisik",0)
        val gula_pasir = intent.getIntExtra("gula_pasir",0)
        val gula_merah = intent.getIntExtra("gula_merah",0)
        val garam_halus = intent.getIntExtra("garam_halus",0)
        val kacang_tanah = intent.getIntExtra("kacang_tanah",0)
        val ifu_mie = intent.getIntExtra("ifu_mie",0)
        val bihun_jagung = intent.getIntExtra("bihun_jagung",0)
        val mi_lidi = intent.getIntExtra("mi_lidi",0)
        val tepung_roti_segitiga_biru = intent.getIntExtra("tepung_roti_segitiga_biru",0)
        val sosis_okey = intent.getIntExtra("sosis_okey",0)
        val nugget_okkey = intent.getIntExtra("nugget_okkey",0)
        val beras_ir_64_10_Kg = intent.getIntExtra("beras_ir_64_10_Kg",0)
        val beras_ir_64_5_kg = intent.getIntExtra("beras_ir_64_5_kg",0)
        val minyak_goreng = intent.getIntExtra("minyak_goreng",0)
        val jahe = intent.getIntExtra("jahe",0)
        val lengkuas = intent.getIntExtra("lengkuas",0)
        val kunyit = intent.getIntExtra("kunyit",0)
        val kencur = intent.getIntExtra("kencur",0)
        val sere = intent.getIntExtra("sere",0)
        val daun_salam = intent.getIntExtra("daun_salam",0)
        val daun_kunyit = intent.getIntExtra("daun_kunyit",0)
        val kemiri = intent.getIntExtra("kemiri",0)
        val merica = intent.getIntExtra("merica",0)
        val ketumbar = intent.getIntExtra("ketumbar",0)
        val pala = intent.getIntExtra("pala",0)
        val cengkeh = intent.getIntExtra("cengkeh",0)
        val bunga_lawang = intent.getIntExtra("bunga_lawang",0)
        val kapulaga = intent.getIntExtra("kapulaga",0)
        val kayu_manis = intent.getIntExtra("kayu_manis",0)
        val asam_jawa = intent.getIntExtra("asam_jawa",0)
        val asam_potong = intent.getIntExtra("asam_potong",0)
        val asam_sunti = intent.getIntExtra("asam_sunti",0)
        val asam_kandis = intent.getIntExtra("asam_kandis",0)
        val andaliman = intent.getIntExtra("andaliman",0)
        val asam_cikala = intent.getIntExtra("asam_cikala",0)
        val kincong = intent.getIntExtra("kincong",0)
        val rimbang = intent.getIntExtra("rimbang",0)
        val total = intent.getIntExtra("total",0)
        val adapter = GroupAdapter<ViewHolder>()
        if (cabe_merah == 0) { } else {adapter.add(Checkout(Checkout("Cabe Merah", cabe_merah), this)) }
        if (cabe_rawit_merah == 0){ } else {adapter.add(Checkout(Checkout("Cabe Rawit Merah", cabe_rawit_merah.toInt()), this)) }
        if (cabe_rawit == 0){ } else {adapter.add(Checkout(Checkout("Cabe Rawit", cabe_rawit), this)) }
        if (cabe_hijau == 0){ } else {adapter.add(Checkout(Checkout("Cabe Hijau", cabe_hijau), this)) }
        if (bawang_merah == 0){ } else {adapter.add(Checkout(Checkout("Bawang Merah", bawang_merah), this)) }
        if (bawang_putih == 0){ } else {adapter.add(Checkout(Checkout("Bawang Putih", bawang_putih), this)) }
        if (kol == 0){ } else {adapter.add(Checkout(Checkout("Kol", kol), this)) }
        if (wortel == 0){ } else {adapter.add(Checkout(Checkout("Wortel", wortel), this)) }
        if (kentang == 0){ } else {adapter.add(Checkout(Checkout("Kentang", kentang), this)) }
        if (tomat == 0){ } else {adapter.add(Checkout(Checkout("Tomat", tomat), this)) }
        if (labu_jipang_kecil == 0){ } else {adapter.add(Checkout(Checkout("Labu Jipang Kecil", labu_jipang_kecil), this)) }
        if (labu_jipang_besar == 0){ } else {adapter.add(Checkout(Checkout("Labu Jipang Besar", labu_jipang_besar), this)) }
        if (kacang_panjang == 0){ } else {adapter.add(Checkout(Checkout("Kacang Panjang", kacang_panjang), this)) }
        if (buncis == 0){ } else {adapter.add(Checkout(Checkout("Buncis", buncis), this)) }
        if (brokoli == 0){ } else {adapter.add(Checkout(Checkout("Brokoli", brokoli), this)) }
        if (bunga_kol == 0){ } else {adapter.add(Checkout(Checkout("Bunga Kol", bunga_kol), this)) }
        if (janten == 0){ } else {adapter.add(Checkout(Checkout("Janten", janten), this)) }
        if (melinjo == 0){ } else {adapter.add(Checkout(Checkout("Melinjo", melinjo), this)) }
        if (kangkung == 0){ } else {adapter.add(Checkout(Checkout("Kangkung", kangkung), this)) }
        if (bayam == 0){ } else {adapter.add(Checkout(Checkout("Bayam", bayam), this)) }
        if (sawi_pahit == 0){ } else {adapter.add(Checkout(Checkout("Sawi Pahit", sawi_pahit), this)) }
        if (sawi_manis == 0){ } else {adapter.add(Checkout(Checkout("Sawi Manis", sawi_manis), this)) }
        if (sawi_putih == 0){ } else {adapter.add(Checkout(Checkout("Sawi Putih", sawi_putih), this)) }
        if (daun_sop == 0){ } else {adapter.add(Checkout(Checkout("Daun Sop", daun_sop), this)) }
        if (daun_pre == 0){ } else {adapter.add(Checkout(Checkout("Daun Pre", daun_pre), this)) }
        if (tauge_petik == 0){ } else {adapter.add(Checkout(Checkout("Tauge Petik", tauge_petik), this)) }
        if (tauge == 0){ } else {adapter.add(Checkout(Checkout("Tauge", tauge), this)) }
        if (gambas == 0){ } else {adapter.add(Checkout(Checkout("Gambas", gambas), this)) }
        if (timun == 0){ } else {adapter.add(Checkout(Checkout("Timun", timun), this)) }
        if (terong_ungu == 0){ } else {adapter.add(Checkout(Checkout("Terong Ungu", terong_ungu), this)) }
        if (terong_jempol == 0){ } else {adapter.add(Checkout(Checkout("Terong Jempol", terong_jempol), this)) }
        if (terong_kare == 0){ } else {adapter.add(Checkout(Checkout("Terong Kare", terong_kare), this)) }
        if (terong_telunjuk == 0){ } else {adapter.add(Checkout(Checkout("Terong Telunjuk", terong_telunjuk), this)) }
        if (ayam_potong == 0){ } else {adapter.add(Checkout(Checkout("Ayam Potong", ayam_potong), this)) }
        if (ikan_lele == 0){ } else {adapter.add(Checkout(Checkout("Ikan Lele", ikan_lele), this)) }
        if (ikan_nila == 0){ } else {adapter.add(Checkout(Checkout("Ikan Nila", ikan_nila), this)) }
        if (ikan_gurami == 0){ } else {adapter.add(Checkout(Checkout("Ikan Gurami", ikan_gurami), this)) }
        if (tahu_sumedang == 0){ } else {adapter.add(Checkout(Checkout("Tahu Sumedang", tahu_sumedang), this)) }
        if (tempe_daun == 0){ } else {adapter.add(Checkout(Checkout("Tempe Daun", tempe_daun), this)) }
        if (udang_kecepe == 0){ } else {adapter.add(Checkout(Checkout("Udang Kecepe", udang_kecepe), this)) }
        if (teri_belah == 0){ } else {adapter.add(Checkout(Checkout("Teri Belah", teri_belah), this)) }
        if (teri_nasi == 0){ } else {adapter.add(Checkout(Checkout("Teri Nasi", teri_nasi), this)) }
        if (ikan_dencis == 0){ } else {adapter.add(Checkout(Checkout("Ikan Dencis", ikan_dencis), this)) }
        if (ikan_gembung_aso == 0){ } else {adapter.add(Checkout(Checkout("Ikan Gembung Aso", ikan_gembung_aso), this)) }
        if (ikan_gembung_kuring == 0){ } else {adapter.add(Checkout(Checkout("Ikan Gembung Kuring", ikan_gembung_kuring), this)) }
        if (ikan_tongkol_sisik == 0){ } else {adapter.add(Checkout(Checkout("Ikan Tongkol Sisik", ikan_tongkol_sisik), this)) }
        if (gula_pasir == 0){ } else {adapter.add(Checkout(Checkout("Gula Pasir", gula_pasir), this)) }
        if (gula_merah == 0){ } else {adapter.add(Checkout(Checkout("Gula Merah", gula_merah), this)) }
        if (garam_halus == 0){ } else {adapter.add(Checkout(Checkout("Garam Halus", garam_halus), this)) }
        if (kacang_tanah == 0){ } else {adapter.add(Checkout(Checkout("Kacang Tanah", kacang_tanah), this)) }
        if (ifu_mie == 0){ } else {adapter.add(Checkout(Checkout("Ifu Mie", ifu_mie), this)) }
        if (bihun_jagung == 0){ } else {adapter.add(Checkout(Checkout("Bihun Jagung", bihun_jagung), this)) }
        if (mi_lidi == 0){ } else {adapter.add(Checkout(Checkout("Mi Lidi", mi_lidi), this)) }
        if (tepung_roti_segitiga_biru == 0){ } else {adapter.add(Checkout(Checkout("Tepung Roti Segitiga Biru", tepung_roti_segitiga_biru), this)) }
        if (sosis_okey == 0){ } else {adapter.add(Checkout(Checkout("Sosis Okkey", sosis_okey), this)) }
        if (nugget_okkey == 0){ } else {adapter.add(Checkout(Checkout("Nugget Okkey", nugget_okkey), this)) }
        if (beras_ir_64_10_Kg == 0){ } else {adapter.add(Checkout(Checkout("Beras IR 64 @10 Kg", beras_ir_64_10_Kg), this)) }
        if (beras_ir_64_5_kg == 0){ } else {adapter.add(Checkout(Checkout("Beras IR 64 @5 Kg", beras_ir_64_5_kg), this)) }
        if (minyak_goreng == 0){ } else {adapter.add(Checkout(Checkout("Minyak Goreng @2 Liter", minyak_goreng), this)) }
        if (jahe == 0){ } else {adapter.add(Checkout(Checkout("Jahe", jahe), this)) }
        if (lengkuas == 0){ } else {adapter.add(Checkout(Checkout("Lengkuas", lengkuas), this)) }
        if (kunyit == 0){ } else {adapter.add(Checkout(Checkout("Kunyit", kunyit), this)) }
        if (kencur == 0){ } else {adapter.add(Checkout(Checkout("Kencur", kencur), this)) }
        if (sere == 0){ } else {adapter.add(Checkout(Checkout("Sere", sere), this)) }
        if (daun_salam == 0){ } else {adapter.add(Checkout(Checkout("Daun Salam", daun_salam), this)) }
        if (daun_kunyit == 0){ } else {adapter.add(Checkout(Checkout("Daun Kunyit", daun_kunyit), this)) }
        if (kemiri == 0){ } else {adapter.add(Checkout(Checkout("Kemiri", kemiri), this)) }
        if (merica == 0){ } else {adapter.add(Checkout(Checkout("Merica", merica), this)) }
        if (ketumbar == 0){ } else {adapter.add(Checkout(Checkout("Ketumbar", ketumbar), this)) }
        if (pala == 0){ } else {adapter.add(Checkout(Checkout("Pala", pala), this)) }
        if (cengkeh == 0){ } else {adapter.add(Checkout(Checkout("Cengkeh", cengkeh), this)) }
        if (bunga_lawang == 0){ } else {adapter.add(Checkout(Checkout("Bunga Lawang", bunga_lawang), this)) }
        if (kapulaga == 0){ } else {adapter.add(Checkout(Checkout("Kapulaga", kapulaga), this)) }
        if (kayu_manis == 0){ } else {adapter.add(Checkout(Checkout("Kayu Manis", kayu_manis), this)) }
        if (asam_jawa == 0){ } else {adapter.add(Checkout(Checkout("Asam Jawa", asam_jawa), this)) }
        if (asam_potong == 0){ } else {adapter.add(Checkout(Checkout("Asam Potong", asam_potong), this)) }
        if (asam_sunti == 0){ } else {adapter.add(Checkout(Checkout("Asam Sunti", asam_sunti), this)) }
        if (asam_kandis == 0){ } else {adapter.add(Checkout(Checkout("Asam Kandis", asam_kandis), this)) }
        if (andaliman == 0 ){} else {adapter.add(Checkout(Checkout("Andaliman", andaliman), this)) }
        if (asam_cikala == 0 ){} else {adapter.add(Checkout(Checkout("Asam Cikala", asam_cikala), this)) }
        if (kincong == 0){ } else {adapter.add(Checkout(Checkout("Kincong", kincong), this)) }
        if (rimbang == 0){ } else {adapter.add(Checkout(Checkout("Rimbang", rimbang), this)) }
        checkout_re.adapter = adapter

        val totall = findViewById<TextView>(R.id.total)
        val totalbayar = findViewById<TextView>(R.id.total_bayar)
        val withongkir = total+30
        val txtongkir = findViewById<TextView>(R.id.ongkir)
        totalbayar.text = "$withongkir C"
        totall.text = "$total C"

        val submit = findViewById<Button>(R.id.submit)
        submit.setOnClickListener {
            val nama = findViewById<EditText>(R.id.titName)
            val alamat = findViewById<EditText>(R.id.titAlamat)
            val no = findViewById<EditText>(R.id.titNo)
            if (nama.text.toString() == "")
            {
                nama.error = "Silahkan Isi Nama Terlebih Dahulu"
            }
            else{
                if (alamat.text.toString() == "")
                {
                    alamat.error = "Silahkan Isi Alamat Terlebih Dahulu"
                }
                else{
                    if(no.text.toString() == "")
                    {
                        no.error = "Silahkan Isi Nomor Terlebih Dahulu"
                    }
                    else{
                        var totalnya = 0
                        if (txtongkir.text.toString() == "30 C")
                        {
                            totalnya = withongkir
                        }
                        else{
                            totalnya = total
                        }
                        var sessionManager = SessionManager(this)
                        var user: HashMap<String, String> = sessionManager.userDetail
                        var emaill = user.get("EMAIL")
                        checkpin(cabe_merah, cabe_rawit_merah,cabe_rawit,cabe_hijau,bawang_merah,bawang_putih, kol,
                                wortel, kentang, tomat  , labu_jipang_kecil ,labu_jipang_besar ,kacang_panjang ,buncis ,brokoli ,
                                bunga_kol ,janten ,melinjo ,kangkung , bayam ,sawi_pahit ,sawi_manis ,sawi_putih ,daun_sop  ,
                                daun_pre ,tauge_petik ,tauge ,gambas ,timun ,terong_ungu , terong_jempol , terong_kare ,terong_telunjuk ,
                                ayam_potong ,ikan_lele ,ikan_nila ,ikan_gurami ,tahu_sumedang ,tempe_daun ,udang_kecepe , teri_belah ,
                                teri_nasi ,ikan_dencis , ikan_gembung_aso ,ikan_gembung_kuring ,ikan_tongkol_sisik ,gula_pasir ,gula_merah ,
                                garam_halus ,kacang_tanah ,ifu_mie ,bihun_jagung ,mi_lidi ,tepung_roti_segitiga_biru ,sosis_okey ,nugget_okkey ,
                                beras_ir_64_10_Kg ,beras_ir_64_5_kg ,minyak_goreng ,jahe ,lengkuas ,kunyit , kencur , sere ,daun_salam ,
                                daun_kunyit ,kemiri ,merica ,ketumbar ,pala ,cengkeh ,bunga_lawang ,kapulaga ,kayu_manis ,asam_jawa ,
                                asam_potong ,asam_sunti ,asam_kandis ,andaliman ,asam_cikala ,kincong ,rimbang , emaill!!, totalnya, alamat.text.toString(),
                                nama.text.toString(), no.text.toString()  )
                    }
                }
            }
        }

        val check = findViewById<Button>(R.id.check)
        val main = findViewById<ScrollView>(R.id.main)
        val spinkit = findViewById<SpinKitView>(R.id.spin_checkout)
        val ongkir = findViewById<EditText>(R.id.titkode)
        check.setOnClickListener {
            main.visibility = View.INVISIBLE
            spinkit.visibility = View.VISIBLE
            if (ongkir.text.toString() == "stayhome"){
                val txtongkir = findViewById<TextView>(R.id.ongkir)
                txtongkir.text = "0 C"
                totalbayar.text = "$total C"
                Handler().postDelayed({
                    main.visibility = View.VISIBLE
                    spinkit.visibility = View.INVISIBLE
                }, 2000)
            }
            else{
                Handler().postDelayed({
                    main.visibility = View.VISIBLE
                    spinkit.visibility = View.INVISIBLE
                    Toast.makeText(this, "Maaf Kode Promo Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                }, 2000)
            }
        }
    }

    private fun checkpin(cabe_merah  :Int, cabe_rawit_merah :Int,cabe_rawit :Int,cabe_hijau :Int,bawang_merah :Int,bawang_putih :Int, kol :Int,
                         wortel :Int, kentang  :Int, tomat  :Int, labu_jipang_kecil :Int,labu_jipang_besar :Int,kacang_panjang :Int,buncis :Int,brokoli :Int,
                         bunga_kol :Int,janten :Int,melinjo :Int,kangkung :Int, bayam :Int,sawi_pahit :Int,sawi_manis :Int,sawi_putih :Int,daun_sop  :Int,
                         daun_pre :Int,tauge_petik :Int,tauge :Int,gambas :Int,timun :Int,terong_ungu :Int, terong_jempol :Int, terong_kare :Int,terong_telunjuk :Int,
                         ayam_potong :Int,ikan_lele :Int,ikan_nila :Int,ikan_gurami :Int,tahu_sumedang :Int,tempe_daun :Int,udang_kecepe :Int, teri_belah :Int,
                         teri_nasi :Int,ikan_dencis :Int, ikan_gembung_aso :Int,ikan_gembung_kuring :Int,ikan_tongkol_sisik :Int,gula_pasir :Int,gula_merah :Int,
                         garam_halus :Int,kacang_tanah :Int,ifu_mie :Int,bihun_jagung :Int,mi_lidi :Int,tepung_roti_segitiga_biru :Int,sosis_okey :Int,nugget_okkey :Int,
                         beras_ir_64_10_Kg :Int,beras_ir_64_5_kg :Int,minyak_goreng :Int,jahe :Int,lengkuas :Int,kunyit :Int, kencur :Int, sere :Int,daun_salam :Int,
                         daun_kunyit :Int,kemiri :Int,merica :Int,ketumbar :Int,pala :Int,cengkeh :Int,bunga_lawang :Int,kapulaga :Int,kayu_manis :Int,asam_jawa :Int,
                         asam_potong :Int,asam_sunti :Int,asam_kandis :Int,andaliman :Int,asam_cikala :Int,kincong :Int,rimbang :Int, email:String, total:Int,
                         alamat:String, nama:String, nomor:String){
        val view = layoutInflater.inflate(R.layout.pin, null);
        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(view)
        val pinView = dialog.findViewById<Pinview>(R.id.pinview1)
        pinView.setPinViewEventListener(Pinview.PinViewEventListener { pinview, fromUser -> //Make api calls here or what not
            val intPIN = pinView.value
            val sessionManager = SessionManager(this)
            val user = sessionManager.userDetail
            val pin = user["PIN"]
            if (pin == "123456") {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Masih Default!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN anda masih dalam keadaan default dari sistem, silahkan ganti PIN anda pada menu AKUN.")
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
            } else if (pin == intPIN) {
                dialog.cancel()
                val main = findViewById<ScrollView>(R.id.main)
                val spinkit = findViewById<SpinKitView>(R.id.spin_checkout)
                main.visibility = View.INVISIBLE
                spinkit.visibility = View.VISIBLE
                checkout(cabe_merah, cabe_rawit_merah,cabe_rawit,cabe_hijau,bawang_merah,bawang_putih, kol,
                        wortel, kentang, tomat  , labu_jipang_kecil ,labu_jipang_besar ,kacang_panjang ,buncis ,brokoli ,
                        bunga_kol ,janten ,melinjo ,kangkung , bayam ,sawi_pahit ,sawi_manis ,sawi_putih ,daun_sop  ,
                        daun_pre ,tauge_petik ,tauge ,gambas ,timun ,terong_ungu , terong_jempol , terong_kare ,terong_telunjuk ,
                        ayam_potong ,ikan_lele ,ikan_nila ,ikan_gurami ,tahu_sumedang ,tempe_daun ,udang_kecepe , teri_belah ,
                        teri_nasi ,ikan_dencis , ikan_gembung_aso ,ikan_gembung_kuring ,ikan_tongkol_sisik ,gula_pasir ,gula_merah ,
                        garam_halus ,kacang_tanah ,ifu_mie ,bihun_jagung ,mi_lidi ,tepung_roti_segitiga_biru ,sosis_okey ,nugget_okkey ,
                        beras_ir_64_10_Kg ,beras_ir_64_5_kg ,minyak_goreng ,jahe ,lengkuas ,kunyit , kencur , sere ,daun_salam ,
                        daun_kunyit ,kemiri ,merica ,ketumbar ,pala ,cengkeh ,bunga_lawang ,kapulaga ,kayu_manis ,asam_jawa ,
                        asam_potong ,asam_sunti ,asam_kandis ,andaliman ,asam_cikala ,kincong ,rimbang , email, total, alamat,
                        nama, nomor)
            } else if (pin != intPIN) {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Salah!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN yang anda masukkan salah. Silahkan coba lagi.")
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
        })
        dialog.show()
    }

    private fun checkout(   cabe_merah  :Int, cabe_rawit_merah :Int,cabe_rawit :Int,cabe_hijau :Int,bawang_merah :Int,bawang_putih :Int, kol :Int,
                            wortel :Int, kentang  :Int, tomat  :Int, labu_jipang_kecil :Int,labu_jipang_besar :Int,kacang_panjang :Int,buncis :Int,brokoli :Int,
                            bunga_kol :Int,janten :Int,melinjo :Int,kangkung :Int, bayam :Int,sawi_pahit :Int,sawi_manis :Int,sawi_putih :Int,daun_sop  :Int,
                            daun_pre :Int,tauge_petik :Int,tauge :Int,gambas :Int,timun :Int,terong_ungu :Int, terong_jempol :Int, terong_kare :Int,terong_telunjuk :Int,
                            ayam_potong :Int,ikan_lele :Int,ikan_nila :Int,ikan_gurami :Int,tahu_sumedang :Int,tempe_daun :Int,udang_kecepe :Int, teri_belah :Int,
                            teri_nasi :Int,ikan_dencis :Int, ikan_gembung_aso :Int,ikan_gembung_kuring :Int,ikan_tongkol_sisik :Int,gula_pasir :Int,gula_merah :Int,
                            garam_halus :Int,kacang_tanah :Int,ifu_mie :Int,bihun_jagung :Int,mi_lidi :Int,tepung_roti_segitiga_biru :Int,sosis_okey :Int,nugget_okkey :Int,
                            beras_ir_64_10_Kg :Int,beras_ir_64_5_kg :Int,minyak_goreng :Int,jahe :Int,lengkuas :Int,kunyit :Int, kencur :Int, sere :Int,daun_salam :Int,
                            daun_kunyit :Int,kemiri :Int,merica :Int,ketumbar :Int,pala :Int,cengkeh :Int,bunga_lawang :Int,kapulaga :Int,kayu_manis :Int,asam_jawa :Int,
                            asam_potong :Int,asam_sunti :Int,asam_kandis :Int,andaliman :Int,asam_cikala :Int,kincong :Int,rimbang :Int, email:String, total:Int,
                            alamat:String, nama:String, nomor:String) {
        val URL_checkout = "https://awalspace.com/app/imbalopunyajangandiganggu/checkout.php"
        val stringRequest = object : StringRequest(Request.Method.POST, URL_checkout,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success.equals("1")) {
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spinkit = findViewById<SpinKitView>(R.id.spin_checkout)
                                spinkit.visibility = View.GONE
                                val store = Store()
                                store.reset()
                                startActivity(Intent(this, Success::class.java))
                            }, 3000)
                        }
                        else{
                            Handler().postDelayed({
                                val main = findViewById<ScrollView>(R.id.main)
                                val spinkit = findViewById<SpinKitView>(R.id.spin_checkout)
                                main.visibility = View.VISIBLE
                                spinkit.visibility = View.INVISIBLE
                                Toast.makeText(this, "Saldo Anda Tidak Mencukupi", Toast.LENGTH_SHORT).show()
                            }, 3000)
                        }

                    } catch (e: JSONException) {
                        Handler().postDelayed({
                            e.printStackTrace()
                            val main = findViewById<ScrollView>(R.id.main)
                            val spinkit = findViewById<SpinKitView>(R.id.spin_checkout)
                            main.visibility = View.VISIBLE
                            spinkit.visibility = View.INVISIBLE
                            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
                        }, 3000)
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params.put("Cabe_Merah", cabe_merah.toString())
                params.put("Cabe_Rawit Merah", cabe_rawit_merah.toString())
                params.put("Cabe_Rawit", cabe_rawit.toString())
                params.put("Cabe_Hijau", cabe_hijau.toString())
                params.put("Bawang_Merah", bawang_merah.toString())
                params.put("Bawang_Putih", bawang_putih.toString())
                params.put("Kol", kol.toString())
                params.put("Wortel", wortel.toString())
                params.put("Kentang", kentang.toString())
                params.put("Tomat", tomat.toString())
                params.put("Labu_Jipang_Kecil", labu_jipang_kecil.toString())
                params.put("Labu_Jipang_Besar", labu_jipang_besar.toString())
                params.put("Kacang_Panjang", kacang_panjang.toString())
                params.put("Buncis", buncis.toString())
                params.put("Brokoli", brokoli.toString())
                params.put("Bunga_Kol", bunga_kol.toString())
                params.put("Janten", janten.toString())
                params.put("Melinjo", melinjo.toString())
                params.put("Kangkung", kangkung.toString())
                params.put("Bayam", bayam.toString())
                params.put("Sawi_Pahit", sawi_pahit.toString())
                params.put("Sawi_Manis", sawi_manis.toString())
                params.put("Sawi_Putih", sawi_putih.toString())
                params.put("Daun_Sop", daun_sop.toString())
                params.put("Daun_Pre", daun_pre.toString())
                params.put("Tauge_Petik", tauge_petik.toString())
                params.put("Tauge", tauge.toString())
                params.put("Gambas", gambas.toString())
                params.put("Timun", timun.toString())
                params.put("Terong_Ungu", terong_ungu.toString())
                params.put("Terong_Jempol", terong_jempol.toString())
                params.put("Terong_Kare", terong_kare.toString())
                params.put("Terong_Telunjuk", terong_telunjuk.toString())
                params.put("Ayam_Potong", ayam_potong.toString())
                params.put("Ikan_Lele", ikan_lele.toString())
                params.put("Ikan_Nila", ikan_nila.toString())
                params.put("Ikan_Gurami", ikan_gurami.toString())
                params.put("Tahu_Sumedang", tahu_sumedang.toString())
                params.put("Tempe_Daun", tempe_daun.toString())
                params.put("Udang_Kecepe", udang_kecepe.toString())
                params.put("Teri_Belah", teri_belah.toString())
                params.put("Teri_Nasi", teri_nasi.toString())
                params.put("Ikan_Dencis", ikan_dencis.toString())
                params.put("Ikan_Gembung_Aso", ikan_gembung_aso.toString())
                params.put("Ikan_Gembung_Kuring", ikan_gembung_kuring.toString())
                params.put("Ikan_Tongkol_Sisik", ikan_tongkol_sisik.toString())
                params.put("Gula_Pasir", gula_pasir.toString())
                params.put("Gula_Merah", gula_merah.toString())
                params.put("Garam_Halus", garam_halus.toString())
                params.put("Kacang_Tanah", kacang_tanah.toString())
                params.put("Ifu_Mie", ifu_mie.toString())
                params.put("Bihun_Jagung", bihun_jagung.toString())
                params.put("Mi_Lidi", mi_lidi.toString())
                params.put("Tepung_Roti_Segitiga_Biru", tepung_roti_segitiga_biru.toString())
                params.put("Sosis_Okkey", sosis_okey.toString())
                params.put("Nugget_Okkey", nugget_okkey.toString())
                params.put("Beras_IR_64_10_Kg", beras_ir_64_10_Kg.toString())
                params.put("Beras_IR_64_5_Kg", beras_ir_64_5_kg.toString())
                params.put("Minyak_Goreng_2_Liter", minyak_goreng.toString())
                params.put("Jahe", jahe.toString())
                params.put("Lengkuas", lengkuas.toString())
                params.put("Kunyit", kunyit.toString())
                params.put("Kencur", kencur.toString())
                params.put("Sere", sere.toString())
                params.put("Daun_Salam", daun_salam.toString())
                params.put("Daun_Kunyit", daun_kunyit.toString())
                params.put("Kemiri", kemiri.toString())
                params.put("Merica", merica.toString())
                params.put("Ketumbar", ketumbar.toString())
                params.put("Pala", pala.toString())
                params.put("Cengkeh", cengkeh.toString())
                params.put("Bunga_Lawang", bunga_lawang.toString())
                params.put("Kapulaga", kapulaga.toString())
                params.put("Kayu_Manis", kayu_manis.toString())
                params.put("Asam_Jawa", asam_jawa.toString())
                params.put("Asam_Potong", asam_potong.toString())
                params.put("Asam_Sunti", asam_sunti.toString())
                params.put("Asam_Kandis", asam_kandis.toString())
                params.put("Andaliman", andaliman.toString())
                params.put("Asam_Cikala", asam_cikala.toString())
                params.put("Kincong", kincong.toString())
                params.put("Rimbang", rimbang.toString())
                params.put("email", email)
                params.put("harga", total.toString())
                params.put("nama", nama)
                params.put("nomor", nomor)
                params.put("alamat", alamat)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }


}

class Checkout(val item: Checkout,val context: Context): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.checkout
    }

    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.name.text = item.product
        viewHolder.itemView.count.text = item.jumlah.toString()
    }
}
