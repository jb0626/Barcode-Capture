package dev.barcode.capture.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WebViewModel(
    override val url: String
) : ViewModel(), IWebViewModel {

    private var _isEmptyUrl = MutableStateFlow(false)
    override val isEmptyUrl = _isEmptyUrl.asStateFlow()

    init {
        _isEmptyUrl.tryEmit(url.trim().isEmpty())
    }

}