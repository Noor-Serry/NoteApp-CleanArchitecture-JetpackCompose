package com.example.note.presentation.add_edit_screen

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.note.presentation.add_edit_screen.AddEditScreenArgs.Companion.NOTE_ID_ARGS


private const val ROUTE = "AddEditScreen"

fun NavGraphBuilder.addEditScreenRoute(navHostController: NavHostController){
    composable(ROUTE+"/{${NOTE_ID_ARGS}}", arguments = listOf( navArgument(NOTE_ID_ARGS){type  = NavType.IntType } )){ AddEditScreen(navHostController = navHostController)}
}

fun NavHostController.goToAddEditScreenScreen(noteId: Int) = navigate("$ROUTE/$noteId")


class AddEditScreenArgs(savedStateHandle: SavedStateHandle){
    val noteId = savedStateHandle.get<Int>(NOTE_ID_ARGS)

    companion object{
        const val NOTE_ID_ARGS = "noteId"
    }
}