package com.lamda.projectnotes.ui.manage_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.manage_note.ManageNoteEvents
import com.lamda.projectnotes.ui.manage_note.NoteManagementViewModel
import com.lamda.projectnotes.ui.utils.dateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ManageNoteCard(
    note: Note,
    modifier: Modifier,
    noteFontSize: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier
                    .padding(8.dp, top = 16.dp)
                    .fillMaxWidth(),
                text = note.categoryName,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = note.noteTitle,
                style = MaterialTheme.typography.titleMedium
            )

        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = note.noteContent,
            style = MaterialTheme.typography.bodyMedium,
            softWrap = true,
            fontSize = noteFontSize.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        )
        {
            Text(
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp, top = 8.dp, end = 0.dp),
                text = "Last Modified: ",
                style = MaterialTheme.typography.labelLarge,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light
            )
            Text(
                modifier = Modifier.padding(start = 0.dp, bottom = 16.dp, top = 8.dp, end = 8.dp),
                text = dateConverter(note.creationTime),
                style = MaterialTheme.typography.labelLarge
            )


        }
    }
}

@Composable
fun DeletedNoteCard(
    note: Note,
    viewModel: NoteManagementViewModel,
    modifier: Modifier,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp,end = 16.dp, bottom = 4.dp, top = 16.dp),
                text = note.categoryName,
                style = MaterialTheme.typography.labelLarge
            )
            Box {
                DeletedNoteOptions(
                    note = note,
                    viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }

        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp,end = 16.dp, bottom = 8.dp, top = 4.dp)
                .fillMaxWidth(),
            text = note.noteTitle,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            modifier = Modifier.padding(start = 16.dp,end = 16.dp, bottom = 16.dp, top = 8.dp),
            text = note.noteContent,
            style = MaterialTheme.typography.bodyMedium,
            softWrap = true,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DeletedNoteOptions(
    note: Note,
    viewModel:NoteManagementViewModel,
    scope:CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    var isActionsMenuExpanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = {isActionsMenuExpanded = true}
    ) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "Note Options"
        )
    }

    DropdownMenu(
        expanded = isActionsMenuExpanded,
        onDismissRequest = { isActionsMenuExpanded = false }
    )
    {
        DropdownMenuItem(
            text = { Text("Restore note") },
            onClick = {
                viewModel.onEvent(manageNoteEvents = ManageNoteEvents.RestoreNote(note))
                isActionsMenuExpanded = false
                scope.launch {
                    //snackbar text needs to be adjusted in center
                    snackbarHostState.showSnackbar("Note Restored.")
                }
                      },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Unarchive,
                    contentDescription = "restore note"
                )
            })
        DropdownMenuItem(
            text = { Text("Delete permanently", color = MaterialTheme.colorScheme.error) },
            onClick = {
                viewModel.onEvent(manageNoteEvents = ManageNoteEvents.DeleteNote(note))
                isActionsMenuExpanded = false
                scope.launch {
                    //snackbar text needs to be adjusted in center
                    snackbarHostState.showSnackbar("Note Deleted.")
                }

            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "Delete note permanently",
                    tint = MaterialTheme.colorScheme.error
                )
            })
    }
}
