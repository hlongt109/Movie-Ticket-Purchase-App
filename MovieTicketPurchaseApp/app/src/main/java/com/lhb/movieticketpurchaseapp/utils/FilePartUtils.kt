package com.lhb.movieticketpurchaseapp.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
fun getRequestBody(value: String): RequestBody {
    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), value)
}
fun getRequestBody(value: Int): RequestBody {
    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), value.toString())
}

fun getRequestBody(value: Double): RequestBody {
    return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), value.toString())
}
fun getFilePart(name: String, filePath: String): MultipartBody.Part? {
    val file = File(filePath)
    return if (file.exists()) {
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        MultipartBody.Part.createFormData(name, file.name, requestFile)
    } else {
        null
    }
}

fun getFileParts(name: String, filePaths: List<String>): List<MultipartBody.Part> {
    return filePaths.mapNotNull { getFilePart(name, it) }
}