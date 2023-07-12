package com.example.note.presentation.add_edit_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.SaveNoteState
import com.example.note.R
import com.example.note.presentation.theme.Gray
import com.example.note.presentation.theme.IconColor
import com.example.note.presentation.theme.NotedText
import com.example.note.presentation.theme.TransparentColor
import com.example.note.presentation.utils.TestTags.BODY_TEXT_FIELD
import com.example.note.presentation.utils.TestTags.TITlE_TEXT_FIELD

@Composable
fun AddEditScreen(
    viewModel: AddEditViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val state = viewModel.state.collectAsState()

    AddEditContent(
        state = state, viewModel = viewModel
    ) { navHostController.popBackStack() }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContent(
    state: State<AddEditState>,
    viewModel: AddEditViewModel,
    onBackClick: () -> Unit
) {


    Scaffold(floatingActionButton = {
        FloatingActionButton(shape = AbsoluteRoundedCornerShape(50),
            containerColor = IconColor,
            onClick = { viewModel.onSaveButtonClick() }) {
            Icon(
                painterResource(id = R.drawable.baseline_save_24),
                contentDescription = stringResource(id = R.string.saveButtonDescription),
                tint = Color.White
            )
        }
    }) {


        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Header(onBackClick)
            TitleTextField(state) { viewModel.onTitleChange(it) }
            BodyTextField(state) { viewModel.onBodyChange(it) }
        }
    }
    val context = LocalContext.current
    if (state.value.saveState != null) LaunchedEffect(state.value) {
        when (state.value.saveState) {
            is SaveNoteState.Failure -> {
                Toast.makeText(
                    context,
                    (state.value.saveState as SaveNoteState.Failure).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            SaveNoteState.Success -> {
                onBackClick()
            }

            else -> {}
        }
    }


}

@Composable
fun Header(onBackClick: () -> Unit) {
    ConstraintLayout(
        Modifier.fillMaxWidth()
    ) {
        val (backRef, notedRef) = createRefs()
        Icon(Icons.Default.ArrowBack,
            contentDescription = "Sort",
            tint = IconColor,
            modifier = Modifier
                .constrainAs(backRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable { onBackClick() })

        NotedText(modifier = Modifier.constrainAs(notedRef) {
            top.linkTo(parent.top)
            start.linkTo(backRef.end)
            end.linkTo(parent.end)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTextField(state: State<AddEditState>, onTitleChange: (String) -> Unit) {
    TextField(
        value = state.value.title,
        singleLine = true,
        onValueChange = onTitleChange,
        label = { Text(text = "Title...") },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TransparentColor,
            unfocusedIndicatorColor = Gray,
            focusedIndicatorColor = Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(TITlE_TEXT_FIELD),
        textStyle = TextStyle(color = IconColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyTextField(state: State<AddEditState>, onBodyChange: (String) -> Unit) {
    TextField(value = state.value.body,
        textStyle = TextStyle(),
        onValueChange = onBodyChange,
        modifier = Modifier
            .fillMaxSize()
            .testTag(BODY_TEXT_FIELD),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = TransparentColor,
            unfocusedIndicatorColor = TransparentColor,
            unfocusedLabelColor = Gray,
            focusedLabelColor = Gray,
            focusedIndicatorColor = TransparentColor,
            disabledIndicatorColor = TransparentColor,

            ),
        label = { Text(text = "note content ...") })
}

