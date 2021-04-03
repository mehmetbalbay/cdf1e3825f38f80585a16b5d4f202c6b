package mehmetbalbay.spaceApp.ui.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import timber.log.Timber

class StationViewModel constructor(
    private val spaceStationRepository: SpaceStationRepository
) : LiveCoroutinesViewModel() {

    private val spaceStationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val spaceStationsLiveData: LiveData<List<SpaceStation>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    init {
        Timber.d("Injection StationViewModel")

        this.spaceStationsLiveData = spaceStationPageLiveData.switchMap { page ->
            launchOnViewModelScope {
                spaceStationRepository.loadSpaceStations { _toastLiveData.postValue(it) }
            }
        }
    }

    fun postSpaceStationPage(page: Int) = this.spaceStationPageLiveData.postValue(page)
}