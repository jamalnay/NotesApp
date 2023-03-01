package com.lamda.projectnotes.presentation.main.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics


@Composable
fun PinNoteButton(
    isPinned: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clickLabel = if (isPinned) "Unpin note" else "Pin note"

    IconToggleButton(
        checked = isPinned,
        onCheckedChange = {onClick()},
        modifier = modifier.semantics {
            this.onClick(label = clickLabel,action = null)
        }
                    ) {
        Icon(
            imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
            contentDescription = null)

    }
}