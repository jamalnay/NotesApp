package com.lamda.projectnotes.ui.manage_note


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.ui.manage_note.NoteManagementViewModel
import com.lamda.projectnotes.ui.manage_note.components.DeletedNoteCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletedNotes(
    navController: NavController,
    viewModel: NoteManagementViewModel = hiltViewModel()
) {

    val notes = viewModel.deletedNotesState.value.listOfNotes
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Deleted Notes") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { PaddingValues ->
        LazyColumn(
            /* TODO()
            The padding values calculations here are temporarily,
            i need to find a solution for the categories to stick with the TopBar  */
            Modifier
                .fillMaxSize()
                .padding(top = PaddingValues.calculateTopPadding())
        ) {
            items(notes) { note ->
                DeletedNoteCard(
                    note = note,
                    modifier = Modifier.padding(16.dp),
                    viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}