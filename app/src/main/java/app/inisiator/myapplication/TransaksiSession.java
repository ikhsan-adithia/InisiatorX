package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;

public class TransaksiSession {

    private final Context context;
    private SharedPreferences sharedPreferences6;
    private SharedPreferences.Editor editor6;
    int PRIVATE_MODE6 = 0;
    private static final String PREF_NAME6 = "LOGIN6";
    private static final String LOGIN6 = "IS_LOGIN6";

    @SuppressLint("CommitPrefEdits")
    public TransaksiSession(Context context) {
        this.context = context;
        sharedPreferences6 = context.getSharedPreferences(PREF_NAME6, PRIVATE_MODE6);
        editor6 = sharedPreferences6.edit();
    }

    public void createSession(JSONArray ARRAY){
        editor6.putBoolean(LOGIN6, true);
        editor6.putString("ARRAY", String.valueOf(ARRAY));
        editor6.apply();
    }

    public HashMap<String, String> getTransaksiSession(){
        HashMap<String, String> user = new HashMap<>();
        user.put("ARRAY", sharedPreferences6.getString("ARRAY", null));
        return user;
    }

    public void logout(){
        editor6.clear();
        editor6.commit();
    }
}
