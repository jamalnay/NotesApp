package com.lamda.projectnotes.ui.manage_note


import androidx.compose.foundation.layout.*
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
import com.lamda.projectnotes.ui.manage_note.components.DeletedNoteCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageDeletedNotes(
    navController: NavController,
    viewModel: NoteManagementViewModel = hiltViewModel()
) {

    val notes = viewModel.deletedNotesState.value.listOfNotes
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var hideNotificationCard by remember { mutableStateOf(false) }


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

        Column(
            modifier = Modifier.padding(top = PaddingValues.calculateTopPadding())
        )
        {
            if (!hideNotificationCard) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row {
                        Box(modifier = Modifier.weight(5f)) {
                            Row {
                                Icon(
                                    imageVector = Icons.Default.Report,
                                    contentDescription = null,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        top = 16.dp,
                                        end = 4.dp,
                                        bottom = 16.dp
                                    )
                                )
                                Text(
                                    text = "Notes in Trash will be deleted after 30 days.",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(start = 4.dp,top = 20.dp, end = 8.dp, bottom = 16.dp)
                                )
                            }
                        }

                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { hideNotificationCard = true }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }
                }
            }
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
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
}