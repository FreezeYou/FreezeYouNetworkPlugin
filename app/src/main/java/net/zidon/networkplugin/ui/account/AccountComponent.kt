package net.zidon.networkplugin.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.zidon.networkplugin.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountComponent(activityViewModel: MainViewModel) {
    val accountInfo = activityViewModel.accountInfo
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        item {
            if (activityViewModel.accountDataIsLoading) {
                UserInfoBannerComponent("Loading", Icons.Rounded.AccountCircle) {}
            } else if (activityViewModel.accountLogged) {
                // TODO: https://coil-kt.github.io/coil/compose/
                if (activityViewModel.accountAvatarLoaded) {
                    UserInfoBannerComponent(accountInfo.username, accountInfo.avatarUrl) {}
                } else {
                    UserInfoBannerComponent(accountInfo.username, Icons.Rounded.AccountCircle) {}
                }
            } else {
                UserInfoBannerComponent("Login", Icons.Rounded.AccountCircle) {}
            }
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(144.dp),
        onClick = { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            // TODO: https://coil-kt.github.io/coil/compose/
            Text(username, fontSize = 24.sp, modifier = Modifier.padding(end = 24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBannerComponent(username: String, avatar: ImageVector, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(144.dp),
        onClick = { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                avatar, username,
                Modifier
                    .padding(24.dp)
                    .size(96.dp)
            )
            Text(username, fontSize = 24.sp, modifier = Modifier.padding(end = 24.dp))
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
            Text(title)
        }
    }
}

/**
 * @param username Previous used username
 */
@Composable
fun LoginTable(username: String? = null) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = if (username == null || "" == username) "Hi," else "Welcome back,",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = if (username == null || "" == username) "sign in or sign up" else username,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = if (username == null || "" == username) FontWeight.Normal else FontWeight.Bold
                )
            }
            Column {
                Button(
                    onClick = { /*TODO*/ },
                    content = {
                        Icon(Icons.Rounded.Login, contentDescription = "Account icon")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Sign in")
                    }
                )
                ElevatedButton(
                    onClick = { /*TODO*/ },
                    content = {
                        Text(text = "Sign up")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Rounded.AddCircle, contentDescription = "Account icon")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginTable()
    }
}
