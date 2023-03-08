package com.lamda.projectnotes.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.presentation.home.components.NoteOptions
import com.lamda.projectnotes.presentation.home.components.PinNoteButton
import com.lamda.projectnotes.presentation.home.utils.dateConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    note:Note,
    modifier: Modifier = Modifier,
    onPinToggled: (Note) -> Unit,
    isPinned: Boolean
){
    Card (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(note.noteColor)
            )
    ){


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text( modifier = Modifier.padding(8.dp,top = 16.dp),
                    text = note.noteTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary)

                PinNoteButton(
                    isPinned = note.isPinned,
                    onClick = { onPinToggled(note) },
                    modifier = Modifier.padding(top = 0.dp))
            }

            Text(
                modifier = modifier.padding(8.dp),
                text = note.noteContent,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text( modifier = Modifier
                    .padding(8.dp, top = 14.dp)
                    .weight(2f),
                    text = "Business",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)

                Text(modifier = Modifier.padding(top = 14.dp),
                    text = dateConverter(note.creationTime),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary)

                NoteOptions(note,modifier.padding(0.dp))
            }
    }
}


@Preview
@Composable
fun PreviewNoteItem(){
NoteCard(note = Note(5,"What lorem ipsum text is?","Hello everyone this is " +
        "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
        "a placeholder text commonly used to demonstr " +
        "ate the visual form of a document or a typeface",1677658911,Note.noteColors.random().toArgb(),
    false,0),
    onPinToggled = {},
    isPinned = true,
    modifier = Modifier.wrapContentSize())
}

@Preview
@Composable
fun PreviewTowNoteItem(){
    NoteCard(note = Note(5,"What lorem ipsum text is?","Hello everyone this is " +
            "my first note, happy to meet you all, Thank you. In publishing and graphic design, Lorem ipsum is " +
            "a placeholder text commonly used to demonstr " +
            "ate the visual form of a document or a typeface",1677658911,Note.noteColors.random().toArgb(),
        true,0),
        onPinToggled = {},
        isPinned = true,
        modifier = Modifier.wrapContentSize())
}