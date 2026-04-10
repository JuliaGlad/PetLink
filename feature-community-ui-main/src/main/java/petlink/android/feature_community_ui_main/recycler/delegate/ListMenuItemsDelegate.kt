package petlink.android.feature_community_ui_main.recycler.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_di.app.AppComponentHolder.init
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.feature_community_ui_main.databinding.DelegateMenuItemBinding
import petlink.android.feature_community_ui_main.recycler.item.MenuItemAdapter

class ListMenuItemsDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as ListMenuItemsModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ListMenuItemsDelegateItem

    class ViewHolder(
        binding: DelegateMenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val adapter = MenuItemAdapter()

        init {
            binding.recycler.adapter = adapter
        }

        fun bind(model: ListMenuItemsModel) {
            adapter.submitList(model.items)
        }
    }
}