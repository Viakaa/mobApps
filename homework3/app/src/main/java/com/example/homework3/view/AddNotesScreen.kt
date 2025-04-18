package com.example.homework3.view

import com.example.homework3.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.homework3.viewmodel.NotesViewModel
import com.example.homework3.model.Notes

@Composable
fun AddNoteScreen(navController: NavHostController, viewModel: NotesViewModel)  {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var content by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary_color))
    ) {
        TextField(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 20.dp, start = 65.dp),
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .padding(bottom = 30.dp, start = 65.dp),
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(start = 150.dp),
            onClick = {
                if (title.text.isNotBlank() && content.text.isNotBlank()) {
                    viewModel.addNote(
                        Notes(
                            id = 0,
                            title = title.text,
                            text = content.text
                        )
                    )
                    navController.navigate("noteListScreen") {
                        popUpTo("noteListScreen") { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.secondary_color))
        ) {
            Text("Save", color = colorResource(id = R.color.tertiary_color))
        }
    }
}
