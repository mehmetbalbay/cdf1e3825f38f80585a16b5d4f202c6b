package mehmetbalbay.spaceApp.utils

import android.content.Context
import android.content.Intent
import mehmetbalbay.spaceApp.ui.main.MainActivity
import mehmetbalbay.spaceApp.ui.space_vehicle.create.CreateVehicleActivity

fun Context.startCreateVehicleActivity() =
    Intent(this, CreateVehicleActivity::class.java).also {
        startActivity(it)
    }

fun Context.startMainActivity() =
    Intent(this, MainActivity::class.java).also {
        startActivity(it)
    }