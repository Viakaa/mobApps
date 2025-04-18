package com.example.homework3.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.homework3.model.Notes
import com.example.homework3.viewmodel.NotesViewModel
import android.util.Log
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.homework3.R

@Composable
fun NotesDetailsScreen(note: Notes?, noteId: String, viewModel: NotesViewModel, navController: NavController) {
    Log.d("NotesDetailsScreen", "Navigated to note with ID: $noteId")
    Text("Note Details for ID: $noteId")

    var isEditing by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.text ?: "") }

    LaunchedEffect(noteId) {
        if (note == null) {
            val fetchedNote = viewModel.getNoteById(noteId.toInt())
            title = fetchedNote.title
            content = fetchedNote.text
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary_color))
            .padding(16.dp)
    ) {


        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            enabled = isEditing,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = colorResource(id = R.color.secondary_color),
                focusedLabelColor = colorResource(id = R.color.secondary_color)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            enabled = isEditing,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedBorderColor = colorResource(id = R.color.secondary_color),
                focusedLabelColor = colorResource(id = R.color.secondary_color)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isEditing = !isEditing
                if (!isEditing && note != null) {
                    viewModel.updateNote(note.copy(
                        title = title,
                        text = content,
                        timeStamp = System.currentTimeMillis()
                    ))
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.secondary_color))
        ) {
            Text(if (isEditing) "Save" else "Edit", color = colorResource(id = R.color.tertiary_color))
        }
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back", color = colorResource(id = R.color.tertiary_color))
        }
    }
}
