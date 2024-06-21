package com.lhb.movieticketpurchaseapp.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import java.io.*

fun Context.createFileFromUri(uri: Uri,name: String): File?{
    val file = File(cacheDir, "$name.png")
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
            }
        }
        Log.d("TAG", "File successfully created at: ${file.path}")
        file
    }catch (e: FileNotFoundException) {
        e.printStackTrace()
        Log.e("TAG", "FileNotFoundException: ${e.message}")
        null
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e("TAG", "IOException: ${e.message}")
        null
    }
}