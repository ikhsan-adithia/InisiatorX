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
    private static final String REFERRAl = "REFERRAl";
    private static final String POINT = "POINT";



    @SuppressLint("CommitPrefEdits")
    public TemporarySession(Context context) {
        this.context = context;
        sharedPreferences2 = context.getSharedPreferences(PREF_NAME2, PRIVATE_MODE2);
        editor2 = sharedPreferences2.edit();
    }

    void createSession(String Balance, String REFERRAl, String POINT){
        editor2.putBoolean(LOGIN2, true);
        editor2.putString("BALANCE", Balance);
        editor2.putString("REFERRAl", REFERRAl);
        editor2.putString("POINT", POINT);
        editor2.apply();
    }

    public HashMap<String, String> getTemporarySession(){
        HashMap<String, String> user = new HashMap<>();
        user.put(BALANCE, sharedPreferences2.getString(BALANCE, null));
        user.put(REFERRAl,sharedPreferences2.getString(REFERRAl, null));
        user.put(POINT, sharedPreferences2.getString(POINT, null));
        return user;
    }

    public void logout(){
        editor2.clear();
        editor2.commit();
    }
}
