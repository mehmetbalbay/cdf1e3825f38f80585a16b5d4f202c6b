package mehmetbalbay.spaceApp.ui.adapter.station.favorite

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.enums.FavoriteStationType
import mehmetbalbay.spaceApp.ui.adapter.viewholder.BaseViewHolder
import mehmetbalbay.spaceApp.ui.adapter.viewholder.FavoriteStationViewHolder
import java.util.ArrayList

class FavoriteStationAdapter(
    private val setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var movieData: List<SpaceStation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            FavoriteStationType.NORMAL.id -> FavoriteStationViewHolder(parent)
            else -> FavoriteStationViewHolder(parent)
        }

    override fun getItemCount(): Int = movieData.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is FavoriteStationViewHolder -> holder.bind(movieData[position], position, setOnClickListener)
        }
    }

    fun setData(data: List<SpaceStation>?) {
        if (data != null) {
            this.movieData = data
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return FavoriteStationType.NORMAL.id
    }
}