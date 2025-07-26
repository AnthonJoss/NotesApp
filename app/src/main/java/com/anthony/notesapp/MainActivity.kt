package com.anthony.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anthony.notesapp.data.model.Note
import com.anthony.notesapp.ui.theme.NotesAppTheme
import com.anthony.notesapp.viewModel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val noteViewModel: NoteViewModel = viewModel()
                MainNotesView(viewModel = noteViewModel) // Pass it to your composable
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainNotesView(viewModel: NoteViewModel = viewModel()){
    val notes by viewModel.notes.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSampleNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text(text = "Secretitos", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = Color.Yellow
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Nota",
                )
            }
        }
    ) {
        paddingValues -> NotesList(paddingValues, notes)
    }
}

@Composable
fun NotesList(
    paddingValues: PaddingValues = PaddingValues(12.dp),
    notes : List<Note>
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            Modifier
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(notes) { note ->
                NoteCard(note)
            }
        }
    }
}

@Composable
fun NoteCard(note: Note){
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().clickable {

        },
        colors = CardDefaults.cardColors(Color.Gray),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Icono de Favorito",
                    tint = Color(0xFF616161)
                )
                Text(note.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}