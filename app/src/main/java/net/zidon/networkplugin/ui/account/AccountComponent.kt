package net.zidon.networkplugin.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import net.zidon.networkplugin.Account
import net.zidon.networkplugin.MainViewModel
import net.zidon.networkplugin.ui.FreezeYouNetworkPluginAppState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountComponent(activityViewModel: MainViewModel, appState: FreezeYouNetworkPluginAppState) {
    val accountInfo = activityViewModel.accountInfo

    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        item {
            UserInfoBannerComponent(
                accountInfo.username,
                accountInfo.avatarUrl
            ) { appState.navigateToRoute(Account.Login.route) }
        }
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.Start) {
                    SettingsListItemComponent("Settings", Icons.Rounded.Settings) {}
                    SettingsListItemComponent("About", Icons.Rounded.Info) {}
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBannerComponent(username: String, avatarUrl: String, onClick: () -> Unit) {
    val displayName = username.ifEmpty { "Loading" }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(144.dp),
        onClick = { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = displayName,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(24.dp)
                    .size(96.dp),
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(Modifier.padding(12.dp))
                    }
                    is AsyncImagePainter.State.Error -> {
                        Icon(Icons.Rounded.AccountCircle, displayName)
                    }
                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
            Text(
                displayName,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}

@Composable
fun SettingsListItemComponent(title: String, icon: ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, title)
            Spacer(modifier = Modifier.size(12.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
        }
    }
}
