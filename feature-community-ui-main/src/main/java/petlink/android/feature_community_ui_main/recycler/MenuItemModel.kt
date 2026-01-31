package petlink.android.feature_community_ui_main.recycler

import android.graphics.drawable.Drawable
import kotlin.random.Random

data class MenuItemModel(
    val id: Int = Random.nextInt(),
    val icon: Int,
    val text: String,
    val bgStartColor: Int,
    val bgEndColor: Int,
    val textStartColor: Int,
    val textEndColor: Int,
    val clickListener: () -> Unit
)