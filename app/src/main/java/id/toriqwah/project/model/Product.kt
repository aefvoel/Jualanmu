package id.toriqwah.project.model

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("uid")
    var uid: String? = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("category")
    var category: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("cost")
    var cost: Long? = null,
    @SerializedName("margin")
    var margin: String = "",
    @SerializedName("price")
    var price: Long? = null,
    @SerializedName("stock")
    var stock: Long? = null,
    @SerializedName("min_stock")
    var min_stock: Long? = null,
    @SerializedName("img")
    var img: String = ""

)