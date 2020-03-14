package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;

public class NotifikasiSession {
    private final Context context;
    private SharedPreferences sharedPreferences5;
    private SharedPreferences.Editor editor5;
    int PRIVATE_MODE5 = 0;
    private static final String PREF_NAME5 = "LOGIN5";
    private static final String LOGIN5 = "IS_LOGIN5";

    @SuppressLint("CommitPrefEdits")
    public NotifikasiSession(Context context) {
        this.context = context;
        sharedPreferences5 = context.getSharedPreferences(PREF_NAME5, PRIVATE_MODE5);
        editor5 = sharedPreferences5.edit();
    }

    public void createSession(JSONArray ARRAY){
        editor5.putBoolean(LOGIN5, true);
        editor5.putString("ARRAY", String.valueOf(ARRAY));
        editor5.apply();
    }

    public HashMap<String, String> getNotifikasiSession(){
        HashMap<String, String> user = new HashMap<>();
        user.put("ARRAY", sharedPreferences5.getString("ARRAY", null));
        return user;
    }

    public void logout(){
        editor5.clear();
        editor5.commit();
    }
}
