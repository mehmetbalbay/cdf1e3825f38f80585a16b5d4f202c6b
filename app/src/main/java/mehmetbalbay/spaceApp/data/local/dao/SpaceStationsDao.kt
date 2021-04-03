package mehmetbalbay.spaceApp.data.local.dao

import androidx.room.*
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation

@Dao
interface SpaceStationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceStations(spaceStations: List<SpaceStation>)

    @Query("SELECT * FROM SpaceStation")
    fun getSpaceStations(): List<SpaceStation>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpaceStation(spaceStation: SpaceStation)

    @Query("SELECT * FROM SpaceStation WHERE name = :name_")
    fun getSpaceStation(name_: String): SpaceStation

    @Query("SELECT * FROM SpaceStation WHERE isFavorite = '1'")
    fun getFavoriteSpaceStation(): List<SpaceStation>?
}