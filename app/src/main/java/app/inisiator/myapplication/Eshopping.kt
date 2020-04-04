package app.inisiator.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class Eshopping : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eshopping)

        val pin = findViewById<LinearLayout>(R.id.pin)
        pin.setOnClickListener {
            startActivity(Intent(this, Pin::class.java))
        }

        val ganci = findViewById<LinearLayout>(R.id.ganci)
        ganci.setOnClickListener {
            startActivity(Intent(this, Ganci::class.java))
        }

        val vegetable = findViewById<LinearLayout>(R.id.vegetable)
        vegetable.setOnClickListener {
            startActivity(Intent(this, Store::class.java))
        }
    }
}
