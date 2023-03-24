package com.lamda.projectnotes.ui.manage_note

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.manage_note.components.ManageNoteCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNoteScreen(
    navController: NavController,
    viewModel: NoteManagementViewModel = hiltViewModel()
) {

    val note = viewModel.noteState.value.note
    var isPinned by remember { mutableStateOf(false) }
    //fontSize needs to be stateless and the font style should be changing
    // (bodysmall,bodymedium than body large )from an enum or som kind of state
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
            NavigationBar(
        ) {
            NavigationBarItem(
                modifier = Modifier.wrapContentSize(),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Move to trash",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                },
                icon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.error,
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Move note to trash")}
            )

            NavigationBarItem(
                modifier = Modifier.wrapContentSize(),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Obscure",
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Obscure")}
            )

            NavigationBarItem(
                modifier = Modifier.wrapContentSize(),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Share",
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share")}
            )
            FloatingActionButton(
                onClick = { /*navController.navigate(AppDestinations.CreateUpdateNote.route + "?noteId=${note.noteId}")*/ },
                modifier = Modifier.padding(8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Edit Note"
                    )
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            )
        } }
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
