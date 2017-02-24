package xyz.fcampbell.programmertodo.api.github.model

import java.util.*

/**
 * Created by francois on 2017-02-24.
 */
data class TokenResponse(
        val id: Int,
        val url: String,
        val app: ClientApp,
        val token: String,
        val hashed_token: String,
        val token_last_eight: String,
        val note: String,
        val note_url: String,
        val created_at: Date,
        val updated_at: Date,
        val scopes: List<String>,
        val fingerprint: String
)

