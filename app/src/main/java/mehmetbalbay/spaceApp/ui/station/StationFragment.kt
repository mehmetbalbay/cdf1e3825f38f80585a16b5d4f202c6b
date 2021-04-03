package mehmetbalbay.spaceApp.ui.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingFragment
import mehmetbalbay.spaceApp.databinding.FragmentStationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StationFragment : DatabindingFragment() {

    private lateinit var binding: FragmentStationBinding
    private val viewModel: StationViewModel by viewModel()

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
        observeToastLiveData()
        observeSpaceStationsLiveData()
    }

    private fun initializeUI() {
        loadSpaceStations(page = 1)
    }

    private fun loadSpaceStations(page: Int) = this.viewModel.postSpaceStationPage(page)

    private fun observeSpaceStationsLiveData() {
        this.viewModel.spaceStationsLiveData.observe(viewLifecycleOwner, { spaceStationItems ->
            spaceStationItems?.let {

            }
        })
    }

    private fun observeToastLiveData() {
        this.viewModel.toastLiveData.observe(viewLifecycleOwner, { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
    }
}