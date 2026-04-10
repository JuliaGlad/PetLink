package petlink.android.feature_community_ui_main.recycler.delegate

import petlink.android.feature_community_ui_main.recycler.item.MenuItemModel
import kotlin.random.Random

data class ListMenuItemsModel(
    val id: Int = Random.nextInt(),
    val items: List<MenuItemModel>
)