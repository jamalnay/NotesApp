package com.lamda.projectnotes.ui.home.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.manage_note.ManageNoteEvents
import com.lamda.projectnotes.ui.manage_note.NoteManagementViewModel
import kotlinx.coroutines.launch


@Composable
fun PinNoteButton(
    isPinned: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val clickLabel = if (isPinned) "Unpin note" else "Pin note"

    IconToggleButton(
        checked = isPinned,
        onCheckedChange = { onClick() },
        modifier = modifier.semantics {
            this.onClick(label = clickLabel, action = null)
        }
    ) {
        Icon(
            modifier = modifier,
            imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

    }
}



