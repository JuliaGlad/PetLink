package petlink.android.core_ui.custom_view.description_button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import petlink.android.core_ui.R

class DescriptionButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var iconView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    var descriptionText: String = ""
        set(value) {
            if (value != field){
                field = value
                descriptionTextView.text = field
            }
        }
    var titleText: String = ""
        set(value) {
            if (value != field){
                field = value
                titleTextView.text = field
            }
        }
    var icon: Drawable? = null
        set(value) {
            if (value != field){
                field = value
                iconView.setImageDrawable(field)
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.description_button_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.DescriptionButton) {
            background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.bg_button,
                context.theme
            )
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
        iconView = findViewById(R.id.button_icon_view)
        icon = getDrawable(R.styleable.DescriptionButton_icon)
        iconView.setImageDrawable(icon)
    }

    private fun TypedArray.initDescription() {
        descriptionTextView = findViewById(R.id.description_text_view)
        descriptionText = getString(R.styleable.DescriptionButton_description).toString()
        descriptionTextView.text = descriptionText
    }

    private fun TypedArray.initTitle() {
        titleTextView = findViewById(R.id.title_text_view)
        titleText = getString(R.styleable.DescriptionButton_title).toString()
        titleTextView.text = titleText
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(iconView, widthMeasureSpec, heightMeasureSpec)

        val maxTextWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - iconView.measuredWidth - SPACING
        val childWidthSpec = MeasureSpec.makeMeasureSpec(maxTextWidth, MeasureSpec.AT_MOST)

        titleTextView.measure(childWidthSpec, heightMeasureSpec)
        descriptionTextView.measure(childWidthSpec, heightMeasureSpec)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + iconView.measuredWidth + maxOf(
                titleTextView.measuredWidth, descriptionTextView.measuredWidth
            ) + SPACING,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + maxOf(
                iconView.measuredHeight,
                titleTextView.measuredHeight + descriptionTextView.measuredHeight
            ) + LINE_SPACING,
            heightMeasureSpec
        )
        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val iconTop = (height - iconView.measuredHeight) / 2
        val iconRight = paddingLeft + iconView.measuredWidth
        val iconBottom = iconTop + iconView.measuredHeight
        iconView.layout(paddingLeft, iconTop, iconRight, iconBottom)
        val titleLeft = iconRight + SPACING
        val titleRight = titleLeft + titleTextView.measuredWidth
        val titleBottom = paddingTop + titleTextView.measuredHeight
        titleTextView.layout(titleLeft, paddingTop, titleRight, titleBottom)
        val descriptionLeft = iconRight + SPACING
        val descriptionRight = descriptionLeft + descriptionTextView.measuredWidth
        val descriptionTop = titleBottom + LINE_SPACING
        val descriptionBottom = descriptionTop + descriptionTextView.measuredHeight
        descriptionTextView.layout(descriptionLeft, descriptionTop, descriptionRight, descriptionBottom)
    }

    companion object{
        const val SPACING = 20
        const val LINE_SPACING = 8
    }
}