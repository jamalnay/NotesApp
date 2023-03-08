package com.lamda.projectnotes.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamda.projectnotes.data.data_source.local.Model.Category
import com.lamda.projectnotes.presentation.home.MainEvents
import com.lamda.projectnotes.presentation.home.MainViewModel





@Composable
fun CategoryChipGroup(
    categoriesList: List<Category>,
    viewModel: MainViewModel,
    modifier: Modifier
){
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(24.dp)
    ){
        items(categoriesList){category ->
            CategoryChip(category = category,viewModel
                ,modifier = modifier,
                category == viewModel.notesState.value.selectedCategory
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category:Category,
    viewModel: MainViewModel,
    modifier: Modifier,
    selected: Boolean
) {
    FilterChip(
        modifier = modifier.padding(8.dp),
        label = { Text(text = category.catName) },
        onClick = { viewModel.onEvent(MainEvents.SelectCategory(category)) },
        selected = selected
    )
}

@Preview
@Composable
fun CategoryChipGroupPreview(
){
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(24.dp)
    ){
        items(listOf(
            Category(0,"All"),
            Category(1,"Sports"),
            Category(2,"Business"),
            Category(3,"Movies"),
            Category(4,"Home"),
            Category(5,"Work"),
            Category(6,"Life")
        ))
        { category ->
            CategoryChipPreview(category = category,true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChipPreview(
    category:Category,
    selected:Boolean
) {
    FilterChip(
        modifier = Modifier.padding(8.dp),
        label = { Text(text = category.catName) },
        onClick = {  },
        selected = selected
    )
}