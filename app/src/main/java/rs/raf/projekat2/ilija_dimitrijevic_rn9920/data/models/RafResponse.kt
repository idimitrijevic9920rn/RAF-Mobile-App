package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RafResponse(
    val predmet: String,
    val tip: String,
    val nastavnik: String,
    val grupe: String,
    val dan: String,
    val termin: String,
    val ucionica: String
)