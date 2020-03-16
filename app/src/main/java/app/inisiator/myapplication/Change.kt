package app.inisiator.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.activity_change.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Change : AppCompatActivity() {

    private val URL_CHANGEP = "https://awalspace.com/app/imbalopunyajangandiganggu/changep.php"
    var sessionManager: SessionManager? = null
    private var Oldpass: EditText? = null
    private  var Newpass:EditText? = null
    private var main: ScrollView? = null
    private var spin_kit: SpinKitView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        sessionManager = SessionManager(this)
        val userr: HashMap<String, String> = sessionManager!!.userDetail
        val no_ = userr.get("NO")
        Oldpass = findViewById(R.id.titPassword)
        Newpass = findViewById(R.id.titPassword2)
        main = findViewById(R.id.main)
        spin_kit = findViewById(R.id.spin_kit)

        submit.setOnClickListener {
            val oldpass = Oldpass!!.getText().toString().trim { it <= ' ' }
            val newpass = Newpass!!.getText().toString().trim { it <= ' ' }
            if (!oldpass.isEmpty()) {
                if (!newpass.isEmpty()) {
                    main!!.setVisibility(View.INVISIBLE)
                    spin_kit!!.setVisibility(View.VISIBLE)
                    changep(oldpass, newpass, no_!!)
                } else {
                    Newpass!!.setError("Silahkan Masukkan Password Baru")
                }
            } else {
                if (!newpass.isEmpty()) {
                    Oldpass!!.setError("Silahkan Masukkan Password Lama")
                } else {
                    Oldpass!!.setError("Silahkan Masukkan Password Lama")
                    Newpass!!.setError("Silahkan Masukkan Password Baru")
                }
            }
        }
    }

    private fun changep(oldpass: String, newpass: String, no_: String) {
        val stringRequest: StringRequest = object : StringRequest(Method.POST, URL_CHANGEP,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")
                        if (success == "1") {
                            sessionManager!!.logout()
                            val intent = Intent(this, Login::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else if (success == "0") {
                            Toast.makeText(this, "Password Lama Anda Salah", Toast.LENGTH_SHORT).show()
                            main = findViewById(R.id.main)
                            spin_kit = findViewById(R.id.spin_kit)
                            main!!.setVisibility(View.VISIBLE)
                            spin_kit!!.setVisibility(View.INVISIBLE)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        main = findViewById(R.id.main)
                        spin_kit = findViewById(R.id.spin_kit)
                        main!!.setVisibility(View.VISIBLE)
                        spin_kit!!.setVisibility(View.INVISIBLE)
                        //                            Toast.makeText(ChangepActivity.this, "Error "+e.toString(),Toast.LENGTH_SHORT).show();
                    }
                },
                Response.ErrorListener {
                    //                        Toast.makeText(ChangepActivity.this, "Error "+error.toString(),Toast.LENGTH_SHORT).show();
                    main = findViewById(R.id.main)
                    spin_kit = findViewById(R.id.spin_kit)
                    main!!.setVisibility(View.VISIBLE)
                    spin_kit!!.setVisibility(View.INVISIBLE)
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["oldpass"] = oldpass
                params["newpass"] = newpass
                params["no"] = no_
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
