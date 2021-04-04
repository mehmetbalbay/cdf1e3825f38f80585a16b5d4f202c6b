package mehmetbalbay.spaceApp.data.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mehmetbalbay.spaceApp.data.local.dao.SpaceStationsDao
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.network.ApiResponse
import mehmetbalbay.spaceApp.data.network.client.SpaceStationClient
import mehmetbalbay.spaceApp.data.network.message
import timber.log.Timber

class SpaceStationRepository constructor(
    private val spaceStationClient: SpaceStationClient,
    private val spaceStationsDao: SpaceStationsDao
) : Repository {

    override var isLoading: Boolean = false

    init {
        Timber.d("Injection SpaceStationRepository")
    }

    suspend fun loadSpaceStations(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<SpaceStation>>()
        var spaceStations = spaceStationsDao.getSpaceStations()

        if (spaceStations.isEmpty()) {
            isLoading = true
            spaceStationClient.fetchSpaceStations { response ->
                isLoading = false
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let { data ->
                            data.map {
                                if (it.name == "Dünya") {
                                    it.isCurrentStation = true
                                }
                                it.isTraveler = true
                            }
                            spaceStations = data
                            spaceStationsDao.insertSpaceStations(data)
                            liveData.postValue(spaceStations)
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        }

        liveData.apply { postValue(spaceStations) }
    }

    fun getFavoriteStations(): List<SpaceStation>? = spaceStationsDao.getFavoriteSpaceStation()

    fun getCurrentSpaceStation(): SpaceStation? = spaceStationsDao.getCurrentSpaceStation()

    fun addFavoriteStation(spaceStation: SpaceStation) {
        spaceStation.isFavorite = !spaceStation.isFavorite
        updateSpaceStation(spaceStation)
    }

    fun updateSpaceStation(spaceStation: SpaceStation) {
        spaceStationsDao.updateSpaceStation(spaceStation)
    }

    fun getSpaceStationByName(stationName: String): SpaceStation? {
        return spaceStationsDao.getSpaceStation(stationName)
    }
}