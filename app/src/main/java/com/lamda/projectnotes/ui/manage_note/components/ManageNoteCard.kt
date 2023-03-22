package com.lamda.projectnotes.ui.manage_note.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.ui.utils.dateConverter


@Composable
fun ManageNoteCard(
    note: Note,
    modifier: Modifier,
    noteFontSize: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(note.noteColor)
        )
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
                text = note.noteCategoryName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = note.noteContent,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
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
                color = MaterialTheme.colorScheme.primary,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light
            )
            Text(
                modifier = Modifier.padding(start = 0.dp, bottom = 16.dp, top = 8.dp, end = 8.dp),
                text = dateConverter(note.creationTime),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )


        }
    }
}


@Preview
@Composable
fun PreviewNoteItem() {
    ManageNoteCard(
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
        modifier = Modifier.wrapContentSize(), 20
    )
}
