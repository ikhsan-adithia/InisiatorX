package app.inisiator.myapplication

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_qr_ticket_preview.*
import java.io.File

class QrTicketPreviewActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_ticket_preview)

        val qrUri = intent.getStringExtra("QR_URI")
        val eventTitle = intent.getStringExtra("EVENT_TITLE")
        val eventLokasi = intent.getStringExtra("EVENT_LOKASI")
        val eventTanggal = intent.getStringExtra("EVENT_TANGGAL")
        val eventWaktu = intent.getStringExtra("EVENT_WAKTU")

        val file = File(qrUri!!)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            qr_preview.setImageBitmap(bitmap)

            ticketpreview_event_title.text = eventTitle
            ticketpreview_lokasi_waktu.text = "$eventLokasi | $eventWaktu"
            ticketpreview_tanggal.text = eventTanggal
        }

        ticketpreview_btnOk.setOnClickListener {
            finish()
        }
    }
}
