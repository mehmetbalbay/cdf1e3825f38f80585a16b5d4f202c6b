package mehmetbalbay.spaceApp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivitySplashBinding
import mehmetbalbay.spaceApp.utils.Const
import mehmetbalbay.spaceApp.utils.SharedPreferenceHelper
import mehmetbalbay.spaceApp.utils.startCreateVehicleActivity
import mehmetbalbay.spaceApp.utils.startMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : DatabindingActivity() {

    private val binding: ActivitySplashBinding by binding(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Glide.with(this).load(R.raw.space).into(binding.spaceIconImage)

        Handler(Looper.getMainLooper()).postDelayed({
            this.finish()
            val isSaveVehicleData = SharedPreferenceHelper.getSharedData<Boolean>(Const.IS_VEHICLE_SAVE)
            if (isSaveVehicleData == true) {
                startMainActivity()
            } else {
                startCreateVehicleActivity()
            }
        }, Const.SPLASH_DURATION)
    }
}