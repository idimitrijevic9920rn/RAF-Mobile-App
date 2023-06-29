package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.AddNoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NoteState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.NotesByDateState
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states.RafDataState

interface MainContract {

    interface ViewModel{

        val data: LiveData<List<Data>>
        val rafDataState: LiveData<RafDataState>
        val addDone: LiveData<AddNoteState>
        val noteState: MutableLiveData<NoteState>
        val notesByDayState: MutableLiveData<NotesByDateState>



        fun fetchAll()
        fun getAll()
        fun getFilteredData(grupa: String, dan: String, profesorPredmet: String)
        fun addNote(title:String, content: String)
        fun getAllNotes()
        fun removeNote(id: Int)
        fun archiveNote(id: Int, archive: Boolean)
        fun editNote(id: Int, title: String, content: String)
        fun getNotesByName(name: String)
        fun getNotesByDate()



    }
}