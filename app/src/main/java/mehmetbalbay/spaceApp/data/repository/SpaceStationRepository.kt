package mehmetbalbay.spaceApp.data.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mehmetbalbay.spaceApp.data.network.ApiResponse
import mehmetbalbay.spaceApp.data.network.client.SpaceStationClient
import mehmetbalbay.spaceApp.data.network.message
import mehmetbalbay.spaceApp.data.network.response.SpaceStationsResponse
import timber.log.Timber

class SpaceStationRepository constructor(
    private val spaceStationClient: SpaceStationClient
) : Repository {

    override var isLoading: Boolean = false

    init {
        Timber.d("Injection SpaceStationRepository")
    }

    suspend fun loadSpaceStations(error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<SpaceStationsResponse.SpaceStationsResponseItem>>()
        var spaceStations: List<SpaceStationsResponse.SpaceStationsResponseItem>? = null

        isLoading = false
        spaceStationClient.fetchSpaceStations { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        spaceStations = data
                        liveData.postValue(spaceStations)
                    }
                }
                is ApiResponse.Failure.Error -> error(response.message())
                is ApiResponse.Failure.Exception -> error(response.message())
            }
        }

        liveData.apply { postValue(spaceStations) }
    }
}