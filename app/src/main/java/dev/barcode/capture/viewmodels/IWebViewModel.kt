package dev.barcode.capture.viewmodels

import kotlinx.coroutines.flow.StateFlow

interface IWebViewModel {

    val url: String

    val isEmptyUrl: StateFlow<Boolean>

}