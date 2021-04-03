package mehmetbalbay.spaceApp.ui.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.base.DatabindingActivity
import mehmetbalbay.spaceApp.databinding.ActivitySplashBinding
import mehmetbalbay.spaceApp.utils.startCreateVehicleActivity
import mehmetbalbay.spaceApp.utils.startMainActivity


class SplashActivity : DatabindingActivity() {

    private val binding: ActivitySplashBinding by binding(R.layout.activity_splash)

    val SPLASH_DURATION: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            this.finish()
            startCreateVehicleActivity()
        }, SPLASH_DURATION)
    }
}