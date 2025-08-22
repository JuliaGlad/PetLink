package petlink.android.core_ui.custom_view.chooser.item

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginLeft
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.LayoutAlignment
import kotlin.random.Random

class ChooserItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    val viewId: Int = Random.nextInt()
    private lateinit var iconView: ImageView
    private lateinit var textView: TextView

    var text: String = "empty"
        set(value) {
            if (field != value) {
                field = value
                textView.text = field
            }
        }
    var textSize: Float = 14f
    private var textColor: Int = context.getColor(R.color.black)
    private var typeFace: Typeface? = null

    var iconSelected: Drawable? = null
        set(value) {
            if (value != field){
                field = value
                if (chosen) iconView.setImageDrawable(field)
            }
        }
    var iconUnselected: Drawable? = null
        set(value) {
            if (value != field){
                field = value
                if (!chosen) iconView.setImageDrawable(field)
            }
        }

    var chosen: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                typeFace = if (!field) ResourcesCompat.getFont(context, R.font.roboto_regular)
                else ResourcesCompat.getFont(context, R.font.roboto_medium)

                textColor = if (!field) ResourcesCompat.getColor(
                    context.resources,
                    R.color.medium_green,
                    context.theme
                )
                else ResourcesCompat.getColor(context.resources, R.color.black, context.theme)
                textView.apply {
                    setTextColor(textColor)
                    setTypeface(typeFace)
                }

                if (!field) iconView.setImageDrawable(iconUnselected)
                else iconView.setImageDrawable(iconSelected)

                val backgroundId = if (layoutAlignment == LayoutAlignment.LEFT) {
                    if (!field) R.drawable.bg_chooser_item_left_unselected
                    else R.drawable.bg_chooser_item_left_selected
                } else {
                    if (!field) R.drawable.bg_chooser_item_right_unselected
                    else R.drawable.bg_chooser_item_right_selected
                }

                background = ResourcesCompat.getDrawable(resources, backgroundId, context.theme)
                invalidate()
            }
        }

    var layoutAlignment: Int = -1
        set(value) {
            if (value != field) {
                field = value
                val backgroundId = if (layoutAlignment == LayoutAlignment.LEFT) {
                    if (!chosen) R.drawable.bg_chooser_item_left_unselected
                    else R.drawable.bg_chooser_item_left_selected
                } else {
                    if (!chosen) R.drawable.bg_chooser_item_right_unselected
                    else R.drawable.bg_chooser_item_right_selected
                }

                background = ResourcesCompat.getDrawable(resources, backgroundId, context.theme)
                invalidate()
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.chooser_item_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.ChooserItemView) {
            mainInit()
            initImageView()
            initTextView()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(iconView, widthMeasureSpec, heightMeasureSpec)
        val iconActualWidth: Int
        val iconActualHeight: Int
        with(iconView) {
            iconActualWidth = marginLeft + paddingLeft + paddingRight + measuredWidth
            iconActualHeight = paddingTop + paddingBottom + measuredHeight
        }
        measureChild(textView, widthMeasureSpec, heightMeasureSpec)
        val textActualWidth: Int
        val textActualHeight: Int
        with(textView) {
            textActualWidth = marginLeft + paddingLeft + paddingRight + measuredWidth
            textActualHeight = paddingTop + paddingBottom + measuredHeight
        }
        val elementsHeight =
            if (textActualHeight > iconActualHeight) textActualHeight else iconActualHeight
        val actualWidth = resolveSize(
            marginLeft + paddingLeft + paddingRight + iconActualWidth + textActualWidth + SPACING,
            widthMeasureSpec
        )
        val actualHeight =
            resolveSize(paddingTop + paddingBottom + elementsHeight, heightMeasureSpec)
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
        val textTop = (height - textView.measuredHeight) / 2

        val iconLeft: Int
        val textLeft: Int

        if (layoutAlignment == LayoutAlignment.LEFT) {
            iconLeft = paddingLeft
            textLeft = iconLeft + iconView.measuredWidth + SPACING
        } else {
            textLeft = paddingLeft
            iconLeft = textLeft + textView.measuredWidth + SPACING
        }

        val iconRight = iconLeft + iconView.measuredWidth
        val iconBottom = iconTop + iconView.measuredHeight
        val textRight = textLeft + textView.measuredWidth
        val textBottom = textTop + textView.measuredHeight

        iconView.layout(iconLeft, iconTop, iconRight, iconBottom)
        textView.layout(textLeft, textTop, textRight, textBottom)
    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    private fun Float.toSp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX, this, context.resources.displayMetrics
    )

    private fun TypedArray.mainInit() {
        typeFace = if (!chosen) ResourcesCompat.getFont(context, R.font.roboto_regular)
        else ResourcesCompat.getFont(context, R.font.roboto_medium)
        layoutAlignment = getInt(R.styleable.ChooserItemView_item_alignment, -1)
        val backgroundId = if (layoutAlignment == LayoutAlignment.LEFT) {
            if (!chosen) R.drawable.bg_chooser_item_left_unselected
            else R.drawable.bg_chooser_item_left_selected
        } else {
            if (!chosen) R.drawable.bg_chooser_item_right_unselected
            else R.drawable.bg_chooser_item_right_selected
        }
        val foregroundId = if (layoutAlignment == LayoutAlignment.LEFT) {
            R.drawable.ripple_left_chooser
        } else {
            R.drawable.ripple_right_chooser
        }
        foreground = ResourcesCompat.getDrawable(resources, foregroundId, context.theme)
        background = ResourcesCompat.getDrawable(resources, backgroundId, context.theme)
    }

    private fun TypedArray.initImageView() {
        iconView = findViewById(R.id.iconView)
        iconSelected = getDrawable(R.styleable.ChooserItemView_selected_icon)
        iconUnselected = getDrawable(R.styleable.ChooserItemView_unselected_icon)
        if (chosen) iconView.setImageDrawable(iconSelected) else iconView.setImageDrawable(
            iconUnselected
        )
        iconView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun TypedArray.initTextView() {
        textView = findViewById(R.id.textView)
        text = getString(R.styleable.ChooserItemView_android_text).toString()
        textSize =
            getDimensionPixelSize(R.styleable.ChooserItemView_android_textSize, 14).toFloat().toSp()
        textColor =
            if (!chosen) ResourcesCompat.getColor(resources, R.color.medium_green, context.theme)
            else ResourcesCompat.getColor(resources, R.color.black, context.theme)

        textView.apply {
            text = this@ChooserItemView.text
            textSize = this@ChooserItemView.textSize.toSp()
            setTextColor(this@ChooserItemView.textColor)
        }
        textView.setBackgroundColor(Color.TRANSPARENT)
    }

    companion object{
        const val SPACING = 15
    }

}