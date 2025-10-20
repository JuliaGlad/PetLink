package petlink.android.petlink.ui.calendar.calendar_view.month_view.recycler_view

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.petlink.databinding.RecyclerViewCalendarDayBinding
import petlink.android.petlink.ui.calendar.calendar_view.month_view.recycler_view.CalendarDayAdapter.ViewHolder

class CalendarDayAdapter : ListAdapter<CalendarDayModel, ViewHolder>(CalendarDayCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            RecyclerViewCalendarDayBinding.inflate(
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


    class ViewHolder(val binding: RecyclerViewCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CalendarDayModel) {
            with(binding) {
                day.text = model.day
                events.removeAllViews()
                if (model.events.isNotEmpty()) {
                    model.events.forEach { event ->
                        val eventTheme =
                            CalendarEventTheme.entries.filter { it.value.id == event.theme.toInt() }[0]
                        val dot = View(binding.root.context).apply {
                            val size = (8 * binding.root.resources.displayMetrics.density).toInt()
                            layoutParams = ViewGroup.MarginLayoutParams(size, size)
                            background = GradientDrawable().apply {
                                shape = GradientDrawable.OVAL
                                setColor(
                                    ResourcesCompat.getColor(
                                        itemView.resources,
                                        eventTheme.value.iconTint,
                                        itemView.context.theme
                                    )
                                )
                            }
                        }
                        binding.events.addView(dot)
                    }
                }
                binding.item.setOnClickListener { model.clickListener?.let { click -> click() } }
            }

        }
    }

}