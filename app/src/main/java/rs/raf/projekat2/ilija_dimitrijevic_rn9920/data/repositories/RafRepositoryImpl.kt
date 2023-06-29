package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.converters.DateConverter
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local.DataDao
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local.NoteDao
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.remote.RafService
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class RafRepositoryImpl(
    private val rafService: RafService,
    private val localDataSource: DataDao,
    private val noteDataSource: NoteDao
    ) : RafRepository {

    val dateConverter : DateConverter = DateConverter()


    override fun fetchAll(): Observable<Resource<Unit>> {
        return rafService
            .getAll()
            .doOnNext {
                val entities = it.map {
                    DataEntity(
                        predmet = it.predmet,
                        tip = it.tip,
                        nastavnik = it.nastavnik,
                        grupe = it.grupe,
                        dan = it.dan,
                        termin = it.termin,
                        ucionica = it.ucionica
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Data>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Data(it.predmet, it.tip,it.nastavnik, it.grupe, it.dan, it.termin, it.ucionica)
                }
            }
    }

    override fun getFilteredData(grupa: String, dan: String, profesorPredmet: String): Observable<List<Data>> {

            var profPredmet = "%" + profesorPredmet + "%"
            var gr = "%" + grupa + "%"
            var day = dan

            if(grupa=="Grupa"){
                gr = ""
            }
            if(profesorPredmet==""){
                profPredmet = ""
            }
            if(dan=="Dan"){
                day = ""
            }

            return localDataSource.getFilteredData(profPredmet = profPredmet, dan = day, grupa = gr)
                .map {
                    it.map {
                        Data(it.predmet, it.tip,it.nastavnik, it.grupe, it.dan, it.termin, it.ucionica)
                    }
                }

    }

    override fun addNote(title:String, content: String): Completable {
        val currentDate = Date()

        val noteEntity = NoteEntity(title = title,content = content, archived = false, date = this.dateConverter.dateToTimestamp(
            date = currentDate))
        return noteDataSource.insertNote(noteEntity)
    }

    override fun getAllNotes(): Observable<List<Note>> {
        return noteDataSource
            .getAllNotes()
            .map {
                it.map {
                    Note(it.id,it.title, it.content,it.archived,this.dateConverter.fromTimestamp(it.date))
                }
            }
    }

    override fun deleteNote(id: Int): Completable {
        return noteDataSource.deleteById(id = id)
    }

    override fun archiveNote(id: Int, arhcived: Boolean): Completable {
        return noteDataSource.archiveNote(id = id,archived = !arhcived)
    }

    override fun editNote(id: Int, title: String,content: String): Completable {
        return noteDataSource.editNote(id = id, title = title, content = content)
    }

    override fun getAllByName(name: String): Observable<List<Note>> {
        return noteDataSource.getByName(name)
            .map {
                it.map {
                    Note(it.id, it.title, it.content, it.archived,this.dateConverter.fromTimestamp(it.date))
                }
            }
    }

//    override fun insert(movie: Data): Completable {
//        TODO("Not yet implemented")
//    }



}