package petlink.android.core_ui.delegates.items.theme_chooser.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.databinding.RecyclerItemThemeBinding
import petlink.android.core_ui.delegates.items.theme_chooser.theme.ThemeAdapter.ViewHolder

class ThemeAdapter : ListAdapter<ThemeModel, ViewHolder>(ThemeCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            RecyclerItemThemeBinding.inflate(
                LayoutInflater.from(parent.context),
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


    class ViewHolder(private val binding: RecyclerItemThemeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ThemeModel) {
            with(binding.theme) {
                backgroundThemeColor = model.theme.value.background
                strokeColor = model.theme.value.iconTint
                isChosen = model.isChosen
                setOnClickListener { model.clickListener() }
            }
        }
    }

}