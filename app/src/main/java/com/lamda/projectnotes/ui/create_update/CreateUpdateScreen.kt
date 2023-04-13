package com.lamda.projectnotes.ui.create_update

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.ui.AppDestinations


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateScreen(
    navController: NavController,
    viewModel: CreateUpdateViewModel = hiltViewModel(),
) {
    var isCategoriesMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val options = viewModel.categoriesState.value.listOfCategories
    var isPinned by rememberSaveable { mutableStateOf(false) }

    val title = viewModel.noteTitle.value
    val content = viewModel.noteContent.value
    val category = viewModel.selectedCategoryState.value.category

    val currentNoteId = viewModel.currentNoteId





    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "New Note") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isPinned = !isPinned

                    }) {
                        if (!isPinned) {
                            Icon(
                                imageVector = if (isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                contentDescription = "Pin note"
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Unpin note"
                            )
                        }
                    }
                    IconButton(onClick = {
                        if (title.text.isNotEmpty() && content.text.isNotEmpty()) {
                            viewModel.onEvent(
                                CreateUpdateEvents.SaveNote(
                                    isPinned,
                                    title.text,
                                    content.text,
                                    category.catId!!,
                                    category.catName
                                )
                            )
                            if (currentNoteId == 0) navController.navigateUp()
                            else navController.navigate(AppDestinations.ManageNote.route + "?noteId=${viewModel.currentNoteId}")

                        }
                    }
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save note"
                        )
                    }
                }
            )
        }
    ) { PaddingValues ->

        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = PaddingValues.calculateTopPadding(),
                    bottom = 16.dp
                )
                .fillMaxWidth()
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                ExposedDropdownMenuBox(
                    expanded = isCategoriesMenuExpanded,
                    onExpandedChange = { isCategoriesMenuExpanded = !isCategoriesMenuExpanded }
                )
                {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .wrapContentWidth(),
                        readOnly = true,
                        value = category.catName,
                        onValueChange = {},
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoriesMenuExpanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.wrapContentWidth(),
                        expanded = isCategoriesMenuExpanded,
                        onDismissRequest = { isCategoriesMenuExpanded = false }
                    ) {
                        options.forEach { selectionOption ->
                            //here i needed to make sure that "All" is not showing in categories list
                            if (selectionOption.catId != -1) {
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = { Text(selectionOption.catName) },
                                    onClick = {
                                        viewModel.onEvent(CreateUpdateEvents.SelectCategory(selectionOption))
                                        isCategoriesMenuExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }

                        }
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                ,
                readOnly = false,
                value = title.text,
                onValueChange = {
                    viewModel.onEvent(CreateUpdateEvents.EnteredTitle(it))
                                },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(text = "Title..") }
            )

            TextField(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 16.dp, bottom = 64.dp),
                readOnly = false,
                value = content.text,
                onValueChange = {
                    viewModel.onEvent(CreateUpdateEvents.EnteredContent(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = { Text(text = "Content..") },
            )

        }

    }

}