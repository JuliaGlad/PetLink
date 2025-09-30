package petlink.android.core_ui.delegates.items.theme_chooser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.core_ui.custom_view.calendar_event.EventTheme
import petlink.android.core_ui.databinding.DelegateThemeChooserBinding
import petlink.android.core_ui.delegates.items.theme_chooser.theme.ThemeAdapter
import petlink.android.core_ui.delegates.items.theme_chooser.theme.ThemeModel
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ThemeChooserDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateThemeChooserBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ThemeChooserModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ThemeChooserDelegateItem

    class ViewHolder(private val binding: DelegateThemeChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val recyclerItems: MutableList<ThemeModel> = mutableListOf()
        val adapter: ThemeAdapter = ThemeAdapter()

        fun bind(model: ThemeChooserModel) {
            model.items.forEach { item ->
                val isChosen = recyclerItems.lastIndex + 1 == model.defaultChosenId
                recyclerItems.add(
                    ThemeModel(
                        theme = item,
                        isChosen = isChosen,
                        clickListener = {
                            recyclerItems.forEach { recyclerItem ->
                                recyclerItem.isChosen = !recyclerItem.isChosen
                                adapter.notifyItemChanged(recyclerItems.indexOf(recyclerItem))
                            }
                        }
                    )
                )
            }
            binding.recyclerView.adapter = adapter
            adapter.submitList(recyclerItems)
        }
    }
}