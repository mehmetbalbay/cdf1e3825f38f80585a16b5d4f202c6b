package mehmetbalbay.spaceApp.data.network.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class SpaceStationsResponse : ArrayList<SpaceStationsResponse.SpaceStationsResponseItem>(){

    @Parcelize
    data class SpaceStationsResponseItem(
        @SerializedName("capacity")
        val capacity: Int?,
        @SerializedName("coordinateX")
        val coordinateX: Double?,
        @SerializedName("coordinateY")
        val coordinateY: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("need")
        val need: Int?,
        @SerializedName("stock")
        val stock: Int?
    ): Parcelable
}