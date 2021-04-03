package mehmetbalbay.spaceApp.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import mehmetbalbay.spaceApp.R
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import mehmetbalbay.spaceApp.databinding.RowFavoriteBinding

class FavoriteStationViewHolder(parent: ViewGroup) : BaseViewHolder<SpaceStation?>(
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
        setOnClickListener: (spaceStation: SpaceStation?, position: Int) -> Unit
    ) {
        item?.let {
            binding.spaceStation = it
        }

        itemView.setOnClickListener {
            item?.let {
                setOnClickListener(item, position)
            }
        }
    }
}