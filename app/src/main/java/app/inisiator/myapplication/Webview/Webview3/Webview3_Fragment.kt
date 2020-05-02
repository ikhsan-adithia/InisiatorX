package app.inisiator.myapplication.Webview.Webview3

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.inisiator.myapplication.R
import com.github.ybq.android.spinkit.SpinKitView

class Webview3_Fragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_webview3, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val spinkit = root.findViewById<View>(R.id.Spinkit) as SpinKitView
        val webview = root.findViewById<View>(R.id.webview) as WebView

        webview.loadUrl("https://www.facebook.com/")
        val wVSettings = webview.settings
        wVSettings.javaScriptEnabled = true
        webview.canGoBack()
        webview.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && webview.canGoBack()) {
                webview.goBack()
                return@OnKeyListener true
            }
            false
        })
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Handler().postDelayed({
                    spinkit.visibility = View.GONE
                    webview.visibility = View.VISIBLE
                }, 2000)
            }
        }

        return root
    }
}
