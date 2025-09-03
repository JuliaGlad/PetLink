package petlink.android.core_ui.delegates.items.flexbox

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.flexbox.FlexBoxLayout
import petlink.android.core_ui.custom_view.flexbox_item.FlexboxItemView
import petlink.android.core_ui.databinding.DelegateFlexboxBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.listener.ClickIntegerListener
import kotlin.random.Random

class FlexboxDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateFlexboxBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as FlexboxModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is FlexboxDelegateItem

    class ViewHolder(private val binding: DelegateFlexboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FlexboxModel) {
            binding.flexboxLayout.alignment = model.alignment
            binding.flexboxLayout.removeAllViews()
            model.items.forEach {
                val isChosen = model.defaultValue.contains(it)
                binding.flexboxLayout.addFlexItem(
                    title = it,
                    model = model,
                    isChosen = isChosen
                )
            }
        }

        private fun FlexBoxLayout.addFlexItem(
            title: String,
            model: FlexboxModel,
            isChosen: Boolean = false
        ) {
            val view = FlexboxItemView(itemView.context)
            val params = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            with(view) {
                this.isChosen = isChosen
                viewId = Random.nextInt()
                text = title
                background = ResourcesCompat.getDrawable(
                    resources, R.drawable.bg_flexbox_item_unselected,
                    context.theme
                )
                textColor =
                    ResourcesCompat.getColor(resources, R.color.black, itemView.context.theme)
                foreground =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ripple_left_chooser,
                        context.theme
                    )
            }
            if (model.chosenItemListener != null) {
                with(view) {
                    view.clickListener =
                        object : ClickIntegerListener {
                            override fun onClick(int: Int) {
                                if (!model.isMultiply) {
                                    binding.flexboxLayout.children.forEach {
                                        (it as FlexboxItemView)
                                        if (it.viewId != int) {
                                            it.isChosen = false
                                        }
                                    }
                                }
                                model.chosenItemListener(title)
                            }
                        }
                    onViewClicked()
                }
            }
            addView(view, childCount, params)
        }
    }
}