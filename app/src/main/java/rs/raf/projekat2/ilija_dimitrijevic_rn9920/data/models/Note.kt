package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models

import java.util.*

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val archived: Boolean,
    val date: Date
)