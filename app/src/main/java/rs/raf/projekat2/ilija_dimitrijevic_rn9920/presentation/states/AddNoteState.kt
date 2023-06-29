package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states

sealed class AddNoteState {

    object Success: AddNoteState()
    data class Error(val message: String): AddNoteState()

}