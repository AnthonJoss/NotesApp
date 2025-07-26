package com.anthony.notesapp.viewModel

import androidx.lifecycle.ViewModel
import com.anthony.notesapp.data.model.Note
import com.anthony.notesapp.data.model.NoteUpsert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteViewModel : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    private val _selectedNote = MutableStateFlow<Note?>(null)

    val notes: StateFlow<List<Note>> = _notes.asStateFlow()
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

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

    fun createNote(
        title : String?,
        content : String?,
        secret : Int
    ) {
        val noteRequest = NoteUpsert(title = title, content = content, secret = secret)
        val note = Note(
            id = getLastId() + 1,
            title = noteRequest.title,
            content = noteRequest.content,
            secret = noteRequest.secret
        )
        val currentList = _notes.value.toMutableList()
        currentList.add(note)
        _notes.value = currentList
    }

    fun updateNote(
        title: String?,
        content: String?,
        secret: Int,
        noteId: Int
    ) {
        val updatedNote = Note(
            id = noteId,
            title = title,
            content = content,
            secret = secret
        )

        val updatedList = _notes.value.map { note ->
            if (note.id == noteId) updatedNote else note
        }

        _notes.value = updatedList
    }

    fun getNoteById(noteId: Int) {
        _selectedNote.value = _notes.value.find { it.id == noteId }
    }

    private fun getLastId(): Int {
        return _notes.value.lastOrNull()?.id ?: 0
    }
}