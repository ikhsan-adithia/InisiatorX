package app.inisiator.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import com.goodiebag.pinview.Pinview
import com.goodiebag.pinview.Pinview.PinViewEventListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class Changepin : AppCompatActivity() {

    private val URL_CHANGEP = "https://awalspace.com/app/imbalopunyajangandiganggu/changepin.php"
    var sessionManager: SessionManager? = null
    private var main: RelativeLayout? = null
    private var spin_kit: SpinKitView? = null

    var value1:String? = null
    var value2:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepin)

        sessionManager = SessionManager(this)
        val userr: HashMap<String, String> = sessionManager!!.userDetail
        val pin = userr.get("PIN")
        val no = userr.get("NO")
        val pin1 = findViewById<Pinview>(R.id.pinview1);
        val pin2 = findViewById<Pinview>(R.id.pinview2);
        val text1 = findViewById<TextView>(R.id.text1);
        val text2 = findViewById<TextView>(R.id.text2);
        pin1.setPinViewEventListener(PinViewEventListener { pinview1, _ -> //Make api calls here or what not
            if (pinview1.value == pin) {
                value1 = pinview1.value
                pin1.visibility = View.GONE
                text1.visibility = View.GONE
                text2.visibility = View.VISIBLE
                pin2.visibility = View.VISIBLE
            } else if (pinview1.value != pin) {
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

        pin2.setPinViewEventListener(PinViewEventListener { pinview2, _ -> //Make api calls here or what not
            if (pinview2.value == pin) {
                val bottomSheetDialog = BottomSheetDialog(
                        this, R.style.BottomSheetDialogTheme
                )
                val bottomSheetView = LayoutInflater.from(this)
                        .inflate(
                                R.layout.layout_bottom_notif,
                                null
                        )
                bottomSheetView.findViewById<TextView>(R.id.title).setText("PIN Sama!")
                bottomSheetView.findViewById<TextView>(R.id.subtitle).setText("Maaf, PIN yang anda masukkan sama dengan PIN anda yang terdahulu. Silahkan coba lagi.")
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
            } else if (pinview2.value != pin) {
                value2 = pinview2.value
                main = findViewById(R.id.main)
                spin_kit = findViewById(R.id.spin_kit)
                main!!.setVisibility(View.INVISIBLE)
                spin_kit!!.setVisibility(View.VISIBLE)
                changepin(value1!!, value2!!, no!!)
            }
        })
    }

    private fun changepin(oldpin: String, newpin: String, no_: String) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_CHANGEP,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            main = findViewById(R.id.main)
                            spin_kit = findViewById(R.id.spin_kit)
                            main!!.setVisibility(View.VISIBLE)
                            spin_kit!!.setVisibility(View.INVISIBLE)
                            val alertDialog = AlertDialog.Builder(this)
                                    .setTitle("Berhasil")
                                    .setMessage("Anda akan di logout untuk mendapatkan data terbaru")
                                    .setPositiveButton("Oke") { _, _ ->
                                        sessionManager!!.logout()
                                        val intent = Intent(this, Login::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                    }
                                    .setCancelable(true)

                            alertDialog.show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        main = findViewById(R.id.main)
                        spin_kit = findViewById(R.id.spin_kit)
                        main!!.setVisibility(View.VISIBLE)
                        spin_kit!!.setVisibility(View.INVISIBLE)
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
                },
                Response.ErrorListener {
                    main = findViewById(R.id.main)
                    spin_kit = findViewById(R.id.spin_kit)
                    main!!.setVisibility(View.VISIBLE)
                    spin_kit!!.setVisibility(View.INVISIBLE)
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
                val params: MutableMap<String, String> = HashMap()
                params["oldpin"] = oldpin
                params["newpin"] = newpin
                params["no"] = no_
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
