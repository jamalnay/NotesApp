package com.lamda.projectnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lamda.projectnotes.presentation.home.HomeScreen
import com.lamda.projectnotes.ui.theme.ProjectNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import com.lamda.projectnotes.presentation.home.ManageCategories
import com.lamda.projectnotes.presentation.note.CreateUpdate
import com.lamda.projectnotes.presentation.note.NoteScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        setContent {
            ProjectNotesTheme {
                    val navController: NavHostController = rememberNavController()
                    NavHost(navController = navController, startDestination = AppDestinations.Home.route)
                    {
                        composable(route = AppDestinations.Home.route)
                        {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = AppDestinations.ViewNote.route+"noteId?={noteId}",
                            arguments = listOf(navArgument(name = "noteId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                            )
                        ){
                            NoteScreen(navController = navController)
                        }
                        composable(
                            route = AppDestinations.CreateUpdateNote.route+"noteId?={noteId}",
                            arguments = listOf(navArgument(name = "noteId"){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                            )
                        ){
                            CreateUpdate(navController = navController)
                        }
                        composable(route = AppDestinations.ManageCategories.route)
                        {
                            ManageCategories(navController = navController)
                        }
                        composable(route = AppDestinations.ManageDeleted.route)
                        {
                            DeletedNotes(navController = navController)
                        }
                    }

            }
        }
    }


}