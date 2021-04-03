package mehmetbalbay.spaceApp.ui.main

import mehmetbalbay.spaceApp.base.LiveCoroutinesViewModel
import mehmetbalbay.spaceApp.data.repository.SpaceStationRepository
import timber.log.Timber

class MainViewModel : LiveCoroutinesViewModel() {

    init {
        Timber.d("Injection MainViewModel")
    }
}