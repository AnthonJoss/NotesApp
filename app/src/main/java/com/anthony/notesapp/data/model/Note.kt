package com.anthony.notesapp.data.model

data class Note(
    val id: Int,
    val title: String?,
    val content: String?,
    val secret: Int,
)

data class NoteUpsert(
    val title: String?,
    val content: String?,
    val secret: Int
)

