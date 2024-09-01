package dev.barcode.capture.viewmodels

import android.webkit.JavascriptInterface

interface IBridge {

    @JavascriptInterface
    fun testConsolelog()

}