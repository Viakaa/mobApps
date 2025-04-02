package com.example.homework2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.compose.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewmodel.compose.viewModel


class NotesViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<NotesItem>>(emptyList())
    val notes = _notes.asStateFlow()

    fun addNote(note: NotesItem) {
        _notes.value = _notes.value + note
    }
}
data class NotesItem(val title: String, val text: String)

@Composable
fun NotesApp() {
    val navController = rememberNavController()
    val viewModel: NotesViewModel = viewModel()

    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel) }
        composable("addNote") { AddNoteScreen(navController, viewModel) }
        composable("details/{title}/{content}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val content = backStackEntry.arguments?.getString("content") ?: ""
            NoteScreen(title, content, navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: NotesViewModel) {
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add a note XD") },
                onClick = { navController.navigate("addNote") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add Note XD") }
            )
        }
    ) { paddingValues ->
            Text(
                text = "Lets make a little note, buddy :0",
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 16.dp, start = 40.dp),
                color =  colorResource(id = R.color.tertiary_color)
            )
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("details/${note.title}/${note.text}")
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    backgroundColor = colorResource(id = R.color.secondary_color)
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
            value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .padding(bottom = 30.dp, start = 65.dp),
            value = content, onValueChange = { content = it }, label = { Text("Content") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(start = 150.dp),
            onClick = {
                if (title.text.isNotBlank() && content.text.isNotBlank()) {
                    viewModel.addNote(NotesItem(title.text, content.text))
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.secondary_color))
        ) {
            Text("Save", color = colorResource(id = R.color.tertiary_color))
        }
    }
}

@Composable
fun NoteScreen(title: String, content: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.primary_color))
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content, style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.secondary_color))
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}
