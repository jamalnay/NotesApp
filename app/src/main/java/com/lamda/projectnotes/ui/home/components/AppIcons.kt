package com.lamda.projectnotes.ui.home.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.lamda.projectnotes.data.data_source.local.Model.Note


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


/* Not implemented because Material 3 Bottom sheets not implemented yet*/
@Composable
fun NoteOptions(
    note: Note,
    modifier: Modifier = Modifier,
) {

    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "Note Options",
            tint = MaterialTheme.colorScheme.primary
        )

    }
}


