package com.lamda.projectnotes.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.AppDrawer
import com.lamda.projectnotes.ui.home.components.NoteCard
import com.lamda.projectnotes.ui.home.components.PinnedNoteCard
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val categoriesList = viewModel.categoriesState.value.listOfCategories.toList()
    var currentCategory by remember { mutableStateOf(Category(-1, "All",0)) }


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

            Column(
                modifier = Modifier.padding(top = PaddingValues.calculateTopPadding())
            ) {
                LazyRow(
                    modifier = Modifier.padding(top = 16.dp,start = 16.dp, end = 16.dp, bottom = 0.dp),
                ){
                    items(categoriesList){category ->
                        FilterChip(
                            modifier = Modifier.padding(4.dp),
                            selected = category == currentCategory,
                            onClick = {
                                viewModel.onEvent(HomeEvents.SelectCategory(category))
                                currentCategory = category
                            },
                            label = { Text(text = category.catName,
                                modifier = Modifier.padding(
                                    start = 0.dp,end = 0.dp , top = 12.dp, bottom = 12.dp)) },
                            shape = RoundedCornerShape(15.dp),
                        )
                    }
                }
                HomeContent(viewModel,currentCategory,navController)
            }
        }
    }
}

@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    category: Category,
    navController: NavController
){
    val pinnedNotes = viewModel.pinnedNotesState.value.listOfNotes.toList()
    val allNotes = viewModel.notesState.value.listOfNotes.toList()

        if (category.catId == -1){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
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
                item(){
                    Row(
                        modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                    ) {
                        Icon(imageVector = Icons.Default.History, contentDescription = "")
                        Text(text = "Recent notes",Modifier.padding(8.dp,end = 16.dp))
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
        else{
            viewModel.onEvent(HomeEvents.SelectCategory(category))
            val notesForCategory = viewModel.notesForCategoryState.value.listOfNotes.toList()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                items(notesForCategory) { note ->
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





