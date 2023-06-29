package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states

import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note

sealed class NotesByDateState {
    object Loading: NoteState()
    data class Success(val notes: List<Int>): NotesByDateState()
    data class Error(val message: String): NotesByDateState()
}