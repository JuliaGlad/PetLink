package petlink.android.core_ui.delegates.items.switch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateSwitchBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class SwitchDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateSwitchBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as SwitchModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is SwitchDelegateItem

    class ViewHolder(private val binding: DelegateSwitchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SwitchModel) {
            with(binding.switchItem) {
                text = if (isChecked) model.textOn
                else model.textOff
                setOnClickListener { model.clickListener(isChecked) }
            }
        }
    }
}