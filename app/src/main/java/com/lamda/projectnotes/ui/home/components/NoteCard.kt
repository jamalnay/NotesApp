package com.lamda.projectnotes.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.utils.dateConverter


@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier,
    onPinToggled: (Note) -> Unit,
    isPinned: Boolean,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(note.noteColor)
        )
    ) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier
                    .padding(8.dp, top = 16.dp)
                    .weight(2f),
                text = note.noteTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            PinNoteButton(
                isPinned = note.isPinned,
                onClick = { onPinToggled(note) },
                modifier = Modifier.padding(top = 0.dp)
            )
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = note.noteContent,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier,
                text = note.noteCategoryName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier,
                text = dateConverter(note.creationTime),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )


        }
    }
}


@Preview
@Composable
fun PreviewNoteItem() {
    NoteCard(
        note = Note(
            5,
            "What lorem ipsum text is?",
            "Hello everyone this is " +
                    "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
                    "a placeholder text commonly used to demonstr " +
                    "ate the visual form of a document or a typeface",
            1677658911,
            Note.noteColors.random().toArgb(),
            false,
            0,
            "TestCategory"
        ),
        onPinToggled = {},
        isPinned = true,
        modifier = Modifier.wrapContentSize()
    )
}

@Preview
@Composable
fun PreviewTowNoteItem() {
    NoteCard(
        note = Note(
            5,
            "What lorem ipsum text is?",
            "Hello everyone this is " +
                    "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
                    "a placeholder text commonly used to demonstr " +
                    "ate the visual form of a document or a typeface",
            1677658911,
            Note.noteColors.random().toArgb(),
            true,
            0,
            "TestCategory"
        ),
        onPinToggled = {},
        isPinned = true,
        modifier = Modifier.wrapContentSize()
    )
}