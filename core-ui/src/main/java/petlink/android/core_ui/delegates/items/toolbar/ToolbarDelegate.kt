package petlink.android.core_ui.delegates.items.toolbar

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.DelegateToolbarBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem

class ToolbarDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateToolbarBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as ToolbarModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean =
        item is ToolbarDelegateItem

    class ViewHolder(private val binding: DelegateToolbarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ToolbarModel) {
            with(binding) {
                if (model.leftIcon != null && model.leftIconClick != null) {
                    setIcon(leftButton, model.leftIcon, model.leftIconClick)
                } else leftButton.visibility = View.GONE

                if (model.rightIcon != null && model.rightIconClick != null) {
                    setIcon(rightButton, model.rightIcon, model.rightIconClick)
                } else rightButton.visibility = View.GONE

                text.text = model.title
            }
        }

        private fun setIcon(iconView: ImageButton, icon: Drawable, click: () -> Unit){
            with(iconView){
                setImageDrawable(icon)
                setOnClickListener { click() }
            }
        }
    }
}