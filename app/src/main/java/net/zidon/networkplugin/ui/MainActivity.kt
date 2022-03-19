package net.zidon.networkplugin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import net.zidon.networkplugin.MainViewModel

class MainActivity : ComponentActivity() {
    private val activityViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FreezeYouNetworkPluginApp(activityViewModel)
        }
    }
}
