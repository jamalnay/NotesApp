package com.lamda.projectnotes.presentation

sealed class AppDestinations(val route: String) {
    object Home: AppDestinations("home")
    object ViewNote: AppDestinations("view_note")
    object CreateUpdateNote: AppDestinations("create_update_note")
    object ManageCategories: AppDestinations("manage_categories")
    object ManageDeleted: AppDestinations("manage_deleted")
}