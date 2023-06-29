package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Note
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Resource

interface RafRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Data>>
    fun getFilteredData(grupa: String, dan: String, profesorPredmet: String): Observable<List<Data>>
    fun getAllNotes(): Observable<List<Note>>
    fun addNote(title: String, content: String): Completable
    fun deleteNote(id: Int): Completable
    fun archiveNote(id: Int, archived: Boolean): Completable
    fun editNote(id: Int, title: String,content: String): Completable
    fun getAllByName(name: String): Observable<List<Note>>

//    fun insert(data: Data): Completable

}