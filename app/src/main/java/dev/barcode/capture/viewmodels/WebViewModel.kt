package dev.barcode.capture.viewmodels

import androidx.lifecycle.ViewModel

class WebViewModel(
    override val url: String
) : ViewModel(), IWebViewModel {

}