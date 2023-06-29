package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}