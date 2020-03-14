package app.inisiator.myapplication

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.HashMap

class Akun : Fragment() {
    var temporarySession: TemporarySession? = null
    var sessionManager: SessionManager? = null
    val URL_UPLOAD = "https://awalspace.com/app/imbalopunyajangandiganggu/upload.php"
    private var txtnama: TextView? = null
    private var txtemail: TextView? = null
    private var txtnohp: TextView? = null
    private var txtreferral: TextView? = null
    private var txtbalance: TextView? = null
    private var txtnameawal: TextView? = null
    private var txtemailawal: TextView? = null
    private var txtpoint: TextView? = null
    private var txtposition: TextView? = null
    private lateinit var root: View
    private lateinit var profilee: CircleImageView
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root =  inflater.inflate(R.layout.fragment_akun, container, false)

        temporarySession = TemporarySession(activity)
        sessionManager = SessionManager(activity)
        txtnama = root.findViewById(R.id.txtMyName)
        txtemail = root.findViewById(R.id.txtMyLevel)
        txtnohp = root.findViewById(R.id.txtMyPhoneNumber)
        txtreferral = root.findViewById(R.id.txtMyReferralCode)
        txtbalance = root.findViewById(R.id.dailyScore)
        txtnameawal = root.findViewById(R.id.userName_HomeContent)
        txtemailawal = root.findViewById(R.id.userEmail_HomeContent)
        txtpoint = root.findViewById(R.id.txtMyEnergyLeft)
        txtposition = root.findViewById(R.id.txtMyRank)

        val profilee = root.findViewById<View>(R.id.profilePicture_Profile) as CircleImageView
        val logout = root.findViewById<View>(R.id.logoutLayout)
        val changep = root.findViewById<View>(R.id.passwordLayout)
        val user: HashMap<String, String> = sessionManager!!.userDetail
        val nama = user.get("NAMA")
        val email = user.get("EMAIL")
        val phone = user.get("NO")
        val photo = user.get("PHOTO")
        val user1: HashMap<String, String> = temporarySession!!.temporarySession
        val balance = user1.get("BALANCE")
        val referral = user1.get("REFERRAl")
        val point = user1.get("POINT")
        val position = user1.get("POSITION")
        profilee.setOnClickListener {chooseFile()}
        logout.setOnClickListener {signOutUser() }

        txtnama!!.text = nama
        txtemail!!.text  = email
        txtnohp!!.text = phone
        txtbalance!!.text = balance
        txtemailawal!!.text = email
        txtnameawal!!.text = nama
        txtreferral!!.text = referral
        txtpoint!!.text = point
        txtposition!!.text = position
        if (photo.equals("")) {
        }
        else {
            val profilee = root.findViewById<View>(R.id.profilePicture_Profile) as CircleImageView
            Glide.with(activity!!).load(photo).into(profilee)}
        return root
    }

    private fun signOutUser() {

        val alertDialog = AlertDialog.Builder(activity)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout from Inisiator?")
                .setPositiveButton("Yes") { _, _ ->
                    val sessionManager = SessionManager(activity)
                    sessionManager.logout()
                    val intent = Intent(activity, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .setNegativeButton("No") { _, _ -> }

        alertDialog.show()
    }

    private fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === RESULT_OK && data != null && data.data != null) {
            val filepath = data.data
            try {
                profilee = activity?.findViewById<View>(R.id.profilePicture_Profile) as CircleImageView
                bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, filepath)
                profilee.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var sessionManager = SessionManager(activity)
            var user: HashMap<String, String> = sessionManager.userDetail
            var emaill = user.get("EMAIL")
            UploadPicture(emaill!!, bitmap?.let { getStringImage(it) }!!)
        }
    }

    private fun UploadPicture(email: String, photo: String) {

        val progressdialog = ProgressDialog(activity)
        progressdialog.setMessage("Uploading...")
        progressdialog.show()

        val stringRequest = object : StringRequest(Request.Method.POST, URL_UPLOAD,
                Response.Listener { response ->
                    try {
                        progressdialog.dismiss()
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getString("success")

                        if (success.equals("1")) {
                            val home = MainActivity()
                            home.clearglide()

                            val alertDialog = AlertDialog.Builder(activity)
                                    .setTitle("Berhasil")
                                    .setMessage("Foto Profil Anda Berhasil Diganti, Silahkan Mulai Ulang Untuk Meliat Hasilnya")
                                    .setPositiveButton("Mulai Ulang") { _, _ ->
                                        val frg = getFragmentManager()!!.findFragmentById(R.id.akun);
                                        val ft = getFragmentManager()!!.beginTransaction();
                                        ft.detach(frg!!);
                                        ft.attach(frg);
                                        ft.commit();
                                    }
                                    .setCancelable(true)

                            alertDialog.show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        progressdialog.dismiss()
                        Toast.makeText(activity, "Try Again!", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener { error -> }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()
                params.put("email", email)
                params.put("photo", photo)
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    fun getStringImage(bitmap: Bitmap): String? {

        val bytearrayoutputstream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytearrayoutputstream)
        var imageByteArray = bytearrayoutputstream.toByteArray()
        var encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT)


        return encodedImage
    }
}