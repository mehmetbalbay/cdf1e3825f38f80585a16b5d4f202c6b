package mehmetbalbay.spaceApp.ui.adapter.viewholder

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.RowTravelStationBinding

class TravelStationViewHolder(parent: ViewGroup) : BaseStationViewHolder<SpaceStation?>(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_travel_station,
        parent,
        false
    )
) {

    val binding by bindings<RowTravelStationBinding>(itemView)

    override fun bind(
        item: SpaceStation?,
        position: Int,
        setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
        setOnFavoriteClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
    ) {
        with(binding) {
            item?.let { mItem ->
                spaceStation = mItem

                if (mItem.isFavorite) {
                    favoriteBtn.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.bottom_icon_active
                        )
                    )
                } else {
                    favoriteBtn.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.bottom_icon_passive
                        )
                    )
                }

                travelBtn.setOnClickListener { setOnClickListener.invoke(mItem, position) }
                favoriteBtn.setOnClickListener {
                    setOnFavoriteClickListener.invoke(
                        mItem,
                        position
                    )
                }


                travelBtn.isEnabled = mItem.isTraveler
                if (mItem.isTraveler) {
                    travelStationCard.setCardBackgroundColor(Color.WHITE)
                    travelBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.button
                        )
                    )
                } else {
                    travelStationCard.setCardBackgroundColor(Color.LTGRAY)
                    travelBtn.setBackgroundColor(Color.LTGRAY)
                }
            }
        }
    }
}