package com.example.homework3.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homework3.viewmodel.NotesViewModel
import com.example.homework3.model.Notes
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.colorResource
import com.example.homework3.R
@OptIn(ExperimentalMaterialApi::class)

@Composable
fun NotesListScreen(viewModel: NotesViewModel, navController: NavController) {
    val notes by viewModel.allNotes.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add a note XD") },
                onClick = {
                    navController.navigate("addNote")
                },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add Note") },
                backgroundColor = colorResource(id = R.color.secondary_color),
                contentColor = colorResource(id = R.color.tertiary_color)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.primary_color))
                .padding(16.dp)
        ) {
            Text(
                text = "Lets make a little note, buddy :0",
                style = MaterialTheme.typography.h5,
                color = colorResource(id = R.color.tertiary_color),
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            )

            LazyColumn {
                items(notes) { note ->
                    Card(
                        onClick = {
                            navController.navigate("noteDetailScreen/${note.id}")
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = { viewModel.deleteNote(note) },

                                )
                            },

                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = colorResource(id = R.color.secondary_color),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = note.title, style = MaterialTheme.typography.h6)
                            Text(text = note.text, maxLines = 1)
                        }
                    }
                }
            }

        }
    }
}

