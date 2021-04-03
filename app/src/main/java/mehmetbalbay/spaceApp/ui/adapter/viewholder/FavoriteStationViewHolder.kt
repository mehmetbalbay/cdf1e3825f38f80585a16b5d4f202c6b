package mehmetbalbay.spaceApp.ui.adapter.viewholder

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.RowFavoriteBinding

class FavoriteStationViewHolder(parent: ViewGroup) : BaseStationViewHolder<SpaceStation?>(
    LayoutInflater.from(parent.context).inflate(
        R.layout.row_favorite,
        parent,
        false
    )
) {

    val binding by bindings<RowFavoriteBinding>(itemView)

    override fun bind(
        item: SpaceStation?,
        position: Int,
        setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
        setOnFavoriteClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit,
    ) {
        item?.let { mItem ->
            with(binding) {
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

                favoriteBtn.setOnClickListener { setOnFavoriteClickListener.invoke(item, position) }
                itemView.setOnClickListener { setOnClickListener.invoke(item, position) }
            }
        }
    }
}