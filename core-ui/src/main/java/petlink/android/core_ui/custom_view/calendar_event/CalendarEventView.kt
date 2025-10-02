package petlink.android.core_ui.custom_view.calendar_event

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.description_button.DescriptionButton.Companion.LINE_SPACING
import petlink.android.core_ui.custom_view.description_button.DescriptionButton.Companion.SPACING

class CalendarEventView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var notificationIconView: ImageView
    private lateinit var eventTitleTextView: TextView
    private lateinit var eventDateTextView: TextView

    var isNotificationOn: Boolean = false
        set(value) {
            if (value != field){
                field = value
                val iconId = if (field)R.drawable.ic_notification_on
                else R.drawable.ic_notification_off

                notificationIconView.setImageDrawable(ResourcesCompat.getDrawable(resources, iconId, context?.theme))
            }
        }

    var eventTheme: CalendarEventTheme = CalendarEventTheme.GREEN
        set(value) {
            if (value != field){
                field = value
                invalidate()
            }
        }

    var eventDateText: String = ""
        set(value) {
            if (value != field){
                field = value
                eventDateTextView.text = field
            }
        }
    var eventTitle: String = ""
        set(value) {
            if (value != field){
                field = value
                eventTitleTextView.text = field
            }
        }


    init {
        LayoutInflater.from(context).inflate(R.layout.layout_calendar_event, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.CalendarEventView) {
            val backgroundGradientDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_button,
                context.theme
            ) as GradientDrawable
            backgroundGradientDrawable.setColor(ResourcesCompat.getColor(resources, eventTheme.value.background, context.theme))
            background = backgroundGradientDrawable
            foreground = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ripple_button,
                context.theme
            )
            initIcon()
            initDescription()
            initTitle()
        }
    }

    private fun TypedArray.initIcon() {
        notificationIconView = findViewById(R.id.notification_icon)
        isNotificationOn = getBoolean(R.styleable.CalendarEventView_is_notification_on, false)
        val iconId = if (isNotificationOn)R.drawable.ic_notification_on
        else R.drawable.ic_notification_off

        notificationIconView.setImageDrawable(ResourcesCompat.getDrawable(resources, iconId, context?.theme))
        notificationIconView.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, eventTheme.value.iconTint, context.theme))
    }

    private fun TypedArray.initDescription() {
        eventDateTextView = findViewById(R.id.event_date)
        eventDateText = getString(R.styleable.CalendarEventView_event_date).toString()
        eventDateTextView.text = eventDateText
        eventDateTextView.setTextColor(ResourcesCompat.getColor(resources, eventTheme.value.descriptionTextColor, context.theme))
    }

    private fun TypedArray.initTitle() {
        eventTitleTextView = findViewById(R.id.event_title)
        eventTitle = getString(R.styleable.CalendarEventView_event_title).toString()
        eventTitleTextView.text = eventTitle
        eventTitleTextView.setTextColor(ResourcesCompat.getColor(resources, eventTheme.value.titleTextColor, context.theme))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(notificationIconView, widthMeasureSpec, heightMeasureSpec)

        val maxTextWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - notificationIconView.measuredWidth - SPACING
        val childWidthSpec = MeasureSpec.makeMeasureSpec(maxTextWidth, MeasureSpec.AT_MOST)

        eventTitleTextView.measure(childWidthSpec, heightMeasureSpec)
        eventDateTextView.measure(childWidthSpec, heightMeasureSpec)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + notificationIconView.measuredWidth + maxOf(
                eventTitleTextView.measuredWidth, eventDateTextView.measuredWidth
            ) + SPACING,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + maxOf(
                notificationIconView.measuredHeight,
                eventTitleTextView.measuredHeight + eventDateTextView.measuredHeight
            ) + LINE_SPACING,
            heightMeasureSpec
        )
        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onLayout(
        p0: Boolean,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int
    ) {
        val titleLeft = paddingLeft
        val titleRight = titleLeft + eventTitleTextView.measuredWidth
        val titleBottom = paddingTop + eventTitleTextView.measuredHeight
        eventTitleTextView.layout(titleLeft, paddingTop, titleRight, titleBottom)
        val descriptionLeft = paddingLeft
        val descriptionRight = descriptionLeft + eventDateTextView.measuredWidth
        val descriptionTop = titleBottom + SPACING_VERTICAL
        val descriptionBottom = descriptionTop + eventDateTextView.measuredHeight
        eventDateTextView.layout(descriptionLeft, descriptionTop, descriptionRight, descriptionBottom)
        val iconTop = (height - notificationIconView.measuredHeight) / 2
        val iconRight = measuredWidth - paddingRight
        val iconLeft = iconRight - notificationIconView.measuredWidth
        val iconBottom = iconTop + notificationIconView.measuredHeight
        notificationIconView.layout(iconLeft, iconTop, iconRight, iconBottom)
    }

    companion object{
        const val SPACING_HORIZONTAL = 14
        const val SPACING_VERTICAL = 8
    }

}