package petlink.android.feature_community_ui_main.recycler.item

import androidx.recyclerview.widget.DiffUtil

class MenuItemCallback: DiffUtil.ItemCallback<MenuItemModel>() {
    override fun areItemsTheSame(
        oldItem: MenuItemModel,
        newItem: MenuItemModel
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: MenuItemModel,
        newItem: MenuItemModel
    ): Boolean = newItem == oldItem
}