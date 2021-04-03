package mehmetbalbay.spaceApp.ui.station.favorite

import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import timber.log.Timber

class MyFavoriteStationViewModel constructor(
    val spaceStationRepository: SpaceStationRepository
) : LiveCoroutinesViewModel() {

    init {
        Timber.d("MyFavoriteStationViewModel")
    }

    fun getFavoriteStationList() = this.spaceStationRepository.getFavoriteStations()

    fun addFavoriteStation(spaceStation: SpaceStation) {
        spaceStationRepository.addFavoriteStation(spaceStation = spaceStation)
    }
}