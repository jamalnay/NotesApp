package com.lamda.projectnotes.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import com.lamda.projectnotes.data.data_source.local.model.Note
import com.lamda.projectnotes.ui.utils.dateConverter


@Composable
fun NoteCard(
    note: Note,
    modifier: Modifier = Modifier
) {

    //making the number of maxLines dynamic will give the home screen a bit of a dynamic look
    val maxLines = when (note.noteContent.length) {
        in 0..250 -> 1
        in 250..300 -> 2
        in 300..350 -> 3
        else -> 4
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {

        Text(
            modifier = Modifier
                .padding(start = 16.dp,end = 16.dp, bottom = 2.dp, top = 16.dp),
            text = note.noteTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(start = 16.dp,end = 24.dp, bottom = 12.dp, top = 12.dp)
        )

//            PinNoteButton(
//                isPinned = note.isPinned,
//                onClick = { onPinToggled(note) },
//                modifier = Modifier.padding(top = 0.dp)
//            )

        Text(
            modifier = Modifier.padding(start = 16.dp,end = 16.dp, bottom = 12.dp, top = 2.dp),
            text = note.noteContent,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                modifier = Modifier,
                text = note.categoryName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.weight(2f),
                text = dateConverter(note.creationTime),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
            )


        }
    }
}

@Composable
fun PinnedNoteCard(
    note: Note,
    modifier: Modifier = Modifier
){
        Card(
            modifier = modifier.size(200.dp,260.dp)
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 16.dp),
            shape = RoundedCornerShape(20.dp)
        )
        {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp,end = 16.dp, bottom = 2.dp, top = 16.dp),
                text = note.noteTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 16.dp,end = 24.dp, bottom = 8.dp, top = 8.dp)
            )
            Text(
                modifier = Modifier.padding(start = 16.dp,end = 16.dp, bottom = 12.dp, top = 2.dp),
                text = note.noteContent,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
        }

}
