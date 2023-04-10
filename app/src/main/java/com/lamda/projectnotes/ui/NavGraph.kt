package com.lamda.projectnotes.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType

import com.google.accompanist.navigation.animation.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.lamda.projectnotes.ui.category.ManageCategories
import com.lamda.projectnotes.ui.create_update.CreateUpdateScreen
import com.lamda.projectnotes.ui.home.HomeScreen
import com.lamda.projectnotes.ui.manage_note.ManageDeletedNotes
import com.lamda.projectnotes.ui.manage_note.ManageNoteScreen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph (
    navController: NavHostController
){
    AnimatedNavHost(
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
            ),
            enterTransition = { scaleIn(tween(300)) },
            exitTransition = { slideOutHorizontally (tween(400))}
        ) {
            ManageNoteScreen(navController = navController)
        }
        composable(
            route = AppDestinations.CreateUpdateNote.route + "?noteId={noteId}",
            arguments = listOf(navArgument(name = "noteId") {
                type = NavType.IntType
            }
            ),
            enterTransition = { scaleIn(tween(300)) },
            exitTransition = { slideOutHorizontally (tween(400))}
        ) {
            CreateUpdateScreen(navController = navController)
        }
        composable(
            route = AppDestinations.ManageCategories.route,
            enterTransition = { slideInHorizontally(tween(300)) },
            exitTransition = { slideOutHorizontally(tween(400))}
        )
        {
            ManageCategories(navController = navController)
        }
        composable(
            route = AppDestinations.ManageDeleted.route,
            enterTransition = { slideInHorizontally(tween(300)) },
            exitTransition = { slideOutHorizontally(tween(400))}
        )
        {
            ManageDeletedNotes(navController = navController)
        }
    }
}
