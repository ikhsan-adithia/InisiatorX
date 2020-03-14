package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.HashMap;

public class KegiatanSession {
    private final Context context;
    private SharedPreferences sharedPreferences4;
    private SharedPreferences.Editor editor4;
    int PRIVATE_MODE4 = 0;
    private static final String PREF_NAME4 = "LOGIN4";
    private static final String LOGIN4 = "IS_LOGIN4";

    @SuppressLint("CommitPrefEdits")
    public KegiatanSession(Context context) {
        this.context = context;
        sharedPreferences4 = context.getSharedPreferences(PREF_NAME4, PRIVATE_MODE4);
        editor4 = sharedPreferences4.edit();
    }

    public void createSession(JSONArray ARRAY){
        editor4.putBoolean(LOGIN4, true);
        editor4.putString("ARRAY", String.valueOf(ARRAY));
        editor4.apply();
    }

    public HashMap<String, String> getKegiatanSession(){
        HashMap<String, String> user = new HashMap<>();
        user.put("ARRAY", sharedPreferences4.getString("ARRAY", null));
        return user;
    }

    public void logout(){
        editor4.clear();
        editor4.commit();
    }
}
