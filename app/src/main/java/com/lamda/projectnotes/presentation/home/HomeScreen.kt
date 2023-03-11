package com.lamda.projectnotes.presentation.home
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lamda.projectnotes.presentation.AppDrawer
import com.lamda.projectnotes.presentation.home.components.CategoryChipGroup
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
)
{
    val viewModel: MainViewModel = hiltViewModel()
    val categories = viewModel.categoriesState.value.listOfCategories
    val notes = viewModel.notesState.value.listOfNotes
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        /* TODO()
         when "swip to dismiss Navigatio drawer" gesture is enabled it overlaps
         with the gesture of navgating the categories,when it is disabled, its a bit hard for the user
          to dismiss the drawer unless he clicks on one of the drawer items, this will produce a bad
          experience for the user this needs a solution
          Another problem that needs to be solve is pressing the back button when the drawer is open,
          pressing the back button must close the drawer not the App
          */
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navigateToCategories = { /*TODO*/ },
                navigateToTrash = { /*TODO*/ },
                closeDrawer = { coroutineScope.launch{ drawerState.close()} },
                isSyncActivated = true /* TODO */,
                onSyncChecked = { /*TODO*/ })
        }){
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {Text(text = "My Notes")},
                    navigationIcon = {
                        IconButton(
                            onClick = { coroutineScope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } }
                        ){
                            /* TODO() navigation drawer icon needs rotate animation */
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Navigation menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text(text = "New Note") },
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = "Create a new Note") },
                    onClick = { /* TODO() */ },
                    modifier = Modifier.padding(8.dp)
                )
            }
        )
        {PaddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = PaddingValues.calculateTopPadding(), bottom = 56.dp)) {
                CategoryChipGroup(
                    categoriesList = categories,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            LazyColumn(
                /* TODO()
                The padding values calculations here are temporarily,
                i need to find a solution for the categories to stick with the TopBar  */
                Modifier
                    .fillMaxSize()
                    .padding(top = PaddingValues.calculateTopPadding() + 56.dp)
            ){
                items(notes){note ->
                    NoteCard(
                        note = note,
                        onPinToggled = {viewModel.onEvent(MainEvents.PinUnpinNote(note))},
                        isPinned = note.isPinned)
                }
            }
        }
    }
}
