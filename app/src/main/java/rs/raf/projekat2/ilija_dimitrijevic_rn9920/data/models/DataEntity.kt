package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "raf")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Integer? = null,
    val predmet: String,
    val tip: String,
    val nastavnik: String,
    val grupe: String,
    val dan: String,
    val termin: String,
    val ucionica: String
)