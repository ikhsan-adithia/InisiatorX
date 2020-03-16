package app.inisiator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    Integer tampilan = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentPage(new Beranda());
        final SessionManager sessionManager = new SessionManager(this);
        sessionManager.checkLogin(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;

                switch (menuItem.getItemId()){
                    case R.id.beranda:
                        if(tampilan == 0) {}
                        else { fragment = new Beranda(); tampilan = 0; }
                        break;

                    case R.id.peluang:
                        if(tampilan == 1){}
                        else{ fragment = new Peluang(); tampilan = 1; }
                        break;

                    case R.id.notifikasi:
                        if(tampilan == 2){}
                        else{ fragment = new Notifikasi(); tampilan = 2;}
                        break;

                    case R.id.akun:
                        if(tampilan == 3){}
                        else{ fragment = new Akun(); tampilan = 3;}
                        break;

                }
                return getFragmentPage(fragment);
            }
        });


    }

    public void status (Boolean bl, FragmentActivity activity){
        if(bl == true)
        {
            View beranda = activity.findViewById(R.id.beranda);
            View peluang = activity.findViewById(R.id.peluang);
            View notifikasi = activity.findViewById(R.id.notifikasi);
            View akun = activity.findViewById(R.id.akun);
            beranda.setEnabled(true);
            peluang.setEnabled(true);
            notifikasi.setEnabled(true);
            akun.setEnabled(true);
        }
        else {
            View beranda = activity.findViewById(R.id.beranda);
            View peluang = activity.findViewById(R.id.peluang);
            View notifikasi = activity.findViewById(R.id.notifikasi);
            View akun = activity.findViewById(R.id.akun);
            beranda.setEnabled(false);
            peluang.setEnabled(false);
            notifikasi.setEnabled(false);
            akun.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean getFragmentPage(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        final TemporarySession temporarySession = new TemporarySession(this);
        temporarySession.logout();
        final ArtikelSession artikelSession = new ArtikelSession(this);
        artikelSession.logout();
        final KegiatanSession kegiatanSession = new KegiatanSession(this);
        kegiatanSession.logout();
        final NotifikasiSession notifikasiSession = new NotifikasiSession(this);
        notifikasiSession.logout();
        final TransaksiSession transaksiSession = new TransaksiSession(this);
        transaksiSession.logout();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();
            }
        }).start();
    }

    public void clearglide(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();
            }
        }).start();
    }
}
