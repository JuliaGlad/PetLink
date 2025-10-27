package petlink.android.petlink.ui.calendar.calendar_view.month_view.recycler_view

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.petlink.R
import petlink.android.petlink.databinding.RecyclerViewCalendarDayBinding
import petlink.android.petlink.ui.calendar.calendar_view.month_view.recycler_view.CalendarDayAdapter.ViewHolder
import java.util.Calendar
import kotlin.math.ceil

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
        setItemSize(holder)
        holder.bind(getItem(position))
    }

    class ViewHolder(val binding: RecyclerViewCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CalendarDayModel) {
            with(binding) {
                day.text = model.day
                events.removeAllViews()
                if (model.day.isNotEmpty() && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == model.day.toInt()){
                    binding.day.background = ResourcesCompat.getDrawable(itemView.resources, R.drawable.bg_calendar_today, itemView.context.theme)
                    day.setTextColor(ResourcesCompat.getColor(itemView.resources, R.color.white, itemView.context.theme))
                    day.typeface = ResourcesCompat.getFont(itemView.context, R.font.roboto_bold)
                }
                if (model.events.isNotEmpty()) {
                    val density = binding.root.resources.displayMetrics.density
                    val dotSize = (8 * density).toInt()
                    val marginSize = (2 * density).toInt()
                    val maxDots = 3

                    model.events
                        .take(maxDots)
                        .forEach { event ->
                            val eventTheme =
                                CalendarEventTheme.entries.filter { it.value.id == event.theme.toInt() }[0]

                            val dot = View(binding.root.context).apply {
                                val size = dotSize
                                val margin = marginSize
                                layoutParams = ViewGroup.MarginLayoutParams(size, size)
                                    .apply { rightMargin = margin }
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

    private fun setItemSize(holder: ViewHolder) {
        val layoutParams = holder.itemView.layoutParams
        val displayMetrics = holder.itemView.context.resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val totalDays = currentList.size
        val rowCount = ceil(totalDays / 7.0).toInt().coerceAtLeast(5)
        val headerHeightPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 48f, displayMetrics
        )
        val verticalPaddingPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 16f, displayMetrics
        )
        val availableHeight = screenHeight - headerHeightPx - verticalPaddingPx
        layoutParams.height = (availableHeight / rowCount).toInt()
        holder.itemView.layoutParams = layoutParams
    }


}