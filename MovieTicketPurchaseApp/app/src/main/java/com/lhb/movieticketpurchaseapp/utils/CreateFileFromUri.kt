package com.lhb.movieticketpurchaseapp.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.*

fun Context.createFileFromUri(uri: Uri, fileType: String): File? {
    val fileName = "${System.currentTimeMillis()}.$fileType"
    val file = File(cacheDir, fileName)

    try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}