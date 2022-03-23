package net.zidon.networkplugin.ui.world

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.zidon.networkplugin.MainViewModel
import net.zidon.networkplugin.model.WorldSharedItem
import net.zidon.networkplugin.model.WorldSharedItemTag
import net.zidon.networkplugin.model.WorldSharedItemTagState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldComponent(activityViewModel: MainViewModel) {
    val scrollState = rememberLazyListState()
    val dataToDisplayOnScreen = activityViewModel.worldSharedDataToDisplayOnScreen
    val dataIsLoading = activityViewModel.worldSharedDataIsLoading
    val dataNoMore = activityViewModel.worldSharedDataNoMore

    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp),
        state = scrollState
    ) {
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(160.dp)
                ) {
                }
            }
        }
        item {
            SharedDataListToolBarComponent(
                activityViewModel.worldSharedDataTopTenTags.values,
                {
                    while (activityViewModel.worldSharedDataTopTenTags.isEmpty())
                        activityViewModel.refreshWorldSharedDataTopTenTags()
                },
                { state -> activityViewModel.changeWorldSharedDataTopTenTagCheckState(state) },
                { activityViewModel.refreshWorldSharedDataTopTenTags() }
            )
        }
        item {
            Spacer(Modifier.height(4.dp))
        }
        items(dataToDisplayOnScreen) {
            SharedDataListItemComponent(
                it,
                { activityViewModel.changeWorldSharedItemFavoriteState(it) },
                {},
                {}
            )
        }
        item {
            SharedDataListLoadMoreItemComponent(
                dataIsLoading, dataNoMore
            ) { if (!dataIsLoading) activityViewModel.getMoreWorldSharedData() }
        }
        item("footerPadding") {
            Spacer(Modifier.height(16.dp))
        }
    }

    // Autoload more
    scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.key?.let {
        if ("footerPadding" == it) {
            activityViewModel.getMoreWorldSharedData()
        }
    }
}

@Composable
fun SharedDataListToolBarComponent(
    filterItems: Collection<WorldSharedItemTagState>,
    onFilterButtonClick: () -> Unit,
    onDropMenuItemClick: (WorldSharedItemTagState) -> Unit,
    onRefreshFilterItemsClick: () -> Unit
) {
    var filterMenuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                filterMenuExpanded = true
                onFilterButtonClick()
            },
            content = {
                Icon(
                    Icons.Rounded.FilterList,
                    contentDescription = "1"
                )
            })
        IconButton(
            onClick = { },
            content = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "1"
                )
            })
    }
    Box {
        DropdownMenu(
            expanded = filterMenuExpanded,
            onDismissRequest = { filterMenuExpanded = false }) {
            val items = ArrayList(filterItems)
            items.sortWith { t1, t2 -> if (t1.tag.count > t2.tag.count) -1 else 1 }
            for (item in items) {
                SharedDataListToolBarFilterDropdownMenuItemComponent(item, onDropMenuItemClick)
            }
            DropdownMenuItem(
                text = { Text("Refresh") },
                onClick = { onRefreshFilterItemsClick() },
                leadingIcon = { Icon(Icons.Rounded.Refresh, "") },
                trailingIcon = { Icon(Icons.Rounded.Sync, "") }
            )
        }
    }
}

@Composable
fun SharedDataListToolBarFilterDropdownMenuItemComponent(
    item: WorldSharedItemTagState,
    onDropMenuItemClick: (WorldSharedItemTagState) -> Unit
) {
    DropdownMenuItem(
        text = { Text(item.tag.title) },
        onClick = { onDropMenuItemClick(item) },
        leadingIcon = {
            Icon(
                when (item.tag.count) {
                    in 9001..Long.MAX_VALUE -> Icons.Rounded._9kPlus
                    in 8001..9000 -> Icons.Rounded._8kPlus
                    in 7001..8000 -> Icons.Rounded._7kPlus
                    in 6001..7000 -> Icons.Rounded._6kPlus
                    in 5001..6000 -> Icons.Rounded._5kPlus
                    in 4001..5000 -> Icons.Rounded._4kPlus
                    in 3001..4000 -> Icons.Rounded._3kPlus
                    in 2001..3000 -> Icons.Rounded._2kPlus
                    in 1001..2000 -> Icons.Rounded._1kPlus
                    else -> Icons.Rounded.TrendingUp
                },
                "Trends"
            )
        },
        trailingIcon = {
            Icon(
                if (item.checked) Icons.Rounded.CheckBox else Icons.Rounded.CheckBoxOutlineBlank,
                ""
            )
        }
    )
}

@Composable
fun SharedDataListItemComponent(
    data: WorldSharedItem,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        Modifier
            .padding(horizontal = 8.dp, vertical = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable {},
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = data.title, fontSize = 20.sp)
            Text(text = data.subtitle, fontSize = 12.sp)
        }
        SharedDataListItemMoreButtonComponent(data, onFavoriteClick, onDownloadClick, onShareClick)
    }
}

@Composable
fun SharedDataListItemMoreButtonComponent(
    data: WorldSharedItem,
    onFavoriteClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    IconButton(onClick = { menuExpanded = true }) {
        Icon(Icons.Rounded.MoreVert, "")
        Box {
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }) {
                DropdownMenuItem(
                    text = { Text("Favorite") },
                    leadingIcon = {
                        Icon(
                            if (data.favorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                            ""
                        )
                    },
                    onClick = { onFavoriteClick() },
                )
                DropdownMenuItem(
                    text = { Text("Download") },
                    leadingIcon = { Icon(Icons.Rounded.Download, "") },
                    onClick = { onDownloadClick() },
                )
                DropdownMenuItem(
                    text = { Text("Share") },
                    leadingIcon = { Icon(Icons.Rounded.Share, "") },
                    onClick = { onShareClick() },
                )
            }
        }
    }
}

@Composable
fun SharedDataListLoadMoreItemComponent(
    dataIsLoading: Boolean,
    dataNoMore: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .padding(horizontal = 8.dp, vertical = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = if (dataIsLoading) "Loading" else if (dataNoMore) "No more" else "More",
                color = Color.LightGray,
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SharedDataListToolBarComponentPreview() {
    SharedDataListToolBarComponent(
        listOf(
            WorldSharedItemTagState(
                WorldSharedItemTag(
                    1L,
                    "1",
                    10L
                ),
                false
            ),
            WorldSharedItemTagState(
                WorldSharedItemTag(
                    2L,
                    "2",
                    100L
                ),
                true
            )
        ),
        {}, {}, {}
    )
}

@Preview(showBackground = true)
@Composable
fun SharedDataListItemComponentPreview() {
    SharedDataListItemComponent(
        WorldSharedItem(
            1,
            "Title",
            "Subtitle",
            listOf(1),
            true,
            ""
        ),
        {}, {}, {}
    )
}

@Preview(showBackground = true)
@Composable
fun SharedDataListLoadMoreItemComponentPreview() {
    SharedDataListLoadMoreItemComponent(dataIsLoading = false, dataNoMore = false) {}
}
