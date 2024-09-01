package dev.barcode.capture.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel(), IHomeViewModel {
    private var _inputUrl = MutableStateFlow("")
    override val inputUrl = _inputUrl.asStateFlow()

    override fun setInputUrl(value: String) {
        println(value)
        _inputUrl.tryEmit(value)
    }
}