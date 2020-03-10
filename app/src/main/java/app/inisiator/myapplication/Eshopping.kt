package app.inisiator.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Eshopping : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eshopping)

        val pin = findViewById<Button>(R.id.btntopin)
        pin.setOnClickListener {
            startActivity(Intent(this, Pin::class.java))
        }

        val ganci = findViewById<Button>(R.id.btntoganci)
        ganci.setOnClickListener {
            startActivity(Intent(this, Ganci::class.java))
        }
    }
}
