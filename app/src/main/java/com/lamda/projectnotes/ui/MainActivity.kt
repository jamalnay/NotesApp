package com.lamda.projectnotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lamda.projectnotes.ui.category.ManageCategories
import com.lamda.projectnotes.ui.create_update.CreateUpdateScreen
import com.lamda.projectnotes.ui.home.HomeScreen
import com.lamda.projectnotes.ui.manage_note.ManageNoteScreen
import com.lamda.projectnotes.ui.theme.ProjectNotesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectNotesTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppDestinations.Home.route
                )
                {
                    composable(route = AppDestinations.Home.route)
                    {
                        HomeScreen(navController = navController)
                    }
                    composable(
                        route = AppDestinations.ManageNote.route + "?noteId={noteId}",
                        arguments = listOf(navArgument(name = "noteId") {
                            type = NavType.IntType
                        }
                        )
                    ) {
                        ManageNoteScreen(navController = navController)
                    }
                    composable(
                        route = AppDestinations.CreateUpdateNote.route + "?noteId={noteId}",
                        arguments = listOf(navArgument(name = "noteId") {
                            type = NavType.IntType
                        }
                        )
                    ) {
                        CreateUpdateScreen(navController = navController)
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