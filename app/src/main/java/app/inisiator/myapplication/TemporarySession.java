package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;

public class TemporarySession {

    private final Context context;
    private SharedPreferences sharedPreferences2;
    private SharedPreferences.Editor editor2;
    int PRIVATE_MODE2 = 0;
    private static final String PREF_NAME2 = "LOGIN2";
    private static final String LOGIN2 = "IS_LOGIN2";
    private static final String BALANCE = "BALANCE";
    private static final String NAMA1 = "NAMA1";
    private static final String NAMA2 = "NAMA2";
    private static final String NAMA3 = "NAMA3";
    private static final String POINT1 = "POINT1";
    private static final String POINT2 = "POINT2";
    private static final String POINT3 = "POINT3";
    private static final String PHOTO1 = "PHOTO1";
    private static final String PHOTO2 = "PHOTO2";
    private static final String PHOTO3 = "PHOTO3";
    private static final String REFERRAl = "REFERRAl";
    private static final String POINT = "POINT";
    private static final String POSITION = "POSITION";



    @SuppressLint("CommitPrefEdits")
    public TemporarySession(Context context) {
        this.context = context;
        sharedPreferences2 = context.getSharedPreferences(PREF_NAME2, PRIVATE_MODE2);
        editor2 = sharedPreferences2.edit();
    }

    void createSession(String Balance, String NAMA1, String NAMA2, String NAMA3, String POINT1, String POINT2, String POINT3, String PHOTO1, String PHOTO2, String PHOTO3, String REFERRAl, String POINT, String POSITION){
        editor2.putBoolean(LOGIN2, true);
        editor2.putString("BALANCE", Balance);
        editor2.putString("NAMA1", NAMA1);
        editor2.putString("NAMA2", NAMA2);
        editor2.putString("NAMA3", NAMA3);
        editor2.putString("POINT1", POINT1);
        editor2.putString("POINT2", POINT2);
        editor2.putString("POINT3", POINT3);
        editor2.putString("PHOTO1", PHOTO1);
        editor2.putString("PHOTO2", PHOTO2);
        editor2.putString("PHOTO3", PHOTO3);
        editor2.putString("REFERRAl", REFERRAl);
        editor2.putString("POINT", POINT);
        editor2.putString("POSITION",POSITION);
        editor2.apply();
    }

    public HashMap<String, String> getTemporarySession(){
        HashMap<String, String> user = new HashMap<>();
        user.put(BALANCE, sharedPreferences2.getString(BALANCE, null));
        user.put(NAMA1, sharedPreferences2.getString(NAMA1, null));
        user.put(NAMA2, sharedPreferences2.getString(NAMA2, null));
        user.put(NAMA3, sharedPreferences2.getString(NAMA3, null));
        user.put(POINT1, sharedPreferences2.getString(POINT1, null));
        user.put(POINT2, sharedPreferences2.getString(POINT2, null));
        user.put(POINT3, sharedPreferences2.getString(POINT3, null));
        user.put(PHOTO1, sharedPreferences2.getString(PHOTO1, null));
        user.put(PHOTO2, sharedPreferences2.getString(PHOTO2, null));
        user.put(PHOTO3, sharedPreferences2.getString(PHOTO3, null));
        user.put(REFERRAl,sharedPreferences2.getString(REFERRAl, null));
        user.put(POINT, sharedPreferences2.getString(POINT, null));
        user.put(POSITION, sharedPreferences2.getString(POSITION, null));
        return user;
    }

    public void logout(){
        editor2.clear();
        editor2.commit();
    }
}
