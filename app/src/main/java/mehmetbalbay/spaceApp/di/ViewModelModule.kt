package mehmetbalbay.spaceApp.di

import mehmetbalbay.spaceApp.ui.main.MainViewModel
import mehmetbalbay.spaceApp.ui.space_vehicle.create.CreateVehicleViewModel
import mehmetbalbay.spaceApp.ui.station.StationViewModel
import mehmetbalbay.spaceApp.ui.station.favorite.MyFavoriteStationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { CreateVehicleViewModel() }
    viewModel { StationViewModel(spaceStationRepository = get()) }
    viewModel { MyFavoriteStationViewModel(spaceStationRepository = get()) }
}