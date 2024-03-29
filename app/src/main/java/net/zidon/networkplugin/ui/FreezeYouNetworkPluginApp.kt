package net.zidon.networkplugin.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import net.zidon.networkplugin.Account
import net.zidon.networkplugin.MainViewModel
import net.zidon.networkplugin.Screen
import net.zidon.networkplugin.ui.account.AccountComponent
import net.zidon.networkplugin.ui.account.LoginComponent
import net.zidon.networkplugin.ui.home.HomeComponent
import net.zidon.networkplugin.ui.theme.FreezeYouNetworkPluginTheme
import net.zidon.networkplugin.ui.world.WorldComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreezeYouNetworkPluginApp(activityViewModel: MainViewModel) {
    FreezeYouNetworkPluginTheme {
        val appState = rememberFreezeYouNetworkPluginAppState()
        Scaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    NavigationBar {
                        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        listOf(
                            Screen.World,
                            Screen.Home,
                            Screen.Account,
                        ).forEach { item ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                onClick = { appState.navigateToBottomBarRoute(item.route) },
                                icon = { Icon(item.icon, stringResource(item.resourceId)) },
                                label = { Text(stringResource(item.resourceId)) }
                            )
                        }
                    }
                }
            }) {
            NavHost(
                navController = appState.navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(it)
            ) {
                composable(Screen.World.route) { WorldComponent(activityViewModel) }
                composable(Screen.Home.route) { HomeComponent() }
                composable(Screen.Account.route) { AccountComponent(activityViewModel, appState) }
                composable(Account.Login.route) { LoginComponent(activityViewModel, appState) }
            }
        }
    }
}

