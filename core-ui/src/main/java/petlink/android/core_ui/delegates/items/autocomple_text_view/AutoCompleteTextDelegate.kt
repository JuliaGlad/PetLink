package petlink.android.core_ui.delegates.items.autocomple_text_view

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateAutoCompleteTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem


class AutoCompleteTextDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateAutoCompleteTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as AutoCompleteTextModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is AutoCompleteTextDelegateItem

    class ViewHolder(private val binding: DelegateAutoCompleteTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AutoCompleteTextModel){
            with(binding.autoComplete) {
                hint = model.hint
                threshold = THRESHOLD
                setText(model.defaultValue)
                val adapter: ArrayAdapter<String?> = ArrayAdapter(
                    itemView.context,
                    R.layout.simple_dropdown_item_1line,
                    model.values
                )
                setAdapter(adapter)
                addTextChangedListener(
                    onTextChanged = model.textChangedListener
                )
            }
        }

        companion object{
            const val THRESHOLD = 3
        }
    }
}