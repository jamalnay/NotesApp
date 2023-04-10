package com.lamda.projectnotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lamda.projectnotes.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    navController: NavController,
    closeDrawer: () -> Unit,
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        ProjectNotesLogo(
            modifier = Modifier.padding(vertical = 32.dp)
        )

        NavigationDrawerItem(
            label = { Text("Home") },
            icon = { Icon(Icons.Filled.Home, "Home Screen") },
            selected = true /* currentRoute == NavDestinations.HOME_ROUTE*/,
            onClick = { navController.navigate(AppDestinations.Home.route); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            colors = NavigationDrawerItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.background,
                selectedTextColor = MaterialTheme.colorScheme.background
            )
        )

        NavigationDrawerItem(
            label = { Text("Edit Categories") },
            icon = { Icon(Icons.Filled.Apps, "Edit Categories") },
            selected = false /* currentRoute == NavDestinations.HOME_ROUTE*/,
            onClick = { navController.navigate(AppDestinations.ManageCategories.route); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text("Trash") },
            icon = { Icon(Icons.Filled.Delete, "Trash") },
            selected = false, /* currentRoute == NavDestinations.HOME_ROUTE*/
            onClick = { navController.navigate(AppDestinations.ManageDeleted.route); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )

        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        NavigationDrawerItem(
            label = { Text("About The App") },
            icon = { Icon(Icons.Filled.HelpCenter, "About") },
            selected = false, /* currentRoute == NavDestinations.HOME_ROUTE*/
            onClick = { },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        )
    }
}

@Composable
private fun ProjectNotesLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            painterResource(R.drawable.logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(72.dp)
        )
    }
}


//@Preview
//@Composable
//fun PreviewAppDrawer(){
//    AppDrawer(
//        isSyncActivated = false,
//        onSyncChecked = {},
//        closeDrawer = {}
//    )
//}