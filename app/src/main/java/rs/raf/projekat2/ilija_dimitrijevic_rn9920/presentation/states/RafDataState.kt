package rs.raf.projekat2.ilija_dimitrijevic_rn9920.presentation.states

import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.models.Data

sealed class RafDataState {
    object Loading: RafDataState()
    object DataFetched: RafDataState()
    data class Success(val data: List<Data>): RafDataState()
    data class Error(val message: String): RafDataState()
}