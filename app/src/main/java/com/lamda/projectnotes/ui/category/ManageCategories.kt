package com.lamda.projectnotes.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.home.HomeEvents
import com.lamda.projectnotes.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageCategories(
    navController: NavController,
    viewModel: ManageCategoriesViewModel = hiltViewModel()
) {

    val categories = viewModel.categoriesState.value.listOfCategories

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }

    var hideNotificationCard by remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Manage Categories") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { PaddingValues ->

        Column(
            modifier = Modifier.padding(top = PaddingValues.calculateTopPadding())
        )
        {
            LazyColumn(
                Modifier
                    .wrapContentSize()
                    .padding(top = 16.dp)
            ) {
                items(categories) { category ->
                    if (category.catId != -1)
                        CategoryNoteCard(category = category,viewModel,scope,snackbarHostState)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 24.dp)
                    .clickable {
                        showDialog = true
                    },
                //color should not be hard coded here
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEDEDEF))

            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "create new category",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 4.dp)
                )

                Text(
                    text = "Create a new Category",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, bottom = 20.dp, top = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        if (showDialog) {
            AlertDialog(
                iconContentColor = MaterialTheme.colorScheme.primary,
                onDismissRequest = {
                                        showDialog = false
                                        text = ""
                                   },
                confirmButton = {
                    TextButton(onClick = {
                        if (text.isNotEmpty())
                            viewModel.onEvent(CategoryEvents.CreateCategory(text))
                        showDialog = false
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
                        showDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                },
                containerColor = Color.White,
                tonalElevation = 0.dp
            )

        }


    }
}

@Composable
fun CategoryNoteCard(
    category:Category,
    viewModel: ManageCategoriesViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 8.dp),

        //color should not be hard coded here
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDEDEF))


    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(text = category.catName, modifier = Modifier)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(category.notesCount.toString())
                CategoryOptions(
                    category = category,
                    viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }

        }
    }

}

@Composable
fun CategoryOptions(
    category: Category,
    viewModel:ManageCategoriesViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    var isActionsMenuExpanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = {isActionsMenuExpanded = true}
    ) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "Note Options",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    DropdownMenu(
        expanded = isActionsMenuExpanded,
        onDismissRequest = { isActionsMenuExpanded = false }
    )
    {
        DropdownMenuItem(
            text = { Text("Rename Category") },
            onClick = {
                //TODO()
                isActionsMenuExpanded = false
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Create,
                    contentDescription = "Rename Category"
                )
            })
        DropdownMenuItem(
            text = { Text("Delete Category", color = MaterialTheme.colorScheme.error) },
            onClick = {
                viewModel.onEvent(CategoryEvents.DeleteCategory(category))
                isActionsMenuExpanded = false
                scope.launch {
                    //snackbar text needs to be adjusted in center
                    snackbarHostState.showSnackbar("Category Deleted.")
                }
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "delete category"
                )
            })
    }
}


