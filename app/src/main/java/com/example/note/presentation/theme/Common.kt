package com.example.note.presentation.theme


import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.note.R

@Composable
fun NotedText(modifier: Modifier = Modifier){
    Text(modifier = modifier , text = stringResource(id = R.string.noted), fontSize = 28.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
}

@Composable
fun CustomRadioButton(modifier:Modifier,selected: Boolean ,onClick: () ->Unit,textId: Int){
    Row(modifier){
     RadioButton(selected = selected, onClick = onClick, colors =  RadioButtonDefaults.colors(
         selectedColor = IconColor,
         unselectedColor = Gray
     ))
        Text(text = stringResource(id = textId), textAlign = TextAlign.Center, modifier = Modifier.align(CenterVertically))
    }
}