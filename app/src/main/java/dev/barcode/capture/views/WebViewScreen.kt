package dev.barcode.capture.views

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import dev.barcode.capture.viewmodels.IBridge
import dev.barcode.capture.viewmodels.WebViewModel

@Composable
fun WebViewScreen(
    navController: NavHostController,
    viewModel:WebViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (viewModel.isEmptyUrl.collectAsState().value) {
            ShowEmptyView(navController)
        } else {
            ShowWebView(viewModel.url)
        }
    }
}

@Composable
private fun ShowEmptyView(navController: NavHostController) {
    ElevatedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { navController.popBackStack() }
    ) {
        Text(
            text = "Not found the page\nPlease check url again",
            fontSize = 16.sp
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun ShowWebView(url: String) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webChromeClient = WebChromeClient()
                loadUrl(url)
            }
        }
    )
}
