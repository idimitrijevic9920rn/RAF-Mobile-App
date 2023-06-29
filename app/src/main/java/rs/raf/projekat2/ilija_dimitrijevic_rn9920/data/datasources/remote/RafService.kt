package rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.RafResponse

interface RafService {

    @GET("raspored/json.php")
    fun getAll(): Observable<List<RafResponse>>


}