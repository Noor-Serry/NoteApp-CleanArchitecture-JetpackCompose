package com.example.note.presentation.notes


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "NotesScreen"


 fun NavGraphBuilder.notesScreenRoute(navHostController: NavHostController) {
    this.composable(route = ROUTE) { NotesScreen(navController = navHostController) }
}

