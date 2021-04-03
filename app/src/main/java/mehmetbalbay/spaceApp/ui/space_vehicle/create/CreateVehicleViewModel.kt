package mehmetbalbay.spaceApp.ui.space_vehicle.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel

class CreateVehicleViewModel : LiveCoroutinesViewModel() {

    private val _distributePointLiveData: MutableLiveData<Int> = MutableLiveData()
    val distributePointLiveData: LiveData<Int> get() = _distributePointLiveData

    private val _checkValueInfoLiveData: MutableLiveData<String> = MutableLiveData()
    val checkValueInfoLiveData: LiveData<String> get() = _checkValueInfoLiveData

    private val _isValidValuesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isValidValuesLiveData: LiveData<Boolean> get() = _isValidValuesLiveData

    var vehicleName: String? = null

    val totalPoint: Int = 15

    var distributePoint: Int = 15

    var durabilityPoint: Int = 0
    var speedPoint: Int = 0
    var capacityPoint: Int = 0

    fun isSelectableDurabilityProgress(durabilityProgress: Int): Boolean {
        return if ((durabilityProgress + speedPoint + capacityPoint) <= totalPoint) {
            durabilityPoint = durabilityProgress
            true
        } else {
            false
        }
    }

    fun isSelectableSpeedProgress(speedProgress: Int): Boolean {
        return if ((durabilityPoint + speedProgress + capacityPoint) <= totalPoint) {
            speedPoint = speedProgress
            true
        } else {
            false
        }
    }

    fun isSelectableCapacityProgress(capacityProgress: Int): Boolean {
        return if ((durabilityPoint + speedPoint + capacityProgress) <= totalPoint) {
            capacityPoint = capacityProgress
            true
        } else {
            false
        }
    }

    fun isValidPoints(): Boolean {
        return (durabilityPoint != 0 && speedPoint != 0 && capacityPoint != 0 && (durabilityPoint + speedPoint + capacityPoint) == totalPoint)
    }

    private fun isValidVehicleName(): Boolean {
        return !vehicleName.isNullOrEmpty()
    }

    fun checkAllValue() {
        if (!isValidVehicleName()) {
            postCheckValueInfo("Uzay araç ismi boş geçilemez.")
        } else if (!isValidPoints()) {
            postCheckValueInfo("Değerler sıfır olamaz veya 3 özelliğin toplamı 15 olmalı")
        } else {
            postIsValidValues(true)
        }
    }

    private fun postIsValidValues(isValid: Boolean) {
        _isValidValuesLiveData.postValue(isValid)
    }

    private fun postCheckValueInfo(info: String) {
        _checkValueInfoLiveData.postValue(info)
    }

    fun postDistributePoint(point: Int) {
        _distributePointLiveData.postValue(point)
    }
}