package mehmetbalbay.spaceApp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.data.network.response.SpaceStationsResponse

@Dao
interface SpaceStationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpaceStations(spaceStations: List<SpaceStation>)

    @Query("SELECT * FROM SpaceStation")
    fun getSpaceStations() : LiveData<List<SpaceStation>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpaceStation(spaceStation: SpaceStation)

    @Query("SELECT * FROM SpaceStation WHERE name = :name_")
    fun getSpaceStation(name_: String): SpaceStation
}