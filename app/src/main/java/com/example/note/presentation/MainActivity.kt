package com.example.note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.example.note.presentation.theme.NoteTheme
import com.example.note.presentation.utils.NavGraph
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
              NoteTheme {
                  val uiController = rememberSystemUiController()
                  uiController.setSystemBarsColor(Color.White , darkIcons = true)
                  uiController.setNavigationBarColor(Color.White , darkIcons = true)
                  NavGraph()
              }

        }
    }
}

