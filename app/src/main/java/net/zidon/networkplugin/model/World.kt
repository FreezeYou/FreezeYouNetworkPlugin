package net.zidon.networkplugin.model

@kotlinx.serialization.Serializable
data class WorldSharedItem(
    val worldId: Long,
    val title: String,
    val subtitle: String,
    val tag: List<WorldSharedItemTag>,
    var favorite: Boolean,
    val detailUrl: String,
)

@kotlinx.serialization.Serializable
data class WorldSharedItemsJson(
    val code: Int,
    val data: List<WorldSharedItem>
)

/**
 * @param currentWorldId Locally loaded smallest ID.
 */
@kotlinx.serialization.Serializable
data class WorldSharedItemsGetMoreWithToken(
    val token: String,
    val currentWorldId: Long
)

@kotlinx.serialization.Serializable
data class WorldSharedItemTag(
    val tagId: Long,
    val title: String,
    val count: Long
)

@kotlinx.serialization.Serializable
data class WorldSharedItemTagsJson(
    val code: Int,
    val data: List<WorldSharedItemTag>
)

data class WorldSharedItemTagState(
    val tag: WorldSharedItemTag,
    var checked: Boolean
)