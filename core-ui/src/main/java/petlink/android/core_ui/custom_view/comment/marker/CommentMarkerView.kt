package petlink.android.core_ui.custom_view.comment.marker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import petlink.android.core_ui.R

class CommentMarkerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private var iconView: ImageView
    private var textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.comment_marker_layout, this, true)
        iconView = findViewById(R.id.icon)
        textView = findViewById(R.id.text)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(iconView, widthMeasureSpec, heightMeasureSpec)
        measureChild(textView, widthMeasureSpec, heightMeasureSpec)
        val actualWidth = resolveSize(
            marginLeft + iconView.measuredWidth + textView.measuredWidth + SPACING,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            marginTop + maxOf(iconView.measuredHeight, textView.measuredHeight),
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
        val textTop = (iconView.measuredHeight - textView.measuredHeight) / 2

        val iconRight = paddingLeft + iconView.measuredWidth
        val iconBottom = paddingTop + iconView.measuredHeight

        val textLeft: Int = iconRight + SPACING
        val textRight = textLeft + textView.measuredWidth
        val textBottom = textTop + textView.measuredHeight

        iconView.layout(paddingLeft, paddingTop, iconRight, iconBottom)
        textView.layout(textLeft, textTop, textRight, textBottom)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    companion object {
        const val SPACING = 15
    }

}