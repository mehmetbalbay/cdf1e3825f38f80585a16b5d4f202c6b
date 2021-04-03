package mehmetbalbay.spaceApp.ui.station.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import timber.log.Timber

class MyFavoriteStationViewModel constructor(
    spaceStationRepository: SpaceStationRepository
) : LiveCoroutinesViewModel() {

    private val spaceStationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val spaceStationsLiveData: LiveData<List<SpaceStation>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    init {
        Timber.d("MyFavoriteStationViewModel")

        this.spaceStationsLiveData = launchOnViewModelScope {
            spaceStationRepository.loadFavoriteStations { _toastLiveData.postValue(it) }
        }
    }
}