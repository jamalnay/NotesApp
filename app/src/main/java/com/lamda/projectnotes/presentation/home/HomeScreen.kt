package com.lamda.projectnotes.presentation.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.data.data_source.local.Model.Note
import com.lamda.projectnotes.presentation.home.components.CategoryChipGroup



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
)
{
    val viewModel: MainViewModel = hiltViewModel()
    val categories = viewModel.categoriesState.value.listOfCategories
    val notes = viewModel.notesState.value.listOfNotes

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text(text = "My Notes")},
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Navigation menu")
                    }
                }
            )
        }

    ) {

        Column(modifier = Modifier.fillMaxSize().padding(it)) {
            CategoryChipGroup(
                categoriesList = categories,
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(Modifier.fillMaxSize()){
                items(notes){note ->
                    NoteCard(
                        note = note,
                        onPinToggled = {viewModel.onEvent(MainEvents.PinUnpinNote(note))},
                        isPinned = note.isPinned)
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}