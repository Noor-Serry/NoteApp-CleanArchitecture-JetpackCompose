package com.example.note.presentation.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.note.presentation.add_edit_screen.addEditScreenRoute
import com.example.note.presentation.notes.notesScreenRoute

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "NotesScreen"
    ) {

        notesScreenRoute(navHostController = navController)
        addEditScreenRoute(navHostController = navController)

    }
}


