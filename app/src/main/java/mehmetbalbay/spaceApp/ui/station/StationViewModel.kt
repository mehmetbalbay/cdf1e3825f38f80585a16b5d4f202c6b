package mehmetbalbay.spaceApp.ui.station

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import mehmetbalbay.spaceApp.utils.Const
import mehmetbalbay.spaceApp.utils.SharedPreferenceHelper
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class StationViewModel constructor(
    private val spaceStationRepository: SpaceStationRepository
) : LiveCoroutinesViewModel() {

    private val spaceStationPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val spaceStationsLiveData: LiveData<List<SpaceStation>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    private val _spaceSuitCountLiveData: MutableLiveData<Int> = MutableLiveData()
    val spaceSuitCountLiveData: LiveData<Int> get() = _spaceSuitCountLiveData

    private val _universalSpaceTimeLiveData: MutableLiveData<Int> = MutableLiveData()
    val universalSpaceTimeLiveData: LiveData<Int> get() = _universalSpaceTimeLiveData

    private val _currentStationLiveData: MutableLiveData<SpaceStation> = MutableLiveData()
    val currentStationLiveData: LiveData<SpaceStation> get() = _currentStationLiveData

    private val _vehicleHealthLiveData: MutableLiveData<Int> = MutableLiveData()
    val vehicleHealthLiveData: LiveData<Int> get() = _vehicleHealthLiveData

    var isDeliveryFinished: Boolean = false

    var spaceStations: List<SpaceStation>? = null

    var spaceVehicleName: String = ""

    var vehicleHealth: Int = 0

    var spaceSuits: Int = 0
    var universalSpaceTime: Int = 0
    var durabilityTime: Int = 0

    init {
        spaceVehicleName = SharedPreferenceHelper.getSharedData(Const.VEHICLE_NAME) as String? ?: ""
        vehicleHealth =
            SharedPreferenceHelper.getSharedData(Const.VEHICLE_DAMAGE_CAPACITY) as Int? ?: 0

        spaceSuits = (SharedPreferenceHelper.getSharedData<Int>(Const.SPACE_SUIT_COUNT) ?: 0)
        universalSpaceTime =
            (SharedPreferenceHelper.getSharedData<Int>(Const.UNIVERSAL_SPACE_TIME) ?: 0)
        durabilityTime = (SharedPreferenceHelper.getSharedData<Int>(Const.DURABILITY_TIME) ?: 0)

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

    fun getCurrentStation(): SpaceStation {
        return spaceStationRepository.getCurrentSpaceStation() ?: SpaceStation(
            "Dünya",
            capacity = 0,
            coordinateX = 0,
            coordinateY = 0,
            need = 0,
            stock = 0,
            isFavorite = false,
            isTraveler = true,
            isCurrentStation = true
        )
    }

    fun getCurrentStationName(): String = getCurrentStation().name

    private fun postSpaceSuitCount(spaceSuitCount: Int) {
        _spaceSuitCountLiveData.postValue(spaceSuitCount)
    }

    private fun postUniversalSpaceTime(universalSpaceTime: Int) {
        _universalSpaceTimeLiveData.postValue(universalSpaceTime)
    }

    private fun postCurrentStation(newCurrentStation: SpaceStation) {
        _currentStationLiveData.postValue(newCurrentStation)
    }

    private fun calculateTravelDistance(
        currentStation: SpaceStation,
        arrivalStation: SpaceStation
    ): Int {
        val currentStationX = currentStation.coordinateX
        val arrivalStationX = arrivalStation.coordinateX

        val currentStationY = currentStation.coordinateY
        val arrivalStationY = arrivalStation.coordinateY

        return sqrt(
            (arrivalStationX.minus(currentStationX).toDouble().pow(2)) +
                    (arrivalStationY.minus(currentStationY).toDouble().pow(2))
        ).toInt()
    }

    fun travel(
        currentStation: SpaceStation,
        arrivalStation: SpaceStation
    ) {
        val travelDistance = calculateTravelDistance(currentStation, arrivalStation)

        if (universalSpaceTime > travelDistance && spaceSuits > arrivalStation.need) {
            val remainDistance = universalSpaceTime - travelDistance
            universalSpaceTime = remainDistance
            postUniversalSpaceTime(universalSpaceTime)
            SharedPreferenceHelper.saveSharedData(Const.UNIVERSAL_SPACE_TIME, universalSpaceTime)

            val remainSpaceSuit = spaceSuits - arrivalStation.need
            spaceSuits = remainSpaceSuit
            postSpaceSuitCount(spaceSuits)
            SharedPreferenceHelper.saveSharedData(Const.SPACE_SUIT_COUNT, spaceSuits)

            arrivalStation.stock += arrivalStation.need
            arrivalStation.need = 0
            arrivalStation.isCurrentStation = true

            // Successful Travel Set Disable
            arrivalStation.isTraveler = false
            spaceStationRepository.updateSpaceStation(arrivalStation)
            postCurrentStation(arrivalStation)
        } else {
            turnToWorld(currentStation)
        }
    }

    fun turnToWorld(currentStation: SpaceStation) {
        isDeliveryFinished = true

        currentStation.isCurrentStation = false
        spaceStationRepository.updateSpaceStation(currentStation)

        val worldStation = spaceStationRepository.getSpaceStationByName("Dünya")
        worldStation?.apply {
            isCurrentStation = true
            isTraveler = false
        }?.also {
            spaceStationRepository.updateSpaceStation(it)
        }
    }

    private fun postVehicleHealth(health: Int) {
        _vehicleHealthLiveData.postValue(health)
    }

    fun takeDamage() {
        vehicleHealth -= 10
        postVehicleHealth(vehicleHealth)
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_DAMAGE_CAPACITY, vehicleHealth)
    }
}