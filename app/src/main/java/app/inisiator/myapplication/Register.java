package app.inisiator.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static String URL_REGIST = "https://awalspace.com/app/imbalopunyajangandiganggu/register.php ";
    SessionManager sessionManager;
    private EditText nama, email, no, password;
    private Button btn_SignUp;
    private TextView link_signin, alert;
    private CheckBox checkbox;
    private RelativeLayout main,utama,dua;
    private SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        utama = findViewById(R.id.h);
        dua = findViewById(R.id.dua);
        main = findViewById(R.id.main);
        no = findViewById(R.id.titPhone);
        nama = findViewById(R.id.titName);
        email = findViewById(R.id.titEmail);
        alert = findViewById(R.id.textView24);
        btn_SignUp = findViewById(R.id.btnSignUp);
        password = findViewById(R.id.titPassword);
        spinKitView = findViewById(R.id.spin_kit);
        link_signin = findViewById(R.id.tvToSignIn);
        checkbox = findViewById(R.id.cbPrivacyPolicy);
        sessionManager = new SessionManager(this);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_SignUp.setBackground(getResources().getDrawable(R.drawable.roundedred));
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateName();
                alert.setVisibility(View.VISIBLE);
            }
        });

        link_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void validateName() {
        final String name = this.nama.getText().toString().trim();
        if (name.isEmpty()) {
            alert.setText("Nama Tidak Boleh Kosong");
        } else if (name.length() < 3) {
            alert.setText("Nama Terlalu Pendek");
        } else {
            nama.setError(null);
            validateEmail();
        }
    }

    private void validateEmail() {
        final String mail = this.email.getText().toString().trim();
        if (mail.isEmpty()) {
            alert.setText("Email Tidak Boleh Kosong");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            alert.setText("Email Anda Tidak Valid");
        } else {
            email.setError(null);
            validateHP();
        }
    }

    private void validateHP() {
        final String hp = this.no.getText().toString().trim();
        if (hp.isEmpty()) {
            alert.setText("Nomor Handphone Tidak Boleh Kosong");
        } else if (hp.length() < 10) {
            alert.setText("Nomor Handphone Anda Tidak Valid");
        } else {
            no.setError(null);
            validatePass();
        }
    }

    private void validatePass() {
        final String pass = this.password.getText().toString().trim();
        if (pass.isEmpty()) {
            alert.setText("Kata Sandi Tidak Boleh Kosong");
        } else if (pass.length() < 6) {
            alert.setText("Kata Sandi Harus Lebih Dari 6 Character");
        } else {
            password.setError(null);
            check();
        }
    }

    private void check() {
        if (checkbox.isChecked()) {
            utama.setBackgroundColor(Color.parseColor("#FFFFFF"));
            dua.setVisibility(View.GONE);
            main.setVisibility(View.GONE);
            spinKitView.setVisibility(View.VISIBLE);
            Regist();
        } else {
            alert.setText("Tolong Terima Privacy Policy InisiatorX");
        }
    }

    private void Regist() {

        final String nama = this.nama.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String no = this.no.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        sessionManager.createSession(email, password, no, nama,"" );
                                        spinKitView.setVisibility(View.GONE);
                                        Intent intent = new Intent(Register.this, Success.class);
                                        startActivity(intent);
                                    }
                                }, 3000);
                            } else if (success.equals("0")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        dua.setVisibility(View.VISIBLE);
                                        spinKitView.setVisibility(View.INVISIBLE);
                                        main.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(Register.this, Failed.class);
                                        startActivity(intent);
                                    }
                                }, 3000);
                            } else if (success.equals("3")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        dua.setVisibility(View.VISIBLE);
                                        spinKitView.setVisibility(View.INVISIBLE);
                                        main.setVisibility(View.VISIBLE);
                                        alert.setText("Email Telah Digunakan!");
                                    }
                                }, 3000);
                            } else {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        dua.setVisibility(View.VISIBLE);
                                        spinKitView.setVisibility(View.INVISIBLE);
                                        main.setVisibility(View.VISIBLE);
                                        alert.setText("Nomor Handphone Telah Digunakan!");
                                    }
                                }, 3000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Intent intent = new Intent(Register.this, Failed.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(Register.this, Failed.class);
                        startActivity(intent);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nama);
                params.put("no_hp", no);
                params.put("email", email);
                params.put("password", password);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
