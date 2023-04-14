package com.lamda.projectnotes.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.AppDrawer
import com.lamda.projectnotes.ui.home.components.NoteCard
import com.lamda.projectnotes.ui.home.components.PinnedNoteCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val allCategory = viewModel.allCategory

    val categoriesList = viewModel.categoriesState.value.listOfCategories.toList()
    var currentCategory by remember { mutableStateOf(allCategory) }


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
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                        title = { Text(text = "My notes")},
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
                        },
                        scrollBehavior = scrollBehavior
                    )
                    LazyRow(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 0.dp
                        ),
                    ) {
                        items(categoriesList) { category ->
                            FilterChip(
                                modifier = Modifier.padding(4.dp),
                                selected = category == currentCategory,
                                onClick = {
                                    viewModel.onEvent(HomeEvents.SelectCategory(category))
                                    currentCategory = category
                                },
                                label = {
                                    Text(
                                        text = category.catName,
                                        modifier = Modifier.padding(
                                            start = 0.dp, end = 0.dp, top = 12.dp, bottom = 12.dp
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(15.dp),
                            )
                        }
                    }
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

            BackHandler(enabled = currentCategory.catId != allCategory.catId) {
                //goes back to All category
                currentCategory = allCategory
                viewModel.onEvent(HomeEvents.SelectCategory(allCategory))
            }
            HomeContent(
                viewModel,
                currentCategory,
                navController,
                drawerState,
                coroutineScope,
                Modifier.padding(top = PaddingValues.calculateTopPadding())
            )



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewModel: HomeViewModel,
    category: Category,
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {
    val notesState = viewModel.notesState.value.listOfNotes.toList()
    var pinnedExist by remember {mutableStateOf(false)}

    var showToast by remember { mutableStateOf(false) }
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }
    val context = LocalContext.current

    if(drawerState.isOpen){
        BackHandler(enabled = true) {
            coroutineScope.launch { drawerState.close() }
        }
    }

    if (category.catId == -1){
        if(showToast){
            Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
            showToast= false
        }

        LaunchedEffect(key1 = backPressState) {
            if (backPressState == BackPress.InitialTouch) {
                delay(2000)
                backPressState = BackPress.Idle
            }
        }

        BackHandler(backPressState == BackPress.Idle) {
            backPressState = BackPress.InitialTouch
            showToast = true
        }
    }


    Crossfade(
        modifier = modifier,
        targetState = notesState,
        animationSpec = tween(durationMillis = 500, delayMillis = 0, easing = LinearOutSlowInEasing)
    ) { notes ->
        //check for pinned notes
        notes.forEach {
            if (it.isPinned) {
                pinnedExist = true
                return@forEach
            }
        }
        if (category.catId == -1) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                item {
                    if (pinnedExist) {
                        Row(
                            modifier = Modifier.padding(16.dp,top = 8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.PushPin, contentDescription = "")
                            Text(text = "Pinned notes", Modifier.padding(8.dp, end = 16.dp))
                        }
                    }
                    LazyRow(modifier = Modifier) {
                        items(notes) { note ->
                            if (note.isPinned) {
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
                if (notes.isNotEmpty())
                {
                    item {
                        Row(
                            modifier = Modifier.padding(start = 16.dp, top = 24.dp)
                        ) {
                            Icon(imageVector = Icons.Default.History, contentDescription = "")
                            Text(text = "Recent notes", Modifier.padding(8.dp, end = 16.dp))
                        }
                    }
                }
                items(notes) { note ->
                    if (!note.isPinned) {
                        NoteCard(
                            note = note,
                            modifier = Modifier.clickable {
                                navController.navigate(AppDestinations.ManageNote.route + "?noteId=${note.noteId}")
                            }
                        )
                    }
                }
            }

        } else {
            viewModel.onEvent(HomeEvents.SelectCategory(category))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
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




