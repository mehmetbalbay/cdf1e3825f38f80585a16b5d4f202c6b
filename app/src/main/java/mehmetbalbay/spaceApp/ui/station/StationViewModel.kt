package mehmetbalbay.spaceApp.ui.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import timber.log.Timber
import java.util.*

class StationViewModel constructor(
    private val spaceStationRepository: SpaceStationRepository
) : LiveCoroutinesViewModel() {

    private val spaceStationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val spaceStationsLiveData: LiveData<List<SpaceStation>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    var spaceStations: List<SpaceStation>? = null

    init {
        Timber.d("Injection StationViewModel")

        this.spaceStationsLiveData = spaceStationPageLiveData.switchMap { _ ->
            launchOnViewModelScope {
                spaceStationRepository.loadSpaceStations { _toastLiveData.postValue(it) }
            }
        }
    }

    fun performSearch(searchParam: String): List<SpaceStation> {
        spaceStations?.let { mSpaceStations ->
            return mSpaceStations.filter {
                it.name.toLowerCase(Locale.ROOT).contains(
                    searchParam.toLowerCase(Locale.ROOT)
                )
            }
        }
        return emptyList()
    }

    fun addFavoriteStation(spaceStation: SpaceStation) {
        spaceStationRepository.addFavoriteStation(spaceStation = spaceStation)
    }

    fun postSpaceStationPage(page: Int) = this.spaceStationPageLiveData.postValue(page)
}