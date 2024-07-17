package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class TimeFrame(
    val id: String,
    val startTime: String,
    val endTime: String
)
data class TimeFrameResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String
)
fun TimeFrameResponse.toTimeFrame(): TimeFrame{
    return TimeFrame(
        id = this.id,
        startTime = this.startTime,
        endTime = this.endTime
    )
}
data class TimeFrameFormData(
    var id: String? = "",
    var startTime: String = "",
    var endTime: String = "",
)
fun TimeFrame?.toFormData() = this?.let {
    TimeFrameFormData(
        this.id,
        this.startTime,
        this.endTime
    )
}