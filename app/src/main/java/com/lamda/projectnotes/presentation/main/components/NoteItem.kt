package com.lamda.projectnotes.presentation.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.presentation.main.components.PinNoteButton

@Composable
fun NoteItem(
    note:Note,
    modifier: Modifier = Modifier,
    onPinToggled: (Int) -> Unit,
    isPinned: Boolean
){
    Box(
        modifier = modifier
    ){
        Canvas(modifier = Modifier.matchParentSize()){
            drawRoundRect(
                color = Color(note.noteColor),
                size = size,
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp))
        {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text( modifier = Modifier.padding(8.dp),
                    text = note.noteTitle,
                    style = MaterialTheme.typography.titleLarge)

                PinNoteButton(isPinned = note.isPinned, onClick = { onPinToggled(note.noteId) })

            }



        }
    }
}


