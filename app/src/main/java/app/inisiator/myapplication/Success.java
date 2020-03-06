package app.inisiator.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Success extends AppCompatActivity {

    private ImageView back;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        back = findViewById(R.id.backbutton);
        button = findViewById(R.id.buttonnext);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Success.this, MainActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Success.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(Success.this, MainActivity.class));
    }
}
