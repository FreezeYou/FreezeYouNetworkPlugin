package net.zidon.networkplugin.model

data class WorldSharedItem(
    val worldId: Long,
    val title: String,
    val subtitle: String,
    val tag: List<WorldSharedItemTag>,
    var favorite: Boolean,
    val detailUrl: String,
)

data class WorldSharedItemTag(
    val tagId: Long,
    val title: String,
    val count: Long
)

data class WorldSharedItemTagState(
    val tag: WorldSharedItemTag,
    var checked: Boolean
)