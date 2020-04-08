package app.inisiator.myapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.activity_insert_file_kompetisi.*
import kotlinx.android.synthetic.main.activity_print.*
import net.gotev.uploadservice.MultipartUploadRequest
import java.util.*


class InsertFileKompetisi : AppCompatActivity() {

    val urlRegistrasikompetisi = "https://awalspace.com/app/imbalopunyajangandiganggu/registrasiKompetisi.php"
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_file_kompetisi)

        btn_PilihFileKomp.setOnClickListener {
            AllowRunTimePermission()
            val intent = Intent()
                    .setType("application/pdf")
                    .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Pilih File"), 1)
        }

        btnSubmitKarya.setOnClickListener {
            uploadMultipart()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedFile = data.data
            filePath = selectedFile
            Log.d("InsertFile", "--------noActivityResult--------")
            Log.d("InsertFile", "selectedFile           : $selectedFile")
            Log.d("InsertFile", "selectedFile           : $selectedFile")
            Log.d("InsertFile", "data?.data.toString()  : ${data.data.toString()}")
            Log.d("InsertFile", "data?.type.toString()  : ${data.type.toString()}")
            Log.d("InsertFile", "filesDir.toString()    : ${this.filesDir}")
            Log.d("InsertFile", "filesDir.path          : ${filesDir.path}")
            Log.d("InsertFile", "filesDir.absolutePath  : ${filesDir.absolutePath}")
            Log.d("InsertFile", "filesDir.absoluteFile  : ${filesDir.absoluteFile}")
            Log.d("InsertFile", "filesDir.name          : ${filesDir.name}")
        }
    }

    private fun uploadMultipart() {

        val namaFile = FilePath.getPath(this, filePath)
        Log.d("InsertFile", "--------uploadMultipart--------")
        Log.d("InsertFile", "namaFile    : ${namaFile.trim()}")
        if (namaFile == null) {
            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show()
        } else {
            try {
                val name = "karyaKompetisi"
                val uploadId = UUID.randomUUID().toString()
                var sessionManager = SessionManager(this)
                var user: HashMap<String, String> = sessionManager.userDetail
                var email = user.get("EMAIL")
                var nama_pengirim = user["NAMA"]
                val nama_komp = intent.getStringExtra("NAMA_KOMP")

                MultipartUploadRequest(this, uploadId, urlRegistrasikompetisi)
                        .addFileToUpload(namaFile.trim(), "pdf")
                        .addParameter("name", name)
                        .addParameter("email", email)
                        .addParameter("nama_pengirim", nama_pengirim)
                        .addParameter("nama_pengirim", nama_komp)
                        .setMaxRetries(2)
                        .startUpload()
//                        .addParameter("file", namaFile)

            } catch (exception: Exception) {
                Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
                Log.e("InsertFile", exception.message!!)
//                Handler().postDelayed({
//                    val main = findViewById<ScrollView>(R.id.main)
//                    val spin_kit = findViewById<SpinKitView>(R.id.spin_kit)
//                    main.visibility = View.VISIBLE
//                    spin_kit.visibility = View.INVISIBLE
//                    Toast.makeText(this, "Try Again", Toast.LENGTH_SHORT).show()
//                }, 3000)
            }
        }
    }

    fun AllowRunTimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onRequestPermissionsResult(RC: Int, per: Array<String>, Result: IntArray) {
        when (RC) {
            1 ->
                if (Result.size > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission Canceled", Toast.LENGTH_LONG).show()
                }
        }
    }
}
