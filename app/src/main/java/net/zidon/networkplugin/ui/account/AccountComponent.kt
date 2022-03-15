package net.zidon.networkplugin.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Login
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountComponent() {
    val scroll = rememberScrollState(0)
    Column(
        modifier = Modifier.verticalScroll(scroll)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .height(160.dp)
            ) {
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        ) {
            Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.Start) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }) {
                    Row(modifier = Modifier
                    .padding(12.dp)) {
                        Icon(Icons.Rounded.Settings, "")
                        Spacer(modifier = Modifier.size(16.dp))
                        Text("Settings")
                    }
                }
            }
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
