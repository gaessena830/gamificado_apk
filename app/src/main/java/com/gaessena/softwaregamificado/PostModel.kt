package com.gaessena.softwaregamificado

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("status")
    var status:String,
    @SerializedName("message")
    var message:String,
    @SerializedName("page")
    var page: String
)
