package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class User(
//    val id: String,
//    val username: String,
//    val name: String,
//    val email: String,
//    val password: String,
//    val role: Int,
//    val avatar: String,
//    val phoneNumber: String
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: Int,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)
data class UserResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("role") val role: Int,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("phoneNumber") val phoneNumber: String
)
data class UserRequest(
    val id: String ? = null,
    val username: String,
    val name: String,
    val email: String,
    val password: String,
    val role: Int,
    val avatar: String,
    val phoneNumber: String
)
fun UserResponse.toUser(): User{
    return User(
        this.id,
        this.username,
        this.name,
        this.email,
        this.password,
        this.role,
        this.avatar,
        this.phoneNumber
    )
}
data class UserFormData(
    var id: String? = "",
    var username: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var role: Int = -1,
    var avatar: String = "",
    var phoneNumber: String = "",
)
fun User?.toFormData() = this?.let {
    UserFormData(
        this.id,
        this.username,
        this.name,
        this.email,
        this.password,
        this.role,
        this.avatar,
        this.phoneNumber
    )
}
//
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
//    val status: Int,
//    val messenger: String,
//    val data: User,
//    val token: String,
//    val refreshToken: String,
//    val role: Int,

    @SerializedName("status") val status: Int,
    @SerializedName("messenger") val messenger: String,
    @SerializedName("data") val data: User,
    @SerializedName("token") val token: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("role") val role: Int,
)
fun LoginResponse.toUserLogin(): User{
    return User(
        this.data.id,
        this.data.username,
        this.data.name,
        this.data.email,
        this.data.password,
        this.role,
        this.data.avatar,
        this.data.phoneNumber
    )
}

