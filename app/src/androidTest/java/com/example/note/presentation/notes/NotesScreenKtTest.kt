package com.example.note.presentation.notes


import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.note.R
import com.example.note.di.LocalDB
import com.example.note.presentation.MainActivity
import com.example.note.presentation.theme.NoteTheme
import com.example.note.presentation.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(LocalDB::class)
class NotesScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composerRule = createAndroidComposeRule<MainActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val getString = {id : Int -> context.getString(id)}

    @Before
    fun setup() {
        hiltRule.inject()

        composerRule.activity.setContent {
            val navController = rememberNavController()
            NoteTheme() {
                NavHost(navController = navController, startDestination = "NotesScreen") {
                    notesScreenRoute(navHostController = navController)
                }
            }

        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        with(composerRule) {
            onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
            onNodeWithContentDescription(getString(R.string.toggleDescription))
                .performClick()
            onNodeWithTag(TestTags.ORDER_SECTION).assertExists()
        }
    }

    @Test
    fun doubleClickToggleOrderSection_isInVisible() {
        with(composerRule) {
            onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
            val toggleButton =
                onNodeWithContentDescription(getString(R.string.toggleDescription))
            toggleButton.performClick()
            toggleButton.performClick()
            onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        }
    }


}