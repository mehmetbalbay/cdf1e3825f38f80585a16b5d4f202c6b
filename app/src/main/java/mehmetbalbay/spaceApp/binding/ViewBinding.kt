package mehmetbalbay.spaceApp.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import mehmetbalbay.spaceApp.data.local.entity.SpaceStation
import kotlin.math.pow
import kotlin.math.sqrt

@BindingAdapter("distanceToWorld")
fun bindDistanceToWorld(view: TextView, spaceStation: SpaceStation?) {
    spaceStation?.let {
        val distanceToWorld = sqrt(
            (it.coordinateX.minus(0).toDouble().pow(2)) +
                    (it.coordinateY.minus(0).toDouble().pow(2))
        ).toInt()
        val distanceText = "$distanceToWorld EUS"
        view.text = distanceText
    }
}