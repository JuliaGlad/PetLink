package petlink.android.core_ui.custom_view.icon_button

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
import petlink.android.core_ui.custom_view.ButtonMode
import petlink.android.core_ui.custom_view.description_button.DescriptionButton.Companion.LINE_SPACING
import petlink.android.core_ui.custom_view.description_button.DescriptionButton.Companion.SPACING

class IconButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var iconView: ImageView
    private lateinit var titleTextView: TextView
    var mode: Int = ButtonMode.NORMAL
        set(value) {
            if (value != field){
                field = value
                val backgroundId = if (field == ButtonMode.NORMAL) R.drawable.bg_button
                else R.drawable.bg_button_red
                background = ResourcesCompat.getDrawable(resources, backgroundId, context.theme)
                val textColor = if (field == ButtonMode.WARNING) R.color.red
                else R.color.black
                titleTextView.setTextColor(ResourcesCompat.getColor(resources, textColor, context.theme))
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
        LayoutInflater.from(context).inflate(R.layout.icon_button_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.IconButton) {
            mode = getInt(R.styleable.IconButton_mode, ButtonMode.NORMAL)
            val backgroundId = if (mode == ButtonMode.NORMAL) R.drawable.bg_button
            else R.drawable.bg_button_red
            background = ResourcesCompat.getDrawable(resources, backgroundId, context.theme)
            foreground = ResourcesCompat.getDrawable(resources, R.drawable.ripple_button, context.theme)
            initIcon()
            initTitle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(iconView, widthMeasureSpec, heightMeasureSpec)

        val maxTextWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - iconView.measuredWidth - SPACING
        val childWidthSpec = MeasureSpec.makeMeasureSpec(maxTextWidth, MeasureSpec.AT_MOST)

        titleTextView.measure(childWidthSpec, heightMeasureSpec)
        val actualWidth = resolveSize(
            paddingLeft + paddingRight + iconView.measuredWidth + titleTextView.measuredWidth + SPACING,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + maxOf(iconView.measuredHeight, titleTextView.measuredHeight) + LINE_SPACING,
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
        val titleTop = (height - titleTextView.measuredHeight) / 2
        val titleLeft = iconRight + SPACING
        val titleRight = titleLeft + titleTextView.measuredWidth
        val titleBottom = paddingTop + titleTextView.measuredHeight
        titleTextView.layout(titleLeft, titleTop, titleRight, titleBottom)
    }

    private fun TypedArray.initIcon() {
        iconView = findViewById(R.id.button_icon_view)
        icon = getDrawable(R.styleable.IconButton_android_drawable)
        iconView.setImageDrawable(icon)
    }

    private fun TypedArray.initTitle() {
        titleTextView = findViewById(R.id.title_text_view)
        titleText = getString(R.styleable.IconButton_android_text).toString()
        val textColor = if (mode == ButtonMode.WARNING) R.color.red
        else R.color.black
        with(titleTextView) {
            text = titleText
            setTextColor(ResourcesCompat.getColor(resources, textColor, context.theme))
        }
    }
}