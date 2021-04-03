package mehmetbalbay.spaceApp.ui.space_vehicle.create

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.Toast
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivityCreateVehicleBinding
import mehmetbalbay.spaceApp.extension.snack
import mehmetbalbay.spaceApp.utils.Const
import mehmetbalbay.spaceApp.utils.SharedPreferenceHelper
import mehmetbalbay.spaceApp.utils.startMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class CreateVehicleActivity : DatabindingActivity() {

    private val binding: ActivityCreateVehicleBinding by binding(R.layout.activity_create_vehicle)
    private val viewModel: CreateVehicleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel = this@CreateVehicleActivity.viewModel
            lifecycleOwner = this@CreateVehicleActivity
        }
        initializeUI()
        setClickListener()

        observeCheckValueInfoLiveData()
        observeIsValidValuesLiveData()

        observeDurabilityPointLiveData()
        observeSpeedPointLiveData()
        observeCapacityPointLiveData()

        setVehicleNameTextListener()

        setDurabilitySeekBarListener()
        setSpeedSeekBarListener()
        setCapacitySeekBarListener()
    }

    private fun initializeUI() {
        setMaxSeekBars()
        setInitialPointTextValues()
        setInitialTotalPoint()
    }

    private fun setInitialTotalPoint() {
        viewModel.postTotalPoint("0 / ${viewModel.maxPoint}")
    }

    private fun setInitialPointTextValues() {
        val durabilityTxt = "${getString(R.string.durability)} : ${viewModel.durabilityPoint}"
        val speedTxt = "${getString(R.string.speed)} : ${viewModel.speedPoint}"
        val capacityTxt = "${getString(R.string.capacity)} : ${viewModel.capacityPoint}"
        binding.durabilityTv.text = durabilityTxt
        binding.speedTv.text = speedTxt
        binding.capacityTv.text = capacityTxt
    }

    private fun setMaxSeekBars() {
        binding.durabilitySB.max = viewModel.maxPoint
        binding.speedSB.max = viewModel.maxPoint
        binding.capacitySB.max = viewModel.maxPoint
    }

    private fun observeCheckValueInfoLiveData() {
        this.viewModel.checkValueInfoLiveData.observe(this, { info ->
            info?.let {
                binding.root.snack(it, Const.SNACK_BAR_DURATION, f = { })
            }
        })
    }

    private fun observeIsValidValuesLiveData() {
        this.viewModel.isValidValuesLiveData.observe(this, { _ ->
            saveVehicleData()
            navigateMainActivity()
        })
    }

    private fun observeDurabilityPointLiveData() {
        viewModel.durabilityPointLiveData.observe(this, { durability ->
            durability?.let {
                val durabilityTxt = "${getString(R.string.durability)} : $it"
                binding.durabilityTv.text = durabilityTxt
            }
        })
    }

    private fun observeSpeedPointLiveData() {
        viewModel.speedPointLiveData.observe(this, { speed ->
            speed?.let {
                val speedTxt = "${getString(R.string.speed)} : $it"
                binding.speedTv.text = speedTxt
            }
        })
    }

    private fun observeCapacityPointLiveData() {
        viewModel.capacityPointLiveData.observe(this, { capacity ->
            capacity?.let {
                val capacityTxt = "${getString(R.string.capacity)} : $it"
                binding.capacityTv.text = capacityTxt
            }
        })
    }

    private fun saveVehicleData() {
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_NAME, viewModel.vehicleName)
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_DURATION, viewModel.durabilityPoint)
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_SPEED, viewModel.speedPoint)
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_CAPACITY, viewModel.capacityPoint)
        SharedPreferenceHelper.saveSharedData(Const.VEHICLE_DAMAGE_CAPACITY, viewModel.damageCapacity)
        SharedPreferenceHelper.saveSharedData(Const.IS_VEHICLE_SAVE, true)
    }

    private fun navigateMainActivity() {
        this.finish()
        startMainActivity()
    }

    private fun setClickListener() {
        binding.goOnBtn.setOnClickListener {
            viewModel.checkAllValue()
        }
    }

    private fun setVehicleNameTextListener() {
        binding.spaceVehicleEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.trim()?.let {
                    viewModel.vehicleName = it
                }
            }
        })
    }

    private fun setDurabilitySeekBarListener() {
        binding.durabilitySB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!viewModel.isSelectableDurabilityProgress(progress)) {
                    Handler(Looper.getMainLooper()).post {
                        binding.durabilitySB.progress = viewModel.durabilityPoint
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun setSpeedSeekBarListener() {
        binding.speedSB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!viewModel.isSelectableSpeedProgress(progress)) {
                    Handler(Looper.getMainLooper()).post {
                        binding.speedSB.progress = viewModel.speedPoint
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun setCapacitySeekBarListener() {
        binding.capacitySB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!viewModel.isSelectableCapacityProgress(progress)) {
                    Handler(Looper.getMainLooper()).post {
                        binding.capacitySB.progress = viewModel.capacityPoint
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}