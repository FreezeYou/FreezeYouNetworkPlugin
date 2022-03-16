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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldComponent() {
    var moreMenuExpanded by remember { mutableStateOf(false) }
    val listData = listOf(1, 2, 3)

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
            Row(
                modifier = Modifier.fillParentMaxWidth(),
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
        item {
            Spacer(Modifier.height(4.dp))
        }
        items(listData) {
            Box(
                Modifier
                    .padding(horizontal = 8.dp, vertical = 1.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { }) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillParentMaxWidth()
                ) {
                    Text(text = "123$it", fontSize = 24.sp)
                    Text(text = "123")
                }
            }
        }
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorldComponent()
}
