package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val content: String,
    val archived: Boolean,
    val date: Long
)