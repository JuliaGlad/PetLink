package petlink.android.core_ui.delegates.items.chooser

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.custom_view.chooser.item.ChooserItemView
import petlink.android.core_ui.custom_view.chooser.main.ChooserView
import petlink.android.core_ui.databinding.DelegateChooserViewBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ChooserViewDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateChooserViewBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ChooserViewModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ChooserViewDelegateItem

    class ViewHolder(private val binding: DelegateChooserViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ChooserViewModel) {
            with(binding.chooserView) {
                initChooser(
                    getChooser1(),
                    model.text1,
                    model.selectedIcon1,
                    model.unselectedIcon1,
                    model.clickListener
                )
                initChooser(
                    getChooser2(),
                    model.text2,
                    model.selectedIcon2,
                    model.unselectedIcon2,
                    model.clickListener
                )
                if (getChooser1().text == model.defaultValue) {
                    getChooser1().chosen = true
                    getChooser2().chosen = false
                } else if (getChooser2().text == model.defaultValue) {
                    getChooser2().chosen = true
                    getChooser1().chosen = false
                }
            }
        }

        private fun initChooser(
            view: ChooserItemView,
            text: String,
            selectedIcon: Drawable?,
            unselectedIcon: Drawable?,
            clickListener: ((String) -> Unit)?
        ) {
            view.apply {
                this.text = text
                iconSelected = selectedIcon
                iconUnselected = unselectedIcon
                view.setOnClickListener {
                    if (binding.chooserView.chosenId != viewId)
                        view.chosen = !view.chosen
                    if (clickListener != null) {
                        binding.chooserView.onViewClicked(viewId)
                        clickListener(text)
                    }
                }

            }
        }
    }
}