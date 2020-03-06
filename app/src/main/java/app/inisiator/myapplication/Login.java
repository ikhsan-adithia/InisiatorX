package app.inisiator.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static String URL_LOGIN = "https://awalspace.com/app/imbalopunyajangandiganggu/login.php";
    SessionManager sessionManager;
    private EditText email, password;
    private Button login;
    private TextView link_regist, tp;
    RelativeLayout mainn,utama,dua;
    SpinKitView spin_kit;
    ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        mainn = findViewById(R.id.main);
        spin_kit = findViewById(R.id.spin_kit);
        email = findViewById(R.id.titEmail);
        tp = findViewById(R.id.textView30);
        password = findViewById(R.id.titPassword);
        login = findViewById(R.id.btnSignIn);
        link_regist = findViewById(R.id.tvToSignUp);
        utama = findViewById(R.id.utama);
        dua = findViewById(R.id.dua);
        bg = findViewById(R.id.bg);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                login.setBackground(getResources().getDrawable(R.drawable.roundedred));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                tp.setVisibility(View.VISIBLE);

                if (!mEmail.isEmpty()) {
                    if (!mPassword.isEmpty()) {
                        utama.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        dua.setVisibility(View.GONE);
                        mainn.setVisibility(View.GONE);
                        bg.setVisibility(View.GONE);
                        spin_kit.setVisibility(View.VISIBLE);
                        login(mEmail, mPassword);
                    } else {
                        tp.setText("Silahkan Masukkan Password");
                    }
                } else {
                    tp.setText("Silahkan Masukkan Email");
                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void login(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    final String no_hp = object.getString("no_hp").trim();
                                    final String name = object.getString("name").trim();
                                    final String photo = object.getString("photo").trim();

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            spin_kit.setVisibility(View.GONE);
                                            sessionManager.createSession(email, password, no_hp, name, photo );
                                            Intent intent = new Intent(Login.this, Success.class);
                                            startActivity(intent);
                                        }
                                    }, 3000);

                                }
                            } else if (success.equals("0")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something after 5s = 5000ms
                                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        dua.setVisibility(View.VISIBLE);
                                        bg.setVisibility(View.VISIBLE);
                                        spin_kit.setVisibility(View.INVISIBLE);
                                        mainn.setVisibility(View.VISIBLE);
                                        tp.setText("Password yang anda masukkan salah");
                                    }
                                }, 3000);
                            } else if (success.equals("2")) {
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        dua.setVisibility(View.VISIBLE);
                                        bg.setVisibility(View.VISIBLE);
                                        spin_kit.setVisibility(View.INVISIBLE);
                                        mainn.setVisibility(View.VISIBLE);
                                        tp.setText("Email Yang anda masukkan tidak ditemukan");
                                    }
                                }, 3000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            dua.setVisibility(View.VISIBLE);
                            bg.setVisibility(View.VISIBLE);
                            spin_kit.setVisibility(View.INVISIBLE);
                            mainn.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(Login.this, Failed.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utama.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        dua.setVisibility(View.VISIBLE);
                        bg.setVisibility(View.VISIBLE);
                        spin_kit.setVisibility(View.INVISIBLE);
                        mainn.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(Login.this, Failed.class);
                        startActivity(intent);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
