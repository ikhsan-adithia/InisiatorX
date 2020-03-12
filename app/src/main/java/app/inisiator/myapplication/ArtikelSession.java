package app.inisiator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class ArtikelSession {
    private final Context context;
    private SharedPreferences sharedPreferences3;
    private SharedPreferences.Editor editor3;
    int PRIVATE_MODE3 = 0;
    private static final String PREF_NAME3 = "LOGIN3";
    private static final String LOGIN3 = "IS_LOGIN3";
    private static final String TITLE1 = "TITLE1";
    private static final String TITLE2 = "TITLE2";
    private static final String TITLE3 = "TITLE3";
    private static final String TITLE4 = "TITLE4";
    private static final String TITLE5 = "TITLE5";
    private static final String PREVIEW1 = "PREVIEW1";
    private static final String PREVIEW2 = "PREVIEW2";
    private static final String PREVIEW3 = "PREVIEW3";
    private static final String PREVIEW4 = "PREVIEW4";
    private static final String PREVIEW5 = "PREVIEW5";
    private static final String TIME1 = "TIME1";
    private static final String TIME2 = "TIME2";
    private static final String TIME3 = "TIME3";
    private static final String TIME4 = "TIME4";
    private static final String TIME5 = "TIME5";
    private static final String URL1 = "URL1";
    private static final String URL2 = "URL2";
    private static final String URL3 = "URL3";
    private static final String URL4 = "URL4";
    private static final String URL5 = "URL5";

    @SuppressLint("CommitPrefEdits")
    public ArtikelSession(Context context) {
        this.context = context;
        sharedPreferences3 = context.getSharedPreferences(PREF_NAME3, PRIVATE_MODE3);
        editor3 = sharedPreferences3.edit();
    }

    public void createSession(String TITLE1, String TITLE2, String TITLE3, String TITLE4, String TITLE5, String PREVIEW1, String PREVIEW2, String PREVIEW3, String PREVIEW4, String PREVIEW5, String TIME1, String TIME2, String TIME3, String TIME4, String TIME5, String URL1, String URL2, String URL3, String URL4, String URL5){
        editor3.putBoolean(LOGIN3, true);
        editor3.putString("TITLE1", TITLE1);
        editor3.putString("TITLE2", TITLE2);
        editor3.putString("TITLE3", TITLE3);
        editor3.putString("TITLE4", TITLE4);
        editor3.putString("TITLE5", TITLE5);
        editor3.putString("PREVIEW1", PREVIEW1);
        editor3.putString("PREVIEW2", PREVIEW2);
        editor3.putString("PREVIEW3", PREVIEW3);
        editor3.putString("PREVIEW4", PREVIEW4);
        editor3.putString("PREVIEW5", PREVIEW5);
        editor3.putString("TIME1", TIME1);
        editor3.putString("TIME2", TIME2);
        editor3.putString("TIME3", TIME3);
        editor3.putString("TIME4", TIME4);
        editor3.putString("TIME5", TIME5);
        editor3.putString("URL1", URL1);
        editor3.putString("URL2", URL2);
        editor3.putString("URL3", URL3);
        editor3.putString("URL4", URL4);
        editor3.putString("URL5", URL5);
        editor3.apply();
    }

    public HashMap<String, String> getArtikelSession(){
        HashMap<String, String> user = new HashMap<>();
        user.put(TITLE1, sharedPreferences3.getString(TITLE1, null));
        user.put(TITLE2, sharedPreferences3.getString(TITLE2, null));
        user.put(TITLE3, sharedPreferences3.getString(TITLE3, null));
        user.put(TITLE4, sharedPreferences3.getString(TITLE4, null));
        user.put(TITLE5, sharedPreferences3.getString(TITLE5, null));
        user.put(PREVIEW1, sharedPreferences3.getString(PREVIEW1, null));
        user.put(PREVIEW2, sharedPreferences3.getString(PREVIEW2, null));
        user.put(PREVIEW3, sharedPreferences3.getString(PREVIEW3, null));
        user.put(PREVIEW4, sharedPreferences3.getString(PREVIEW4, null));
        user.put(PREVIEW5, sharedPreferences3.getString(PREVIEW5, null));
        user.put(TIME1, sharedPreferences3.getString(TIME1, null));
        user.put(TIME2, sharedPreferences3.getString(TIME2, null));
        user.put(TIME3, sharedPreferences3.getString(TIME3, null));
        user.put(TIME4, sharedPreferences3.getString(TIME4, null));
        user.put(TIME5, sharedPreferences3.getString(TIME5, null));
        user.put(URL1, sharedPreferences3.getString(URL1, null));
        user.put(URL2, sharedPreferences3.getString(URL2, null));
        user.put(URL3, sharedPreferences3.getString(URL3, null));
        user.put(URL4, sharedPreferences3.getString(URL4, null));
        user.put(URL5, sharedPreferences3.getString(URL5, null));
        return user;
    }

    public void logout(){
        editor3.clear();
        editor3.commit();
    }
}
