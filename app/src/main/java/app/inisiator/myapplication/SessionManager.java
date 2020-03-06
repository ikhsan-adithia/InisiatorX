package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private final Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    static final String EMAIL = "EMAIL";
    static final String PASSWORD = "PASSWORD";
    static final String NO = "NO";
    static final String NAMA = "NAMA";
    static final String PHOTO = "PHOTO";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    void createSession(String Email, String Password, String No, String Nama, String Photo){
        editor.putBoolean(LOGIN, true);
        editor.putString("EMAIL", Email);
        editor.putString("PASSWORD", Password);
        editor.putString("NO",No);
        editor.putString("NAMA", Nama);
        editor.putString("PHOTO", Photo);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin(Context context){
        if (!this.isLoggin()) {
            Intent i = new Intent (context, Login.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(NO,sharedPreferences.getString(NO,null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(PHOTO, sharedPreferences.getString(PHOTO, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
