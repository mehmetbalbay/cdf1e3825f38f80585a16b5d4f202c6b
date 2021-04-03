package mehmetbalbay.spaceApp.di

import androidx.room.Room
import mehmetbalbay.spaceApp.data.local.AppDatabase
import mehmetbalbay.spaceApp.utils.Const
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            Const.DB_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<AppDatabase>().spaceStationsDao()
    }
}