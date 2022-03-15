package net.zidon.networkplugin

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object World : Screen("world", R.string.world, Icons.Rounded.TravelExplore)
    object Home : Screen("home", R.string.home, Icons.Rounded.Home)
    object Account : Screen("account", R.string.account, Icons.Rounded.Person)
}
