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
    val capacity: Int?,
    @SerializedName("coordinateX")
    val coordinateX: Double?,
    @SerializedName("coordinateY")
    val coordinateY: Double?,
    @SerializedName("need")
    val need: Int?,
    @SerializedName("stock")
    val stock: Int?,
    var isFavorite: Boolean = false
) : Parcelable