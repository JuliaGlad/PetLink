package petlink.android.core_ui.delegates.items.description_button

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateDescriptionButtonBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class DescriptionButtonDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateDescriptionButtonBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as DescriptionButtonModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DescriptionButtonDelegateItem

    class ViewHolder(private val binding: DelegateDescriptionButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DescriptionButtonModel) {
            with(binding.item) {
                icon = model.icon
                descriptionText = model.description
                titleText = model.title
                setOnClickListener { model.click() }
            }
        }
    }
}