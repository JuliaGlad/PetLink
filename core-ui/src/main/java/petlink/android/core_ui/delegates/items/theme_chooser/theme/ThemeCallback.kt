package petlink.android.core_ui.delegates.items.theme_chooser.theme

import androidx.recyclerview.widget.DiffUtil

class ThemeCallback: DiffUtil.ItemCallback<ThemeModel>() {
    override fun areItemsTheSame(
        oldItem: ThemeModel,
        newItem: ThemeModel
    ): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(
        oldItem: ThemeModel,
        newItem: ThemeModel
    ): Boolean =
        oldItem == newItem
}