package com.lamda.projectnotes.ui.create_update

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateUpdateScreen(
    navController: NavController,
    viewModel: CreateUpdateViewModel = hiltViewModel(),
) {
    var isCategoriesMenuExpanded by rememberSaveable { mutableStateOf(false) }
    val options = viewModel.categoriesState.value.listOfCategories
    var selectedOption by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var isPinned by rememberSaveable { mutableStateOf(false) }
    var categoryName by rememberSaveable { mutableStateOf("") }
    var categoryId by rememberSaveable { mutableStateOf(-1) }



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
                        if (categoryName != "" && title != "" && content != "") {
                            viewModel.onEvent(
                                CreateUpdateEvents.SaveNote(
                                    isPinned,
                                    title,
                                    content,
                                    categoryId,
                                    categoryName
                                )
                            )
                            navController.navigateUp()
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
                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                        modifier = Modifier
                            .menuAnchor()
                            .wrapContentWidth(),
                        readOnly = true,
                        value = selectedOption,
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
                                        selectedOption = selectionOption.catName
                                        categoryName = selectionOption.catName
                                        categoryId = selectionOption.catId!!
                                        isCategoriesMenuExpanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }

                        }
                    }
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                readOnly = false,
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 64.dp),
                readOnly = false,
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                maxLines = 20,
                textStyle = MaterialTheme.typography.bodyLarge
            )

        }

    }

}