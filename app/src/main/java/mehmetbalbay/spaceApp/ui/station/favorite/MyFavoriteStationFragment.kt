package mehmetbalbay.spaceApp.ui.station.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingFragment
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.FragmentMyFavoriteBinding
import mehmetbalbay.spaceApp.extension.gone
import mehmetbalbay.spaceApp.extension.visible
import mehmetbalbay.spaceApp.ui.adapter.station.favorite.FavoriteStationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MyFavoriteStationFragment : DatabindingFragment() {

    private lateinit var binding: FragmentMyFavoriteBinding
    private val viewModel: MyFavoriteStationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentMyFavoriteBinding>(
            inflater, R.layout.fragment_my_favorite, container
        ).apply {
            binding = this
            viewModel = this@MyFavoriteStationFragment.viewModel
            lifecycleOwner = this@MyFavoriteStationFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        observeMyFavoriteStationsLiveData()
    }

    private fun initializeUI() {

    }

    private fun observeMyFavoriteStationsLiveData() {
        this.viewModel.spaceStationsLiveData.observe(viewLifecycleOwner, { myFavoriteStations ->
            myFavoriteStations?.let {
                if (it.isNotEmpty()) {
                    setFavoriteStationAdapter(it)
                    binding.myFavoriteRecycler.visible()
                    binding.noResultMessage.gone()
                } else {
                    binding.myFavoriteRecycler.gone()
                    binding.noResultMessage.visible()
                }
            }
        })
    }

    private fun setFavoriteStationAdapter(data: List<SpaceStation>) {
        val favoriteStationAdapter = FavoriteStationAdapter { spaceStation, position ->
            Timber.d("Click")
        }
        binding.myFavoriteRecycler.adapter = favoriteStationAdapter
        favoriteStationAdapter.setData(data)
    }
}