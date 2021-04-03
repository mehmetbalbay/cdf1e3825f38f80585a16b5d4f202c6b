package mehmetbalbay.spaceApp.di

import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SpaceStationRepository(spaceStationClient = get(), spaceStationsDao = get()) }
}