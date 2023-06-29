package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.NoteEntity


@Dao
abstract class NoteDao {

    @Insert
    abstract fun insertNote(note: NoteEntity): Completable

    @Query("SELECT * FROM notes")
    abstract fun getAllNotes(): Observable<List<NoteEntity>>

    @Query("DELETE FROM notes")
    abstract fun deleteAll(): Completable

    @Query("SELECT * FROM notes WHERE id == :id")
    abstract fun getById(id: Int): NoteEntity

    @Query("UPDATE notes SET archived = :archived WHERE id == :id")
    abstract fun updateArchivedStatus(id: Int, archived: Boolean): Completable


    @Query("DELETE FROM notes WHERE id == :id")
    abstract fun deleteById(id: Int): Completable

    @Query("UPDATE notes SET archived = :archived WHERE id == :id")
    abstract fun archiveNote(id: Int, archived: Boolean): Completable

    @Query("UPDATE notes SET title = :title,content = :content WHERE id == :id")
    abstract fun editNote(id: Int, title: String, content: String): Completable

    @Query("SELECT * FROM notes WHERE (title LIKE :name || '%') OR (content LIKE :name || '%')")
    abstract fun getByName(name: String): Observable<List<NoteEntity>>


}