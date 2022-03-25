package net.zidon.networkplugin.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zidon.networkplugin.MainViewModel
import net.zidon.networkplugin.ui.FreezeYouNetworkPluginAppState


@Composable
fun LoginComponent(activityViewModel: MainViewModel, appState: FreezeYouNetworkPluginAppState) {
    val accountInfo = activityViewModel.accountInfo

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = { appState.navigateUp() },
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
        ) { Icon(Icons.Rounded.ArrowBack, "Back") }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = if (accountInfo.username.isEmpty()) "Hi," else "Welcome back,",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = accountInfo.username.ifEmpty { "sign in or sign up" },
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = if (accountInfo.username.isEmpty()) FontWeight.Normal else FontWeight.Bold
                )
            }
            Column {
                Button(
                    onClick = { /*TODO*/ },
                    content = {
                        Icon(Icons.Rounded.Login, contentDescription = "Sign in")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Sign in")
                    }
                )
                ElevatedButton(
                    onClick = { /*TODO*/ },
                    content = {
                        Text(text = "Sign up")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Rounded.AddCircle, contentDescription = "Sign up")
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
//        LoginComponent()
    }
}
