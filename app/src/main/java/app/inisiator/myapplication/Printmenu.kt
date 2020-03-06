package app.inisiator.myapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONArray
import org.json.JSONException

class Printmenu : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val URL_MERCHANT2 = "https://awalspace.com/app/imbalopunyajangandiganggu/get_merchant.php"
    var arrayList: ArrayList<String>? = null
    var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printmenu)
        arrayList = ArrayList()
        AllowRunTimePermission()
        val topdf = findViewById<Button>(R.id.btntopdf)
        val combine = findViewById<Button>(R.id.btncombine)
        val print = findViewById<Button>(R.id.btnprint)

        topdf.setOnClickListener {
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://topdf.com/"))
            startActivity(browserIntent)
        }

        combine.setOnClickListener {
            val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://combinepdf.com/"))
            startActivity(browserIntent)
        }

        print.setOnClickListener {
            loadmerchant()
        }
    }

    private fun loadmerchant() {
        val stringRequest = object : StringRequest(Request.Method.GET, URL_MERCHANT2,
                Response.Listener { response ->
                    try {
                        val array = JSONArray(response)
                        val view = layoutInflater.inflate(R.layout.print, null);
                        val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                        dialog.setContentView(view)

                        for (i in 0 until array.length()) {
                                val merchant = array.getJSONObject(i)
                                arrayList!!.add(merchant.getString("nama_merchant"))
                                val merchantt = dialog.findViewById<Spinner>(R.id.merchant)
                                merchantt.onItemSelectedListener = this
                                val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList!!)
                                array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                merchantt.adapter = array_adapter
                        }
                        val close = dialog.findViewById<ImageView>(R.id.close)
                        close.setOnClickListener {
                            dialog.dismiss()
                            onResume()
                        }
                        val file = dialog.findViewById<View>(R.id.btnfile) as Button
                        file.setOnClickListener {
                            chooseFile()
                            file.text = "File Terpilih"
                        }
                        val submit = dialog.findViewById<Button>(R.id.btnprint)
                        submit.setOnClickListener {
                            if (file.text.equals("Pilih File Kamu Disini"))
                            {
                                val bottomSheetDialog = BottomSheetDialog(
                                        this, R.style.BottomSheetDialogTheme
                                )
                                val bottomSheetView = LayoutInflater.from(this)
                                        .inflate(
                                                R.layout.layout_bottom_notif,
                                                null
                                        )
                                bottomSheetView.findViewById<TextView>(R.id.title).setText("Kamu belum memilih file!")
                                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Silahkan pilih file mana yang ingin kamu print terlebih dahulu.")
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
                            else
                            {
                                val jumlah = dialog.findViewById<EditText>(R.id.titJumlah)
                                if (jumlah.text.equals("") && jumlah.text.equals("0"))
                                {
                                    val bottomSheetDialog = BottomSheetDialog(
                                            this, R.style.BottomSheetDialogTheme
                                    )
                                    val bottomSheetView = LayoutInflater.from(this)
                                            .inflate(
                                                    R.layout.layout_bottom_notif,
                                                    null
                                            )
                                    bottomSheetView.findViewById<TextView>(R.id.title).setText("Jumlah print kamu masih kosong!")
                                    bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Silahkan masukkan berapa salinan print yang kamu butuhkan.")
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
                                else{

                                }
                            }
                        }
                        dialog.show()

                    } catch (e: JSONException) {
                        e.printStackTrace()
//                        Toast.makeText(activity, "Error $e", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih PDF Anda"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
        }
    }

    fun AllowRunTimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    override fun onRequestPermissionsResult(RC: Int, per: Array<String>, Result: IntArray) {
        when (RC) {
            1 ->
                if (Result.size > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission Canceled", Toast.LENGTH_LONG).show()
                }
        }
    }
}
