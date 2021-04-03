package mehmetbalbay.spaceApp.ui.adapter.station

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.enums.TravelStationType
import mehmetbalbay.spaceApp.ui.adapter.viewholder.BaseStationViewHolder
import mehmetbalbay.spaceApp.ui.adapter.viewholder.TravelStationViewHolder
import java.util.*

class TravelStationAdapter(
    private val setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
    private val setOnFavoriteClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
) : RecyclerView.Adapter<BaseStationViewHolder<*>>() {

    private var spaceStations: List<SpaceStation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStationViewHolder<*> =
        when (viewType) {
            TravelStationType.NORMAL.id -> TravelStationViewHolder(parent)
            else -> TravelStationViewHolder(parent)
        }

    override fun getItemCount(): Int = spaceStations.size

    override fun onBindViewHolder(holder: BaseStationViewHolder<*>, position: Int) {
        when (holder) {
            is TravelStationViewHolder -> holder.bind(
                spaceStations[position],
                position,
                setOnClickListener,
                setOnFavoriteClickListener
            )
        }
    }

    fun setData(data: List<SpaceStation>?) {
        if (data != null) {
            this.spaceStations = data
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TravelStationType.NORMAL.id
    }
}