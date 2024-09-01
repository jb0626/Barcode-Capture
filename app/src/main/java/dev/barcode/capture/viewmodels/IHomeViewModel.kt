package dev.barcode.capture.viewmodels

import kotlinx.coroutines.flow.StateFlow

interface IHomeViewModel {

    val inputUrl: StateFlow<String>

    fun setInputUrl(value: String)

}