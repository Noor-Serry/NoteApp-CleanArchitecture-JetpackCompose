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
import androidx.compose.ui.res.painterResource
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

@Composable
fun AddEditScreen(
    viewModel: AddEditViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val stateSave = viewModel.stateSave.collectAsState()
    val stateTitle = viewModel.stateTitle.collectAsState()
    val stateBody = viewModel.stateBody.collectAsState()
    AddEditContent(
        stateSave = stateSave, stateTitle = stateTitle, stateBody = stateBody, viewModel
    ) { navHostController.popBackStack() }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContent(
    stateSave: State<SaveNoteState?>,
    stateTitle: State<String>,
    stateBody: State<String>,
    viewModel: AddEditViewModel,
    onBackClick: () -> Unit
) {


    Scaffold(floatingActionButton = {
        FloatingActionButton(shape = AbsoluteRoundedCornerShape(50),
            containerColor = IconColor,
            onClick = { viewModel.onSaveButtonClick() }) {
            Icon(
                painterResource(id = R.drawable.baseline_save_24),
                contentDescription = "Add",
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
            TitleTextField(stateTitle) { viewModel.onTitleChange(it) }
            BodyTextField(stateBody) { viewModel.onBodyChange(it) }
        }
    }
    val context = LocalContext.current
    if (stateSave.value != null) LaunchedEffect(stateSave.value) {
        when (stateSave.value) {
            is SaveNoteState.Failure -> {
                Toast.makeText(
                    context, (stateSave.value as SaveNoteState.Failure).message, Toast.LENGTH_SHORT
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
fun TitleTextField(stateTitle: State<String>, onTitleChange: (String) -> Unit) {
    TextField(
        value = stateTitle.value,
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
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = IconColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyTextField(stateBody: State<String>, onBodyChange: (String) -> Unit) {
    TextField(value = stateBody.value,
        textStyle = TextStyle(),
        onValueChange = onBodyChange,
        modifier = Modifier.fillMaxSize(),
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

