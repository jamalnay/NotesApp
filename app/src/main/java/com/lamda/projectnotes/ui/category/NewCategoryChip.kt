package com.lamda.projectnotes.ui.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.home.HomeEvents
import com.lamda.projectnotes.ui.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryChip(
    viewModel: HomeViewModel,
    modifier: Modifier,
) {
    var showNewCategoryDialog by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }

    FilterChip(
        modifier = modifier.padding(4.dp),
        selected = false,
        onClick = { showNewCategoryDialog = !showNewCategoryDialog },
        label = { "" },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CreateNewFolder,
                contentDescription = "Create New Category"
            )
        }
    )

    if (showNewCategoryDialog) {
        AlertDialog(
            iconContentColor = MaterialTheme.colorScheme.primary,
            onDismissRequest = { showNewCategoryDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (text.isNotEmpty())
                        viewModel.onEvent(HomeEvents.CreateCategory(Category(0, text)))
                    showNewCategoryDialog = false
                }) {
                    Text(text = "Confirm")
                }

            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                securePolicy = SecureFlagPolicy.Inherit
            ),
            icon = { Icon(imageVector = Icons.Default.CreateNewFolder, contentDescription = null) },
            title = { Text(text = "Create New Category") },
            text = {
                TextField(
                    value = text, onValueChange = { text = it },
                    label = { Text(text = "Category Name") },
                    singleLine = true,
                )
            },
            dismissButton = {
                TextButton(onClick = {
                    showNewCategoryDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            containerColor = Color.White,
            tonalElevation = 0.dp
        )

    }
}