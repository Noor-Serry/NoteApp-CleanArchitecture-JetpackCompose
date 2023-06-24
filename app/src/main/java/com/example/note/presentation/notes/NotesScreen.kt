package com.example.note.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Note
import com.example.note.R
import com.example.note.presentation.theme.CustomRadioButton
import com.example.note.presentation.theme.Gray
import com.example.note.presentation.theme.IconColor
import com.example.note.presentation.theme.MistyGray
import com.example.note.presentation.theme.NotedText
import com.example.note.presentation.theme.OffWhite
import com.example.note.presentation.theme.PaleCyan
import androidx.navigation.NavHostController
import com.example.note.presentation.add_edit_screen.goToAddEditScreenScreen
import com.example.note.presentation.utils.NULL_ID


@Composable
fun NotesScreen(viewModel: NotesViewModel = hiltViewModel(),navController: NavHostController) {
    val state = viewModel.state.collectAsState()
    NotesContent(state,navController,viewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable

fun NotesContent(
    state: State<NotesState>, navController: NavHostController, viewModel : NotesViewModel
) {
    val lazyState = rememberLazyGridState()
    Scaffold(floatingActionButton = { FloatingButton(state = state,navController) }) {
        Column {
            LazyVerticalGrid(
                state = lazyState,
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Header(state = state, viewModel = viewModel)
                }

                items(state.value.notes, key = { it.id }) {
                    NoteCard(Modifier.animateItemPlacement(), it,navController, viewModel)
                }
            }
        }
        LaunchedEffect(key1 = lazyState.isScrollInProgress) {
           viewModel.onLazyScrolling(lazyState.isScrollInProgress)
        }
    }

}

@Composable
fun FloatingButton(state: State<NotesState>,navController: NavHostController) {
    AnimatedVisibility(
        visible = state.value.isAddButtonVisible,
        enter = slideInVertically { it + 50 },
        exit = slideOutVertically { it + 50 },
        modifier = Modifier.animateContentSize(tween(1000))
    ) {
        FloatingActionButton(shape = AbsoluteRoundedCornerShape(50),
            containerColor = IconColor,
            onClick = {navController.goToAddEditScreenScreen(NULL_ID) }) {
            Icon(
                Icons.Default.Add, contentDescription = "Add", tint = Color.White
            )
        }
    }
}

@Composable
fun Header(state: State<NotesState>,  viewModel : NotesViewModel) {
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
    ) {
        val (toggleRef, notedRef, orderSectionRef) = createRefs()

        Icon(
            painterResource(id = R.drawable.baseline_sort_24),
            contentDescription = "Sort",
            tint = IconColor, modifier = Modifier
                .constrainAs(toggleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable { viewModel.onClickToggleOrderSection() }
        )

        NotedText(modifier = Modifier.constrainAs(notedRef) {
            top.linkTo(parent.top)
            start.linkTo(toggleRef.end)
            end.linkTo(parent.end)
        })

        AnimatedVisibility(state.value.isOrderSectionVisible, enter = slideInHorizontally { -it },
            exit = slideOutHorizontally { it }, modifier = Modifier
                .constrainAs(orderSectionRef) {
                    top.linkTo(notedRef.bottom)
                    start.linkTo(parent.start)
                }
                .animateContentSize(tween(1000))) {
            OrderSection(state,viewModel )
        }


    }
}

@Composable
fun OrderSection(state: State<NotesState>, viewModel : NotesViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomRadioButton(
            textId = R.string.ascending,
            modifier = Modifier.weight(1F),
            onClick = {
                viewModel.onClickAscendingButton()
            }, selected = state.value.ascendingButtonChecked
        )
        CustomRadioButton(
            textId = R.string.descending,
            modifier = Modifier.weight(1F),
            onClick = {
               viewModel.onClickDescendingButton()
            }, selected = !state.value.ascendingButtonChecked
        )
    }
}


@Composable
fun NoteCard(modifier: Modifier, note: Note, navController: NavHostController, viewModel : NotesViewModel) {
    Card(
        modifier = modifier
            .height(200.dp)
            .background(Color.White).clickable { navController.goToAddEditScreenScreen(note.id) },
        shape = AbsoluteRoundedCornerShape(10)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .background(OffWhite)
                .padding(8.dp)
        ) {
            Text(
                text = note.title,
                fontSize = 20.sp,
                color = PaleCyan,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = note.convertTimeMillisToDate(), fontSize = 11.sp, color = Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Box() {

                Text(
                    text = note.body,
                    fontSize = 14.sp,
                    color = MistyGray,
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis, modifier = Modifier.zIndex(-1F).fillMaxSize()
                )
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",

                    modifier = Modifier
                        .size(30.dp)
                        .align(
                            Alignment.BottomEnd
                        )
                        .clickable { viewModel.deleteNote(note) },
                    tint = IconColor
                )
            }


        }
    }

}