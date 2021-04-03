package mehmetbalbay.spaceApp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import mehmetbalbay.spaceApp.data.local.dao.SpaceStationsDao
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation

@Database(
    entities = [
        SpaceStation::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun spaceStationsDao(): SpaceStationsDao
}