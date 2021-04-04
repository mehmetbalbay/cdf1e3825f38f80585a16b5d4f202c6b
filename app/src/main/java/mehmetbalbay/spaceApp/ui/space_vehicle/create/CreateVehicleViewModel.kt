package mehmetbalbay.spaceApp.ui.space_vehicle.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel

class CreateVehicleViewModel : LiveCoroutinesViewModel() {

    private val _checkValueInfoLiveData: MutableLiveData<Int> = MutableLiveData()
    val checkValueInfoLiveData: LiveData<Int> get() = _checkValueInfoLiveData

    private val _isValidValuesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isValidValuesLiveData: LiveData<Boolean> get() = _isValidValuesLiveData

    // Property Values LiveData
    private val _durabilityPointLiveData: MutableLiveData<Int> = MutableLiveData()
    val durabilityPointLiveData: LiveData<Int> get() = _durabilityPointLiveData

    private val _speedPointLiveData: MutableLiveData<Int> = MutableLiveData()
    val speedPointLiveData: LiveData<Int> get() = _speedPointLiveData

    private val _capacityPointLiveData: MutableLiveData<Int> = MutableLiveData()
    val capacityPointLiveData: LiveData<Int> get() = _capacityPointLiveData

    private val _totalPointLiveData: MutableLiveData<String> = MutableLiveData()
    val totalPointLiveData: LiveData<String> get() = _totalPointLiveData

    var vehicleName: String? = null

    val maxPoint: Int = 15

    val damageCapacity = 100

    var durabilityPoint: Int = 0
    var speedPoint: Int = 0
    var capacityPoint: Int = 0

    fun isSelectableDurabilityProgress(durabilityProgress: Int): Boolean {
        return if ((durabilityProgress + speedPoint + capacityPoint) <= maxPoint) {
            durabilityPoint = durabilityProgress
            postDurabilityPoint(durabilityProgress)
            true
        } else {
            false
        }
    }

    fun isSelectableSpeedProgress(speedProgress: Int): Boolean {
        return if ((durabilityPoint + speedProgress + capacityPoint) <= maxPoint) {
            speedPoint = speedProgress
            postSpeedPoint(speedProgress)
            true
        } else {
            false
        }
    }

    fun isSelectableCapacityProgress(capacityProgress: Int): Boolean {
        return if ((durabilityPoint + speedPoint + capacityProgress) <= maxPoint) {
            capacityPoint = capacityProgress
            postCapacityPoint(capacityProgress)
            true
        } else {
            false
        }
    }

    private fun isValidPoints(): Boolean {
        return (durabilityPoint != 0 &&
                speedPoint != 0 &&
                capacityPoint != 0 &&
                (durabilityPoint + speedPoint + capacityPoint) == maxPoint)
    }

    private fun isValidVehicleName(): Boolean {
        return !vehicleName.isNullOrEmpty()
    }

    fun checkAllValue() {
        if (!isValidVehicleName()) {
            postCheckValueInfo(R.string.not_empty_vehicle_name)
        } else if (!isValidPoints()) {
            postCheckValueInfo(R.string.info_create_vehicle_validation)
        } else {
            postIsValidValues(true)
        }
    }

    private fun postIsValidValues(isValid: Boolean) {
        _isValidValuesLiveData.postValue(isValid)
    }

    private fun postCheckValueInfo(info: Int) {
        _checkValueInfoLiveData.postValue(info)
    }

    private fun postDurabilityPoint(durabilityProgress: Int) {
        durabilityPoint = durabilityProgress
        _durabilityPointLiveData.postValue(durabilityProgress)
        postTotalPoint("${getTotalPoint()} / $maxPoint")
    }

    private fun postSpeedPoint(speedProgress: Int) {
        speedPoint = speedProgress
        _speedPointLiveData.postValue(speedProgress)
        postTotalPoint("${getTotalPoint()} / $maxPoint")
    }

    private fun postCapacityPoint(capacityProgress: Int) {
        capacityPoint = capacityProgress
        _capacityPointLiveData.postValue(capacityProgress)
        postTotalPoint("${getTotalPoint()} / $maxPoint")
    }

    private fun getTotalPoint(): Int = (durabilityPoint + speedPoint + capacityPoint)

    fun postTotalPoint(point: String) {
        _totalPointLiveData.postValue(point)
    }
}