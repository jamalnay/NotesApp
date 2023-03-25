package com.lamda.projectnotes.ui.manage_note

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.manage_note.components.ManageNoteCard
import kotlinx.coroutines.launch


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

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isActionsMenuExpanded by remember { mutableStateOf(false) }



    Scaffold(
        snackbarHost = {
            SnackbarHost(
            hostState = snackbarHostState
        )
                       },
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
                    IconButton(onClick = { isActionsMenuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Note actions")
                    }
                    DropdownMenu(
                        expanded = isActionsMenuExpanded,
                        onDismissRequest = { isActionsMenuExpanded = false }
                    )
                    {
                        DropdownMenuItem(
                            text = { Text("Edit note") },
                            onClick = {
                                      /*navController.navigate(AppDestinations.CreateUpdateNote.route + "?noteId=${note.noteId}")*/
                                        isActionsMenuExpanded = false
                                      },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Create,
                                    contentDescription = "edit note"
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Obscure note") },
                            onClick = {
                                      /* Handle edit! */
                                        isActionsMenuExpanded = false
                                      },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.VisibilityOff,
                                    contentDescription = "obscure note"
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Move to trash", color = MaterialTheme.colorScheme.error) },
                            onClick = {
                                viewModel.onEvent(ManageNoteEvents.MoveToTrash(note))
                                scope.launch {
                                    isActionsMenuExpanded = false
                                    //snackbar text needs to be adjusted in center
                                    snackbarHostState.showSnackbar(
                                        message = "Note Moved To Trash Successfully...",
                                        duration = SnackbarDuration.Short
                                    )
                                    navController.navigateUp()
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    tint = MaterialTheme.colorScheme.error,
                                    contentDescription = "move note to trash"
                                )
                            })

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(ManageNoteEvents.PinUnpinNote(note))
                        isPinned = !isPinned
                    }) {
                        Icon(
                            imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Pin/Unpin note"
                        )
                    }

                    IconButton(onClick = {
                        shareNote(note,context)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share")
                    }

                    IconButton(onClick = {
                        if (noteFontSize == 19) noteFontSize = 16
                        else noteFontSize++
                    }) {
                        Icon(
                            imageVector = Icons.Default.TextIncrease,
                            contentDescription = "Change text size"
                        )
                    }

                }
            )
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

fun shareNote(note: Note, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, note.noteTitle + "\n" + note.noteContent)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            "Share Note"
        )
    )
}
