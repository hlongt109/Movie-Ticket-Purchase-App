package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class Payment(
    val id: String,
    val method: String,
    val status: Boolean,
    val transactionDate: String // day to payment
)
data class PaymentResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("method") val method: String,
    @SerializedName("status") val status: Boolean,
    @SerializedName("transactionDate") val transactionDate: String
)
fun PaymentResponse.toPayment(): Payment{
    return Payment(
        id = this.id,
        method = this.method,
        status = this.status,
        transactionDate = this.transactionDate
    )
}
data class PaymentFormData(
    var id: String? = "",
    var method: String = "",
    var status: Boolean = false,
    var transactionDate: String = ""
)
fun Payment?.toFormData() = this?.let {
    PaymentFormData(
        this.id,
        this.method,
        this.status,
        this.transactionDate
    )
}
