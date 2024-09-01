package dev.barcode.capture.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.Settings
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import dev.barcode.capture.BuildConfig
import dev.barcode.capture.extensions.createTmpImageFile
import dev.barcode.capture.viewmodels.WebViewModel
import java.util.Objects

@Composable
fun WebViewScreen(
    navController: NavHostController,
    viewModel: WebViewModel
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
    var webViewCallback: ValueCallback<Array<Uri>>? = null

    val context = LocalContext.current
    val file = context.createTmpImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                webViewCallback?.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        Activity.RESULT_OK,
                        Intent().apply {
                            data = uri
                        }
                    )
                )
                webViewCallback = null
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(uri)
        } else {
            webViewCallback?.onReceiveValue(null)
            webViewCallback = null

            Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
            context.startActivity(intent)
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                webChromeClient = object : WebChromeClient() {
                    override fun onShowFileChooser(
                        webView: WebView?,
                        filePathCallback: ValueCallback<Array<Uri>>?,
                        fileChooserParams: FileChooserParams?
                    ): Boolean {
                        if (webViewCallback != null) {
                            webViewCallback?.onReceiveValue(null)
                            webViewCallback = null
                        }
                        webViewCallback = filePathCallback

                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }

                        return true
                    }
                }
                loadUrl(url)
            }
        }
    )
}
