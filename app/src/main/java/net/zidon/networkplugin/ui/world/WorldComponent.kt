package net.zidon.networkplugin.ui.world

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldComponent(activityViewModel: MainViewModel) {
    val dataToDisplayOnScreen = activityViewModel.worldSharedDataToDisplayOnScreen
    val dataIsLoading = activityViewModel.worldSharedDataIsLoading

    LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
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
            SharedDataListToolBarComponent()
        }
        item {
            Spacer(Modifier.height(4.dp))
        }
        items(dataToDisplayOnScreen) {
            SharedDataListItemComponent(it)
        }
        item {
            SharedDataListLoadMoreItemComponent(
                dataIsLoading
            ) { if (!dataIsLoading) activityViewModel.getMoreWorldSharedData() }
        }
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SharedDataListToolBarComponent() {
    var moreMenuExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { moreMenuExpanded = true },
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
            expanded = moreMenuExpanded,
            onDismissRequest = { moreMenuExpanded = false }) {
            DropdownMenuItem(
                text = { Text("A") },
                onClick = {},
                leadingIcon = { Icon(Icons.Rounded._9kPlus, "") },
                trailingIcon = { Icon(Icons.Rounded.CheckBox, "") }
            )
            DropdownMenuItem(
                text = { Text("B") },
                onClick = {},
                leadingIcon = { Icon(Icons.Rounded._1kPlus, "") },
                trailingIcon = { Icon(Icons.Rounded.CheckBoxOutlineBlank, "") }
            )
            DropdownMenuItem(
                text = { Text("C") },
                onClick = {},
                leadingIcon = { Icon(Icons.Rounded.TrendingUp, "") },
                trailingIcon = { Icon(Icons.Rounded.CheckBoxOutlineBlank, "") }
            )
        }
    }
}

@Composable
fun SharedDataListItemComponent(data: Int) {
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
            Text(text = "Title$data", fontSize = 24.sp)
            Text(text = "Subtitle")
        }
        var menuExpanded by remember { mutableStateOf(false) }
        IconButton(
            modifier = Modifier.padding(2.dp),
            onClick = { menuExpanded = true }
        ) {
            Icon(Icons.Rounded.MoreVert, "")
            Box {
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Favorite") },
                        leadingIcon = { Icon(Icons.Rounded.FavoriteBorder, "") },
                        onClick = {},
                    )
                    DropdownMenuItem(
                        text = { Text("Download") },
                        leadingIcon = { Icon(Icons.Rounded.Download, "") },
                        onClick = {},
                    )
                    DropdownMenuItem(
                        text = { Text("Share") },
                        leadingIcon = { Icon(Icons.Rounded.Share, "") },
                        onClick = {},
                    )
                }
            }
        }
    }
}

@Composable
fun SharedDataListLoadMoreItemComponent(dataIsLoading: Boolean, onClick: () -> Unit) {
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
                text = if (dataIsLoading) "Loading" else "More",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SharedDataListToolBarComponentPreview() {
    SharedDataListToolBarComponent()
}

@Preview(showBackground = true)
@Composable
fun SharedDataListItemComponentPreview() {
    SharedDataListItemComponent(0)
}

@Preview(showBackground = true)
@Composable
fun SharedDataListLoadMoreItemComponentPreview() {
    SharedDataListLoadMoreItemComponent(false) {}
}
