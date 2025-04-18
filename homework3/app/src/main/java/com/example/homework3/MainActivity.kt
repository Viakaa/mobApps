package com.example.homework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homework3.model.Notes
import com.example.homework3.ui.theme.Homework3Theme
import com.example.homework3.viewmodel.NotesViewModel
import com.example.homework3.view.NotesListScreen
import com.example.homework3.view.NotesDetailsScreen
import com.example.homework3.view.AddNoteScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework3Theme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val noteViewModel: NotesViewModel = viewModel()
                    NavHost(navController = navController, startDestination = "noteListScreen") {
                        composable("noteListScreen") {
                            NotesListScreen(
                                viewModel = noteViewModel,
                                navController = navController
                            )
                        }
                        composable(
                            route = "noteDetailScreen/{noteId}",
                            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getInt("noteId")

                            if (noteId != null) {
                                val note by produceState<Notes?>(initialValue = null, noteId) {
                                    value = noteViewModel.getNoteById(noteId)
                                }

                                note?.let {
                                    NotesDetailsScreen(
                                        note = it,
                                        noteId = noteId.toString(),
                                        viewModel = noteViewModel,
                                        navController = navController
                                    )
                                } ?: run {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        composable("addNote") {
                            AddNoteScreen(
                                navController = navController,
                                viewModel = noteViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}