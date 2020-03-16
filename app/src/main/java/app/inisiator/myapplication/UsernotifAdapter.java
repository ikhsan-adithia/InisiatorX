package app.inisiator.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsernotifAdapter extends RecyclerView.Adapter<UsernotifAdapter.UsernotifHolder> {

    private static String URL_HAPUS = "https://awalspace.com/app/imbalopunyajangandiganggu/hapusnotif.php";
    private static String URL_TERIMA = "https://awalspace.com/app/imbalopunyajangandiganggu/terimarequest.php";
    private static String URL_TOLAK = "https://awalspace.com/app/imbalopunyajangandiganggu/tolakrequest.php";
    private Context mCtx;
    private List<UserNotif> userNotifList;

    public UsernotifAdapter(Context mCtx, List<UserNotif> userNotifList) {
        this.mCtx = mCtx;
        this.userNotifList = userNotifList;
    }

    @Override
    public UsernotifHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.user_row, null);
        return new UsernotifHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsernotifHolder holder, int position) {
        UserNotif userNotif = userNotifList.get(position);
        holder.id_notif.setText(String.valueOf(userNotif.getId()));
        holder.no_asa.setText(userNotif.getAsal_notif());
        holder.isi.setText(userNotif.getIsi_notif());
        holder.catatan.setText(userNotif.getCatatan());
        holder.type.setText(userNotif.getType_notif());
        if (userNotif.getType_notif().equals("sendnormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_btn_send);
        } else if (userNotif.getType_notif().equals("paynormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_btn_pay);
        } else if (userNotif.getType_notif().equals("topupnormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_btn_top_up);
        } else if (userNotif.getType_notif().equals("pinnormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_shopping);
        } else if (userNotif.getType_notif().equals("gancinormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_shopping);
        } else if (userNotif.getType_notif().equals("eprintnormal")) {
            holder.img.setBackgroundResource(R.drawable.ic_print);
        } else {
            holder.img.setBackgroundResource(R.drawable.ic_btn_request);
        }

    }

    @Override
    public int getItemCount() {
        return userNotifList.size();
    }

    private void hapus(final String id) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_HAPUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(mCtx, Success.class);
                                mCtx.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mCtx, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, "Error Mengirim " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);

    }

    private void terimarequest(final String id) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TERIMA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(mCtx, Success.class);
                                mCtx.startActivity(intent);
                            } else {
                                Toast.makeText(mCtx, "Saldo Tidak Cukup", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mCtx, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, "Error Mengirim " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);

    }

    private void tolakrequest(final String id) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TOLAK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Intent intent = new Intent(mCtx, Success.class);
                                mCtx.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mCtx, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, "Error Mengirim " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);

    }

    public class UsernotifHolder extends RecyclerView.ViewHolder {

        TextView id_notif, no_asa, isi, catatan, type;
        ImageView img;

        public UsernotifHolder(@NonNull final View itemView) {
            super(itemView);

            id_notif = itemView.findViewById(R.id.id_notif);
            no_asa = itemView.findViewById(R.id.no_asal_notif);
            isi = itemView.findViewById(R.id.isi_notif);
            catatan = itemView.findViewById(R.id.catatan);
            type = itemView.findViewById(R.id.type);
            img = itemView.findViewById(R.id.imageView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (type.getText().toString().equals("requestaction")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        builder.setTitle(no_asa.getText().toString());
                        builder.setMessage(isi.getText().toString() + ".\n" + "Catatan : " + catatan.getText().toString());
                        builder.setPositiveButton("Terima", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Start Terima
                                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                builder.setTitle("Terima Request");
                                builder.setMessage("Anda Yakin Menerima Request Tersebut ?");
                                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        terimarequest(id_notif.getText().toString());
                                    }
                                });
                                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                //Finish Terima
                            }
                        });
                        builder.setNegativeButton("Tolak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Start Tolak
                                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                                builder.setTitle("Tolak Request");
                                builder.setMessage("Anda Yakin Menolak Request Tersebut ?");
                                builder.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tolakrequest(id_notif.getText().toString());
                                    }
                                });
                                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                //Finish Tolak
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        builder.setTitle(no_asa.getText().toString());
                        builder.setMessage(isi.getText().toString() + ".\n" + "Catatan : " + catatan.getText().toString());
                        builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String id = id_notif.getText().toString();
                                hapus(id);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });
        }
    }
}
