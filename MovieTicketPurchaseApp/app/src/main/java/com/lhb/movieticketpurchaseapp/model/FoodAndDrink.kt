package com.lhb.movieticketpurchaseapp.model

import com.google.gson.annotations.SerializedName

data class FoodAndDrink(
    val id: String,
    val name: String,
    val price: Double,
    val image: String
)
data class FoodAndDrinkResponse(
    @SerializedName("_id")val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String
)
fun FoodAndDrinkResponse.toFoodDrink(): FoodAndDrink{
    return FoodAndDrink(
        id = this.id,
        name = this.name,
        price = this.price,
        image = this.image
    )
}
data class FoodAndDrinkFormData(
    var id: String? = "",
    var name: String = "",
    var price: Double = 0.0,
    var image: String = ""
)
fun FoodAndDrink?.toFormData() = this?.let {
    FoodAndDrinkFormData(
        this.id,
        this.name,
        this.price,
        this.image
    )
}