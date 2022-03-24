package net.zidon.networkplugin.model

data class AccountInfo(
    val userId: Long,
    val username: String,
    val avatarUrl: String,
    val token: String
)
