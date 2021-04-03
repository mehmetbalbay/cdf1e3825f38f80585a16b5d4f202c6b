package mehmetbalbay.spaceApp.ui.adapter.station.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.enums.FavoriteStationType
import mehmetbalbay.spaceApp.ui.adapter.viewholder.BaseStationViewHolder
import mehmetbalbay.spaceApp.ui.adapter.viewholder.FavoriteStationViewHolder
import java.util.*

class FavoriteStationAdapter(
    private val setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
    private val setOnFavoriteClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
) : RecyclerView.Adapter<BaseStationViewHolder<*>>() {

    private var spaceStations: List<SpaceStation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseStationViewHolder<*> =
        when (viewType) {
            FavoriteStationType.NORMAL.id -> FavoriteStationViewHolder(parent)
            else -> FavoriteStationViewHolder(parent)
        }

    override fun getItemCount(): Int = spaceStations.size

    override fun onBindViewHolder(holder: BaseStationViewHolder<*>, position: Int) {
        when (holder) {
            is FavoriteStationViewHolder -> holder.bind(
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
        return FavoriteStationType.NORMAL.id
    }
}