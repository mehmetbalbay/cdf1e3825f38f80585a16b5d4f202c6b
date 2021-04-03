package mehmetbalbay.spaceApp.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : DatabindingActivity() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
        initializeUI()
    }

    private fun initializeUI() {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController)
    }
}