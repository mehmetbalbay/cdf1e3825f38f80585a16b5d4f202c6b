package mehmetbalbay.spaceApp.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DatabindingActivity() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    private val viewModel: MainViewModel  by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        initializeUI()
        observeSpaceStationsLiveData()
    }

    private fun initializeUI() {
        loadSpaceStations(page = 1)
    }

    private fun loadSpaceStations(page: Int) = this.viewModel.postSpaceStationPage(page)

    private fun observeSpaceStationsLiveData() {
        this.viewModel.spaceStationsLiveData.observe(this, { spaceStationItems ->
            spaceStationItems?.let {

            }
        })
    }
}