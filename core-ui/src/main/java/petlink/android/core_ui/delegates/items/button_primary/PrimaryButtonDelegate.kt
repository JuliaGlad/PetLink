package petlink.android.core_ui.delegates.items.button_primary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateButtonPrimaryBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class PrimaryButtonDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateButtonPrimaryBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as PrimaryButtonModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is PrimaryButtonDelegateItem

    class ViewHolder(private val binding: DelegateButtonPrimaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PrimaryButtonModel) {
            with(binding.button) {
                text = model.title
                setOnClickListener { model.click() }
            }
        }
    }
}