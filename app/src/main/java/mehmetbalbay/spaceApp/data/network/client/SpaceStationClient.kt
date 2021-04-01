package mehmetbalbay.spaceApp.data.network.client

import mehmetbalbay.spaceApp.data.network.ApiResponse
import mehmetbalbay.spaceApp.data.network.response.SpaceStationsResponse
import mehmetbalbay.spaceApp.data.network.service.SpaceStationService
import mehmetbalbay.spaceApp.data.network.transform

class SpaceStationClient(private val service: SpaceStationService) {

    fun fetchSpaceStations(
        onResult: (response: ApiResponse<SpaceStationsResponse>) -> Unit
    ) {
        this.service.fetchSpaceStations().transform(onResult)
    }
}