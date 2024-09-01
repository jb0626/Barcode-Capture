package dev.barcode.capture.ui

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webChromeClient = WebChromeClient()
                loadUrl(url)
                addJavascriptInterface(
                    JsBridge(),
                    "Native"
                )
            }
        }
    )
}

class JsBridge {
    @JavascriptInterface
    fun testConsolelog() {
        println("This' from Web")
    }
}