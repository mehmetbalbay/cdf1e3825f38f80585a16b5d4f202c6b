package mehmetbalbay.spaceApp.data.network.service

import mehmetbalbay.spaceApp.data.network.response.SpaceStationsResponse
import retrofit2.Call
import retrofit2.http.GET

interface SpaceStationService {

    @GET("v3/e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    fun fetchSpaceStations(): Call<SpaceStationsResponse>
}