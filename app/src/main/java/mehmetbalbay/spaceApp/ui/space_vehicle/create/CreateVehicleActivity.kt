package mehmetbalbay.spaceApp.ui.space_vehicle.create

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.Observer
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivityCreateVehicleBinding
import mehmetbalbay.spaceApp.utils.startMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        setVehicleNameTextListener()

        setDurabilitySeekBarListener()
        setSpeedSeekBarListener()
        setCapacitySeekBarListener()
    }

    private fun initializeUI() {
        this.viewModel.postDistributePoint(this.viewModel.distributePoint)
        setMaxSeekBars()
    }

    private fun setMaxSeekBars() {
        binding.durabilitySB.max = 15
        binding.speedSB.max = 15
        binding.capacitySB.max = 15
    }

    private fun observeCheckValueInfoLiveData() {
        this.viewModel.checkValueInfoLiveData.observe(this, { info ->
            info?.let {
                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun observeIsValidValuesLiveData() {
        this.viewModel.isValidValuesLiveData.observe(this, { isValid ->
            navigateMainActivity()
        })
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