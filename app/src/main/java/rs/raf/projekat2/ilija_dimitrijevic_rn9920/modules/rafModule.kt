package rs.raf.projekat2.ilija_dimitrijevic_rn9920.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.local.RafDatabase
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.datasources.remote.RafService
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.repositories.RafRepository
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.data.repositories.RafRepositoryImpl
import rs.raf.projekat2.ilija_dimitrijevic_rn9920.viewmodel.RafViewModel

val rafModule = module {

    viewModel { RafViewModel(rafRepository = get()) }

    single<RafRepository> { RafRepositoryImpl(rafService = get(), localDataSource = get(), noteDataSource = get()) }

    single { get<RafDatabase>().getDataDao() }

    single { get<RafDatabase>().getNoteDao() }

    single<RafService> { create(get()) }

}