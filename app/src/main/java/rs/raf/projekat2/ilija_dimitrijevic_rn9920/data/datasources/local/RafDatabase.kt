package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.DataEntity
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.NoteEntity

@Database(
    entities = [DataEntity::class,NoteEntity::class],
    version = 2,
    exportSchema = false)
@TypeConverters()
abstract class RafDatabase : RoomDatabase() {
    abstract fun getDataDao(): DataDao
    abstract fun getNoteDao(): NoteDao
}