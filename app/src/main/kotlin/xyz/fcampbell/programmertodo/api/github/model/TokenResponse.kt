package xyz.fcampbell.programmertodo.api.github.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by francois on 2017-02-24.
 */
data class TokenResponse(
        val id: Int,
        val url: String,
        val app: ClientApp,
        val token: String,
        @SerializedName("hashed_token") val hashedToken: String,
        @SerializedName("token_last_eight") val tokenLastEight: String,
        val note: String,
        @SerializedName("note_url") val noteUrl: String,
        @SerializedName("created_at") val createdAt: Date,
        @SerializedName("updated_at") val updatedAt: Date,
        val scopes: List<String>,
        val fingerprint: String
)

