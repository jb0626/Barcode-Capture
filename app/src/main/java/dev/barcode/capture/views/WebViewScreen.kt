package dev.barcode.capture.views

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    navController: NavHostController,
    url: String
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
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