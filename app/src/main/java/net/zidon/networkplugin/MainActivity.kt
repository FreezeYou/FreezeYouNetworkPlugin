package net.zidon.networkplugin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.zidon.networkplugin.ui.theme.FreezeYouNetworkPluginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreezeYouNetworkPluginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginTable("FreezeYou")
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
                    Icon(Icons.Rounded.AccountCircle, contentDescription = "Account icon")
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