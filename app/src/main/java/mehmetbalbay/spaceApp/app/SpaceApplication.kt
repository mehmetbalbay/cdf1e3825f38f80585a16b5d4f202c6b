package mehmetbalbay.spaceApp.app

import android.app.Application
import com.orhanobut.hawk.Hawk
import mehmetbalbay.spaceApp.BuildConfig
import timber.log.Timber

class SpaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Hawk.init(applicationContext).build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}