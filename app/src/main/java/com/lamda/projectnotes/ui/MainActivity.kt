package com.lamda.projectnotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lamda.projectnotes.ui.theme.ProjectNotesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity () {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProjectNotesTheme {
                val navController: NavHostController = rememberAnimatedNavController()
                NavGraph(navController = navController)
            }
        }
    }


}