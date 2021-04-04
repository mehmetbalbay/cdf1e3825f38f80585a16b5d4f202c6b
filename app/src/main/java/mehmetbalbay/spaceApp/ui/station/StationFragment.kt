package mehmetbalbay.spaceApp.ui.station

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingFragment
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.FragmentStationBinding
import mehmetbalbay.spaceApp.extension.gone
import mehmetbalbay.spaceApp.extension.snack
import mehmetbalbay.spaceApp.extension.visible
import mehmetbalbay.spaceApp.ui.adapter.station.TravelStationAdapter
import mehmetbalbay.spaceApp.utils.Const
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class StationFragment : DatabindingFragment() {

    private lateinit var binding: FragmentStationBinding
    private val viewModel: StationViewModel by viewModel()

    var travelStationAdapter: TravelStationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentStationBinding>(
            inflater, R.layout.fragment_station, container
        ).apply {
            binding = this
            viewModel = this@StationFragment.viewModel
            lifecycleOwner = this@StationFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        setSearchTextListener()

        startTotalDurabilityTimer()

        observeMessages()
        observeSpaceStationsLiveData()
        observeVehicleHealthLiveData()

        observeCurrentStationLiveData()
        observeSpaceSuitCountLiveData()
        observeUniversalSpaceTimeLiveData()
    }

    private fun initializeUI() {
        loadSpaceStations(page = 1)
        setTravelProperties()
        setHealth(viewModel.vehicleHealth)
        setSpaceVehicleName()
    }

    private fun setHealth(health: Int) {
        binding.health.text = health.toString()
    }

    private fun setSpaceVehicleName() {
        binding.spaceVehicleName.text = viewModel.spaceVehicleName
    }

    private fun loadSpaceStations(page: Int) = this.viewModel.postSpaceStationPage(page)

    private fun observeSpaceStationsLiveData() {
        this.viewModel.spaceStationsLiveData.observe(viewLifecycleOwner, { spaceStationItems ->
            spaceStationItems?.let { mSpaceStations ->
                binding.travelStationProgress.gone()
                viewModel.spaceStations = mSpaceStations
                setTravelStationAdapter(mSpaceStations)
            }
        })
    }

    private fun observeVehicleHealthLiveData() {
        this.viewModel.vehicleHealthLiveData.observe(viewLifecycleOwner, { vehicleHealth ->
            vehicleHealth?.let { setHealth(it) }
        })
    }

    private fun observeCurrentStationLiveData() {
        this.viewModel.currentStationLiveData.observe(viewLifecycleOwner, { currentSpaceStation ->
            currentSpaceStation?.let {
                binding.currentStation.text = it.name
            }
        })
    }

    private fun observeSpaceSuitCountLiveData() {
        this.viewModel.spaceSuitCountLiveData.observe(viewLifecycleOwner, { spaceSuitCount ->
            spaceSuitCount?.let {
                val spaceSuitCountText = "${getString(R.string.ugs)}\n $spaceSuitCount"
                binding.ugsTv.text = spaceSuitCountText
            }
        })
    }

    private fun observeUniversalSpaceTimeLiveData() {
        this.viewModel.universalSpaceTimeLiveData.observe(
            viewLifecycleOwner,
            { universalSpaceTime ->
                universalSpaceTime?.let {
                    val universalSpaceTimeText = "${getString(R.string.eus)}\n $universalSpaceTime"
                    binding.eusTv.text = universalSpaceTimeText
                }
            })
    }

    private fun observeMessages() {
        this.viewModel.toastLiveData.observe(viewLifecycleOwner, { message ->
            message?.let {
                binding.root.snack(it, Const.SNACK_BAR_DURATION)
            }
        })
    }

    private fun setTravelStationAdapter(data: List<SpaceStation>) {
        travelStationAdapter = TravelStationAdapter({ spaceStation, _ ->
            spaceStation?.let {
                if (!viewModel.isDeliveryFinished) {
                    viewModel.travel(viewModel.getCurrentStation(), spaceStation)
                    travelStationAdapter?.setData(viewModel.spaceStations)
                    setVisibilityTravelStationViews(viewModel.spaceStations?.isNotEmpty() == true)
                } else {
                    binding.root.snack(getString(R.string.delivery_completed_info), 2000)
                }
            }
        }, { spaceStation, position ->
            spaceStation?.let {
                viewModel.addFavoriteStation(spaceStation = it)
                travelStationAdapter?.notifyItemChanged(position)
            }
        })
        binding.travelStationRecycler.adapter = travelStationAdapter
        travelStationAdapter?.setData(data)
        setVisibilityTravelStationViews(data.isNotEmpty())
    }

    private fun setSearchTextListener() {
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.trim()?.let { searchValue ->
                    val filteredData = viewModel.performSearch(searchValue)
                    setVisibilityTravelStationViews(filteredData.isNotEmpty())
                    travelStationAdapter?.setData(filteredData)
                }
            }
        })
    }

    private fun setVisibilityTravelStationViews(isVisible: Boolean) {
        if (isVisible) {
            binding.travelStationRecycler.visible()
            binding.noFoundStation.gone()
        } else {
            binding.travelStationRecycler.gone()
            binding.noFoundStation.visible()
        }
    }

    private fun setTravelProperties() {
        val ugsText = "${getString(R.string.ugs)}\n${viewModel.spaceSuits}"
        val eusText = "${getString(R.string.eus)}\n${viewModel.universalSpaceTime}"
        val dsText = "${getString(R.string.ds)}\n${viewModel.durabilityTime}"
        binding.ugsTv.text = ugsText
        binding.eusTv.text = eusText
        binding.dsTv.text = dsText
    }

    private fun startTotalDurabilityTimer() {
        if (viewModel.vehicleHealth > 0) {
            object : CountDownTimer(
                (viewModel.durabilityTime).toLong(),
                1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    setDurabilityTime(millisUntilFinished)
                }

                override fun onFinish() {
                    viewModel.takeDamage()
                    if (viewModel.vehicleHealth != 0) {
                        startTotalDurabilityTimer()
                    } else {
                        viewModel.turnToWorld(viewModel.getCurrentStation())
                        viewModel.isDeliveryFinished = true
                    }
                }
            }.start()
        } else {
            setDurabilityTime(viewModel.durabilityTime.toLong())
            viewModel.isDeliveryFinished = true
        }
    }

    private fun setDurabilityTime(millisUntilFinished: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        val minutesToSecond = TimeUnit.MINUTES.toSeconds(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        val timeString = ("" + String.format(
            "%d:%d",
            minutes,
            seconds - minutesToSecond
        ))
        binding.durabilityTime.text = timeString
    }
}