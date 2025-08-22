package petlink.android.core_ui.delegates.items.text.gradient

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.databinding.DelegateSmallTextBinding
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem


class TextGradientDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegateSmallTextBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as TextGradientModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TextGradientDelegateItem

    class ViewHolder(private val binding: DelegateSmallTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TextGradientModel) {
            with(binding.textView) {
                post {
                    val height = measuredHeight.toFloat()

                    val shader = LinearGradient(
                        0f, 0f, 0f, height + 20,
                        ContextCompat.getColor(context, R.color.dark_green_variant),
                        ContextCompat.getColor(context, R.color.dark_green),
                        TileMode.CLAMP
                    )
                    paint.shader = shader
                    invalidate()
                }
                model.clickListener?.let { setOnClickListener { it() } }
                textAlignment = model.textAlignment
                text = model.text
            }
        }
    }
}