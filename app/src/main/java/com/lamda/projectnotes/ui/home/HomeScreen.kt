package com.lamda.projectnotes.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.AppDrawer
import com.lamda.projectnotes.ui.home.components.CategoryChipGroup
import com.lamda.projectnotes.ui.home.components.NoteCard
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val notes = viewModel.notesState.value.listOfNotes.toList()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)


    /* TODO() */
    /*   When "swip to dismiss Navigation drawer" gesture is enabled it overlaps
         with the gesture of navgating the categories,when it is disabled, its a bit hard for the user
         to dismiss the drawer unless he clicks on one of the drawer items, this will produce a bad
         experience for the user this needs a solution
         Another problem that needs to be solve is pressing the back button when the drawer is open,
         pressing the back button must close the drawer not the App
         */

    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController = navController,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                isSyncActivated = true /* TODO */,
                onSyncChecked = { /*TODO*/ })
        }) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp)
                        ),
                        title = { Text(text = "My Notes") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        if (drawerState.isOpen) drawerState.close() else drawerState.open()
                                    }
                                }
                            ) {
                                /* TODO() navigation drawer icon needs 360 rotation animation */
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Navigation menu"
                                )
                            }
                        }
                    )
                    CategoryChipGroup(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "New Note") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = "Create a new Note"
                        )
                    },
                    onClick = { navController.navigate(AppDestinations.CreateUpdateNote.route + "?noteId=${0}") },
                    modifier = Modifier.padding(8.dp)
                )
            }
        )
        { PaddingValues ->

            LazyColumn(
                /* TODO()
                The padding values calculations here are temporarily,
                i need to find a solution for the categories to stick with the TopBar  */
                Modifier
                    .fillMaxSize()
                    .padding(top = PaddingValues.calculateTopPadding())
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        modifier = Modifier.clickable {
                            navController.navigate(AppDestinations.ManageNote.route + "?noteId=${note.noteId}")
                        }
                    )
                }
            }
        }
    }
}



