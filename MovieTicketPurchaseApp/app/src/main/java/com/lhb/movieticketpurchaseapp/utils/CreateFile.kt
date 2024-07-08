package com.lhb.movieticketpurchaseapp.utils

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
fun createFileByUri(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, System.currentTimeMillis().toString() + ".jpg")
    val outputStream = FileOutputStream(tempFile)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    return tempFile.path
}
fun Context.createFileByUris(uri: Uri, fileType: String): File? {
    val fileName = "${System.currentTimeMillis()}.$fileType" + ".jpg"
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
fun Context.createFileVideo(uri: Uri, fileNamePrefix: String): File? {
    val inputStream = contentResolver.openInputStream(uri)
    val fileName = "$fileNamePrefix-${System.currentTimeMillis()}.mp4"
    val file = File(cacheDir, fileName)
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }
    return file
}
