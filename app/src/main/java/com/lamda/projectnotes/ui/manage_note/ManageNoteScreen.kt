package com.lamda.projectnotes.ui.manage_note

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.ui.manage_note.components.ManageNoteCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNoteScreen(
    navController: NavController,
    viewModel: NoteManagementViewModel = hiltViewModel(),
) {

    val note = viewModel.noteState.value.note
    var isPinned by remember { mutableStateOf(false) }
    var noteFontSize by rememberSaveable { mutableStateOf(16) }
    isPinned = note.isPinned



    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "New Note") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (noteFontSize == 19) noteFontSize = 16
                        else noteFontSize++
                    }) {
                        Icon(
                            imageVector = Icons.Default.TextIncrease,
                            contentDescription = "Change text size"
                        )
                    }

                    IconButton(onClick = {
                        //TODO() Implement edit note screen
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Note"
                        )
                    }

                    IconButton(onClick = {
                        viewModel.onEvent(ManageNoteEvents.PinUnpinNote(note))
                        isPinned = !isPinned
                    }) {
                            Icon(
                                imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = "Pin/Unpin note"
                            )
                    }

                }
            )
        },
        bottomBar = {
        }
    ) { PaddingValues ->
        ManageNoteCard(
            note = note,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 32.dp,
                top = PaddingValues.calculateTopPadding() + 16.dp
            ),
            noteFontSize
        )
    }
}