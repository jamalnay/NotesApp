package com.lamda.projectnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lamda.projectnotes.presentation.home.HomeScreen
import com.lamda.projectnotes.ui.theme.ProjectNotesTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectNotesTheme {
                HomeScreen ()
            }
        }
    }


}