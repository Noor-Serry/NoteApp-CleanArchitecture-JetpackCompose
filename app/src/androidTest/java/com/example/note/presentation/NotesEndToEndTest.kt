package com.example.note.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.note.di.LocalDB
import com.example.note.R
import com.example.note.presentation.add_edit_screen.addEditScreenRoute
import com.example.note.presentation.notes.notesScreenRoute
import com.example.note.presentation.theme.NoteTheme
import com.example.note.presentation.utils.TestTags
import com.example.note.presentation.utils.TestTags.BODY_TEXT_FIELD
import com.example.note.presentation.utils.TestTags.TITlE_TEXT_FIELD
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test



@HiltAndroidTest
@UninstallModules(LocalDB::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composerRule = createAndroidComposeRule<MainActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val getString = { id: Int -> context.getString(id) }

    @Before
    fun setup() {
        hiltRule.inject()
        composerRule.activity.setContent {
            val navController = rememberNavController()
            NoteTheme() {
                NavHost(navController = navController, startDestination = "NotesScreen") {
                    notesScreenRoute(navHostController = navController)
                    addEditScreenRoute(navHostController = navController)
                }
            }

        }

    }

    @Test
    fun saveNewNote_editAfterwards() {

        with(composerRule) {
            //save new note
            onNodeWithContentDescription(getString(R.string.addButtonDescription)).performClick()
            onNodeWithTag(TITlE_TEXT_FIELD).performTextInput("TITlE_TEXT_FIELD")
            onNodeWithTag(BODY_TEXT_FIELD).performTextInput("BODY_TEXT_FIELD")
            onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()
            // show note in lazy click to edit it
            onNodeWithText("TITlE_TEXT_FIELD").assertIsDisplayed()
            onNodeWithText("TITlE_TEXT_FIELD").performClick()
            //assert if second screen have expected title and body
            onNodeWithText("TITlE_TEXT_FIELD").assertIsDisplayed()
            onNodeWithText("BODY_TEXT_FIELD").assertIsDisplayed()
            // make the title empty and  click save to show toast with error text
            onNodeWithTag(TITlE_TEXT_FIELD).performTextClearance()
            onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()
            onRoot().fetchSemanticsNode("can't save empty note")
            //edit title and clack save
            onNodeWithTag(TITlE_TEXT_FIELD).performTextInput("title after changed")
            onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()

            onNodeWithText("title after changed").assertIsDisplayed()
        }
    }

    @Test
    fun saveNote_deleteAfterwards() {
        with(composerRule) {
            //save new note
            (0..1).forEach {
                onNodeWithContentDescription(getString(R.string.addButtonDescription)).performClick()
                onNodeWithTag(TITlE_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithTag(BODY_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()
            }
            // show note in lazy click to edit it
            onNodeWithText("0").assertIsDisplayed()
            //delete first note
            onAllNodesWithContentDescription(getString(R.string.deleteButtonDescription))[0].performClick()
            onNodeWithText("0").assertIsNotDisplayed()

        }
    }

    @Test
    fun saveNotes_orderByTimeMillisAscending() {
        //add notes
        with(composerRule) {
            (0..3).forEach {
                onNodeWithContentDescription(getString(R.string.addButtonDescription)).performClick()
                onNodeWithTag(TITlE_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithTag(BODY_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()
            }
            // assert if all nodes displayed
            (0..3).forEach {
                onNodeWithText(it.toString()).assertIsDisplayed()
                onNodeWithText(it.toString()).assertIsDisplayed()
            }
            // select Ascending order
            onNodeWithContentDescription(getString(R.string.toggleDescription)).performClick()
            onNodeWithTag(TestTags.ASCENDING_BUTTON).performClick()
            onNodeWithContentDescription(getString(R.string.toggleDescription)).performClick()
            // get all notes after sorted
            val notes = onAllNodesWithTag(TestTags.NOTE)
            // assert if sorted ascending successfully
            (0..3).forEach {
                notes[it].assertTextContains(it.toString())
            }
        }
    }

    @Test
    fun saveNotes_orderByTimeMillisDescending() {
        //add notes
        with(composerRule) {
            (0..3).forEach {
                onNodeWithContentDescription(getString(R.string.addButtonDescription)).performClick()
                onNodeWithTag(TITlE_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithTag(BODY_TEXT_FIELD).performTextInput(it.toString())
                onNodeWithContentDescription(getString(R.string.saveButtonDescription)).performClick()
            }
            // assert if all notes displayed
            (0..3).forEach {
                onNodeWithText(it.toString()).assertIsDisplayed()
                onNodeWithText(it.toString()).assertIsDisplayed()
            }
            // select descending order
            onNodeWithContentDescription(getString(R.string.toggleDescription)).performClick()
            onNodeWithTag(TestTags.DESCENDING_BUTTON).performClick()
            onNodeWithContentDescription(getString(R.string.toggleDescription)).performClick()
            // get all notes after sorted
            val notes = onAllNodesWithTag(TestTags.NOTE)
            runBlocking {
                delay(20000)
            }
            // assert if sorted descending successfully
            (0..3).forEach {
                notes[it].assertTextContains((3 - it).toString())
            }
        }
    }


}