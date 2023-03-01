package com.lamda.projectnotes.presentation

sealed class ScreenNav(val route:String){
    object NotesScreen: ScreenNav("main")
    object ViewNoteScreen: ScreenNav("view_note")
    object EditNoteScreen: ScreenNav("edit_note")
    object EditCategories: ScreenNav("edit_cats")
}
