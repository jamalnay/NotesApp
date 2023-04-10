package com.lamda.projectnotes.ui.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lamda.projectnotes.data.data_source.local.model.Category
import com.lamda.projectnotes.ui.AppDestinations
import com.lamda.projectnotes.ui.home.HomeEvents
import com.lamda.projectnotes.ui.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipGroup(
    viewModel: HomeViewModel,
    modifier: Modifier,
    navController: NavController
) {
    Row {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(viewModel.categoriesState.value.listOfCategories) { category ->
                CategoryChip(
                    category = category,
                    viewModel = viewModel,
                    modifier = modifier,
                    selected = category == viewModel.selectedCategoryState.value.category
                )
                if (category == viewModel.categoriesState.value.listOfCategories.last()){
                    FilterChip(
                        modifier = modifier.padding(4.dp),
                        selected = false,
                        onClick = { navController.navigate(AppDestinations.ManageCategories.route) },
                        label = { Text(text = "",modifier = Modifier.padding(
                            start = 0.dp,end = 0.dp , top = 12.dp, bottom = 12.dp)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.CreateNewFolder,
                                contentDescription = "Create New Category"
                            )
                        },
                        shape = RoundedCornerShape(15.dp)
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: Category,
    viewModel: HomeViewModel,
    modifier: Modifier,
    selected: Boolean,
) {

        FilterChip(
            modifier = modifier.padding(4.dp),
            label = { Text(text = category.catName,
                modifier = Modifier.padding(
                    start = 0.dp,end = 0.dp , top = 12.dp, bottom = 12.dp)) },
            onClick = { viewModel.onEvent(HomeEvents.SelectCategory(category)) },
            selected = selected,
            shape = RoundedCornerShape(15.dp),
            colors = FilterChipDefaults.filterChipColors(
                selectedLabelColor = MaterialTheme.colorScheme.background
            )
        )


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryDialogue(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {


}

//@Preview
//@Composable
//fun PreviewNewCategoryDialogue() {
////    NewCategoryDialogue()
//}


@Preview
@Composable
fun CategoryChipGroupPreview(
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        items(
            listOf(
                Category(0, "All"),
                Category(1, "Sports"),
                Category(2, "Business"),
                Category(3, "Movies"),
                Category(4, "Home"),
                Category(5, "Work"),
                Category(6, "Life")
            )
        )
        { category ->
            CategoryChipPreview(category = category, true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipPreview(
    category: Category,
    selected: Boolean,
) {
    FilterChip(
        modifier = Modifier.padding(8.dp),
        label = { Text(text = category.catName) },
        onClick = { },
        selected = selected
    )
}

