package dev.barcode.capture.extensions

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Context.createTmpImageFile(): File = File.createTempFile(
    "tmp_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}",
    ".jpg",
    externalCacheDir
)