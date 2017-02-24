package xyz.fcampbell.programmertodo.api.github.model

/**
 * Created by francois on 2017-02-24.
 */
data class TokenRequest(
        val scopes: List<String>,
        val note: String
)