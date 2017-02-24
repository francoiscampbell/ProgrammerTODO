package xyz.fcampbell.programmertodo.api.github.model

import com.google.gson.annotations.SerializedName

data class ClientApp(
        val name: String,
        val url: String,
        @SerializedName("client_id") val clientId: String
)