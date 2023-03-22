package com.lamda.projectnotes.ui

sealed class AppDestinations(val route: String) {
    object Home : AppDestinations("home")
    object ManageNote : AppDestinations("manage_note_screen")
    object CreateUpdateNote : AppDestinations("create_update_screen")
    object ManageCategories : AppDestinations("manage_categories")
    object ManageDeleted : AppDestinations("manage_deleted")
}