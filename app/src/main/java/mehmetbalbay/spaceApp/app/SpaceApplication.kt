package mehmetbalbay.spaceApp.app

import android.app.Application
import com.orhanobut.hawk.Hawk
import mehmetbalbay.spaceApp.BuildConfig
import mehmetbalbay.spaceApp.di.networkModule
import mehmetbalbay.spaceApp.di.repositoryModule
import mehmetbalbay.spaceApp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SpaceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initHawk()
        initializeTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@SpaceApplication)
            modules(listOf(
                networkModule,
                repositoryModule,
                viewModelModule
            ))
        }
    }

    private fun initHawk() {
        Hawk.init(applicationContext).build()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}