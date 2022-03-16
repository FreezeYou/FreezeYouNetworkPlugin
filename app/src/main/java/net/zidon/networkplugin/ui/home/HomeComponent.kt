package net.zidon.networkplugin.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zidon.networkplugin.model.HomeCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComponent() {
    val listData = listOf(
        listOf(
            HomeCategory(0, "Cloud", Icons.Rounded.Cloud),
            HomeCategory(1, "Share", Icons.Rounded.Share)
        ),
        listOf(
            HomeCategory(2, "Download", Icons.Rounded.Download),
            HomeCategory(3, "Favorite", Icons.Rounded.Favorite)
        )
    )

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
        items(listData) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                for (i in 0..1) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth(if (i == 0) 0.5F else 1F)
                            .height(96.dp)
                            .padding(8.dp),
                        onClick = {},
                    ) {
                        Row(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(24.dp))
                            Icon(it[i].icon, "")
                            Spacer(Modifier.width(14.dp))
                            Text(it[i].name)
                            Spacer(Modifier.width(24.dp))
                        }
                    }
                }
            }
        }
        item {
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeComponent()
}
