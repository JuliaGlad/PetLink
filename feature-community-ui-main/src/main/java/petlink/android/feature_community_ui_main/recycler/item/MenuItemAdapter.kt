package petlink.android.feature_community_ui_main.recycler.item

import android.graphics.LinearGradient
import android.graphics.Shader.TileMode
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.feature_community_ui_main.databinding.RecyclerMenuItemBinding

class MenuItemAdapter : ListAdapter<
        MenuItemModel,
        MenuItemAdapter.ViewHolder>(MenuItemCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        RecyclerMenuItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecyclerMenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MenuItemModel) {
            with(binding) {
                icon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        model.icon,
                        itemView.context.theme
                    )
                )
                with(itemText) {
                    itemText.text = model.text
                    val height = measuredHeight.toFloat()
                    val shader = LinearGradient(
                        0f, 0f, 0f, height + 20,
                        ContextCompat.getColor(context, model.textStartColor),
                        ContextCompat.getColor(context, model.textEndColor),
                        TileMode.CLAMP
                    )
                    paint.shader = shader
                    invalidate()
                }
                val drawable = ContextCompat.getDrawable(
                    itemView.context,
                    petlink.android.feature_community_ui_main.R.drawable.bg_menu_item
                )?.mutate() as GradientDrawable
                drawable.colors = intArrayOf(
                    ContextCompat.getColor(itemView.context, model.bgStartColor),
                    ContextCompat.getColor(itemView.context, model.bgEndColor)
                )
                item.background = drawable
                item.setOnClickListener { model.clickListener }
            }
        }
    }
}