package petlink.android.core_ui.custom_view.flexbox_item

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import petlink.android.core_ui.R
import petlink.android.core_ui.listener.ClickIntegerListener

class FlexboxItemView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : View(context, attributeSet, defStyle, defTheme) {
    var viewId: Int = 0
        set(value) {
            if (value != field) {
                field = value
            }
        }

    var isChosen: Boolean = false
        set(value) {
            if (value != field) {
                field = value
                post {
                    val id = if (value) R.drawable.bg_flexbox_item_selected
                    else R.drawable.bg_flexbox_item_unselected
                    background = ResourcesCompat.getDrawable(resources, id, context.theme)
                    textPaint.typeface =
                        if (value) ResourcesCompat.getFont(context, R.font.roboto_medium)
                        else ResourcesCompat.getFont(context, R.font.roboto_regular)
                    invalidate()
                }
            }
        }

    var itemTextSize: Float = 14f
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    var textColor: Int = context.getColor(R.color.black)
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }

    var text: String = "Empty"
        set(value) {
            if (value != field) {
                field = value
                requestLayout()
            }
        }
    var clickListener: ClickIntegerListener? = null

    init {
        context.withStyledAttributes(attributeSet, R.styleable.FlexboxItemView) {
            setItemPadding(context)
            text = getString(R.styleable.FlexboxItemView_android_text).toString()
            itemTextSize =
                getDimensionPixelSize(R.styleable.FlexboxItemView_android_textSize, 14).toFloat()
            textColor = getColor(R.styleable.FlexboxItemView_android_textColor, 0)
            foreground = ResourcesCompat.getDrawable(resources, R.drawable.ripple_flexbox, context.theme)
            isClickable = true
        }
    }

    private fun setItemPadding(context: Context) {
        val paddingDp = 12
        val scale = context.resources.displayMetrics.density
        val paddingPx = (paddingDp * scale).toInt()
        setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
    }

    private val textRect = Rect()

    private val textPaint = Paint().apply {
        color = textColor
        typeface = ResourcesCompat.getFont(context, R.font.roboto_regular)
        textSize = itemTextSize.toSp()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        textPaint.textSize = itemTextSize.toSp()
        textPaint.getTextBounds(text, 0, text.length, textRect)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + textRect.width(),
            widthMeasureSpec
        )

        val actualHeight = resolveSize(
            paddingTop + paddingBottom + textRect.height(),
            heightMeasureSpec
        )

        setMeasuredDimension(actualWidth, actualHeight)
    }

    fun FlexboxItemView.onViewClicked() {
        setOnClickListener {
            isChosen = !isChosen
            clickListener?.onClick(viewId)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.color = textColor
        val topOffset = height / 2 - textRect.exactCenterY()

        canvas.drawText(text, paddingLeft.toFloat(), topOffset, textPaint)
    }

    private fun Float.toSp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics
    )
}