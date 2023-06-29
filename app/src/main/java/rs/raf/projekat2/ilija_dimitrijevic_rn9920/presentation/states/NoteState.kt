package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states

import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note

sealed class NoteState {
    object Loading: NoteState()
    data class Success(val notes: List<Note>): NoteState()
    data class Error(val message: String): NoteState()
}