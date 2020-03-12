package app.inisiator.myapplication

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_read.*

class ReadActivity : AppCompatActivity() {

    private var view: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        val intent = intent
        val url = intent.getStringExtra("url")
        val time = intent.getStringExtra("time")?.toInt()
        val urlTimer: Long = (time!! * 60 * 1000).toLong()

        fab_read.setOnClickListener {
            if (finishedCountDown) {
                finish()
            } else {
                Toast.makeText(this, "Masih ada sisa: ${fab_read.text} detik lagi", Toast.LENGTH_SHORT).show()
            }
        }

        timeProgress_read.bringToFront()
        timeProgress_read.max = time * 60

        web_read.settings.javaScriptEnabled = true
        web_read.webViewClient = WebViewClient()
        web_read.loadUrl(url)
        web_read.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                spinKit_read?.visibility = View.GONE
                timeProgress_read.visibility = View.VISIBLE
                web_read?.visibility = View.VISIBLE
                if (calledOnce) startCountDown(urlTimer)
            }
        }
    }

    var calledOnce: Boolean = true

    private fun startCountDown(urlTimer: Long) {
        fab_read.visibility = View.VISIBLE
        val timer = MyCounter(urlTimer, 1000)
        timer.start()
        calledOnce = false
    }

    var finishedCountDown: Boolean = false

    inner class MyCounter(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            fab_read.visibility = View.VISIBLE
            fab_read.text = "↪"
            finishedCountDown = true
        }

        override fun onTick(millisUntilFinished: Long) {
            val timeLeft = millisUntilFinished / 1000
            timeProgress_read.progress = timeLeft.toInt()
        }

    }
}
