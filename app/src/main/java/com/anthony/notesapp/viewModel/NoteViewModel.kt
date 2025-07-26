package com.anthony.notesapp.viewModel

import androidx.lifecycle.ViewModel
import com.anthony.notesapp.data.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun loadSampleNotes() {
        _notes.value = listOf(
            Note(1, "Secretito 1", "Mi secretito ha sido revelado", 1324),
            Note(2, "No mires mi secretito", "Mi secretito ha sido revelado", 1234),
            Note(3, "Secretito 3", "Mi secretito ha sido revelado", 1234),
            Note(4, "Secretito 4", "Mi secretito ha sido revelado", 1234),
            Note(5, "Secretito 5", "Mi secretito ha sido revelado", 1234),
            Note(6, "Secretito 6", "Mi secretito ha sido revelado", 1234),
        )
    }
}