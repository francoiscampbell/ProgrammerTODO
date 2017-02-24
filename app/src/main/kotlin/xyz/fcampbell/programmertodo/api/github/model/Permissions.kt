package xyz.fcampbell.programmertodo.api.github.model

/**
 * Created by francois on 2017-02-24.
 */
data class Permissions(
        val admin: Boolean,
        val push: Boolean,
        val pull: Boolean
)