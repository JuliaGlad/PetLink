package petlink.android.core_ui.delegates.items.button_primary_variant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateButtonPrimaryVariantBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class PrimaryButtonVariantDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateButtonPrimaryVariantBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as PrimaryButtonVariantModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is PrimaryButtonVariantDelegateItem

    class ViewHolder(private val binding: DelegateButtonPrimaryVariantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PrimaryButtonVariantModel){
            with(binding.button) {
                text = model.title
                setOnClickListener { model.click() }
            }
        }
    }
}