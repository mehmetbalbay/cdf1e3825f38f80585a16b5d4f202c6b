package mehmetbalbay.spaceApp.ui.station

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingFragment
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.FragmentStationBinding
import mehmetbalbay.spaceApp.extension.gone
import mehmetbalbay.spaceApp.extension.visible
import mehmetbalbay.spaceApp.ui.adapter.station.TravelStationAdapter
import mehmetbalbay.spaceApp.utils.Const
import mehmetbalbay.spaceApp.utils.SharedPreferenceHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class StationFragment : DatabindingFragment() {

    private lateinit var binding: FragmentStationBinding
    private val viewModel: StationViewModel by viewModel()

    var travelStationAdapter: TravelStationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        observeToastLiveData()
        observeSpaceStationsLiveData()
    }

    private fun initializeUI() {
        loadSpaceStations(page = 1)
        with(binding) {
            remainHealth.text = (SharedPreferenceHelper.getSharedData(Const.VEHICLE_DAMAGE_CAPACITY) as Int? ?: 0).toString()
            spaceVehicleName.text = SharedPreferenceHelper.getSharedData(Const.VEHICLE_NAME) as String? ?: ""
        }
    }

    private fun loadSpaceStations(page: Int) = this.viewModel.postSpaceStationPage(page)

    private fun observeSpaceStationsLiveData() {
        this.viewModel.spaceStationsLiveData.observe(viewLifecycleOwner, { spaceStationItems ->
            spaceStationItems?.let {
                binding.travelStationProgress.gone()
                viewModel.spaceStations = it
                setTravelStationAdapter(it)
            }
        })
    }

    private fun setTravelStationAdapter(data: List<SpaceStation>) {
        travelStationAdapter = TravelStationAdapter ({ spaceStation, position ->
            Timber.d("Travel Click")
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

    private fun observeToastLiveData() {
        this.viewModel.toastLiveData.observe(viewLifecycleOwner, { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
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
}