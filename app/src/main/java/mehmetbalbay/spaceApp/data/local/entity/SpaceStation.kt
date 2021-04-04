package mehmetbalbay.spaceApp.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class SpaceStation(
    @SerializedName("name")
    @PrimaryKey
    val name: String,
    @SerializedName("capacity")
    val capacity: Int = 0,
    @SerializedName("coordinateX")
    val coordinateX: Int = 0,
    @SerializedName("coordinateY")
    val coordinateY: Int = 0,
    @SerializedName("need")
    var need: Int = 0,
    @SerializedName("stock")
    var stock: Int = 0,
    var isFavorite: Boolean = false,
    var isTraveler: Boolean = true,
    var isCurrentStation: Boolean = false
) : Parcelable