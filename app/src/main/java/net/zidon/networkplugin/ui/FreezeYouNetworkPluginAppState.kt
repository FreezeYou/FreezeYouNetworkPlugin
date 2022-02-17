package net.zidon.networkplugin.ui

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import net.zidon.networkplugin.Screen

@Composable
fun rememberFreezeYouNetworkPluginAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(navController, coroutineScope) {
        FreezeYouNetworkPluginAppState(navController, coroutineScope)
    }

@Stable
class FreezeYouNetworkPluginAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope
) {

    private val currentRoute: String
        get() = navController.currentDestination?.route ?: ""

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(Screen.Home.route) {
                    saveState = true
                }
            }
        }
    }

}
