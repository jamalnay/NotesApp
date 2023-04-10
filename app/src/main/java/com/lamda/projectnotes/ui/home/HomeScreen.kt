package com.lamda.projectnotes.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.AppDrawer
import com.lamda.projectnotes.ui.home.components.CategoryChipGroup
import com.lamda.projectnotes.ui.home.components.NoteCard
import com.lamda.projectnotes.ui.home.components.PinnedNoteCard
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val pinnedNotes = viewModel.pinnedNotesState.value.listOfNotes.toList()
    val allNotes = viewModel.notesState.value.listOfNotes.toList()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val selectedCategory = viewModel.selectedCategoryState.value.category
    val allCategory = Category(-1, "All",0)

    var showPinnedNotes by rememberSaveable{ mutableStateOf(true) }
    showPinnedNotes =  selectedCategory == allCategory && pinnedNotes.isNotEmpty()

    var showRecentNotes by rememberSaveable{ mutableStateOf(true) }
    showRecentNotes = viewModel.notesState.value.listOfNotes.isNotEmpty()

    var otherCategory by rememberSaveable{ mutableStateOf(false) }
    otherCategory = selectedCategory != allCategory




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
                closeDrawer = { coroutineScope.launch { drawerState.close() } })
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
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = PaddingValues.calculateTopPadding() + 16.dp)
                ) {
                    if (!otherCategory && showPinnedNotes){
                        item() {
                            Row(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Icon(imageVector = Icons.Default.PushPin, contentDescription = "")
                                Text(text = "Pinned notes",Modifier.padding(8.dp,end = 16.dp))
                                Icon(imageVector = Icons.Default.East, contentDescription = "")
                            }
                            LazyRow(
                                Modifier
                                    .padding()
                                    .wrapContentSize()
                            ){
                                items(pinnedNotes){note ->
                                    Row(modifier = Modifier) {
                                        PinnedNoteCard(
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

                    if (!otherCategory){
                        item(){
                            Row(
                                modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                            ) {
                                Icon(imageVector = Icons.Default.History, contentDescription = "")
                                Text(text = "Recent notes",Modifier.padding(8.dp,end = 16.dp))
                            }
                        }
                    }
                    items(allNotes) { note ->
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



