package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.DataEntity


@Dao
abstract class DataDao {

//    @Insert( onConflict = OnConflictStrategy.REPLACE )
//    abstract fun insert(entity: DataEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<DataEntity>): Completable

    @Query("SELECT * FROM raf")
    abstract fun getAll(): Observable<List<DataEntity>>

    @Query("SELECT * FROM raf WHERE (grupe LIKE :grupa || '%') AND (dan LIKE :dan || '%') AND ((LOWER(nastavnik) LIKE :profPredmet || '%') OR (LOWER(predmet) LIKE :profPredmet || '%'))")
    abstract fun getFilteredData(profPredmet: String, grupa: String, dan: String): Observable<List<DataEntity>>

    @Query("DELETE FROM raf")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<DataEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

//    @Query("SELECT * FROM raf WHERE ucionica LIKE :name || '%'")
//    abstract fun getByName(name: String): Observable<List<DataEntity>>

}