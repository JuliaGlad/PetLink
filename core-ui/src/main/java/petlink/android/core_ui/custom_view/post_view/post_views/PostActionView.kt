package petlink.android.core_ui.custom_view.post_view.post_views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import petlink.android.core_ui.R

class PostActionView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var iconView: ImageView
    private lateinit var actionCountTextView: TextView

    var backgroundOn: Boolean = true
        set(value) {
            if (value != field){
                field = value
                background = if (field){
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.bg_post_action_view,
                        context.theme
                    )
                } else null
            }
        }

    var actionsCount: Int = -1
        set(value) {
            if (value != field) {
                field = value
                actionCountTextView.text = field.toString()
            }
        }
    var icon: Drawable? = null
        set(value) {
            if (value != field) {
                field = value
                iconView.setImageDrawable(field)
            }
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.post_action_view_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.PostActionView) {
            initIconView()
            initActionCountTextView()
            backgroundOn = getBoolean(R.styleable.PostActionView_backgroundOn, true)
            if (backgroundOn) {
                background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.bg_post_action_view,
                    context.theme
                )
            }
        }
    }

    private fun TypedArray.initActionCountTextView() {
        actionCountTextView = findViewById<TextView>(R.id.action_count)
        actionsCount = getInt(R.styleable.PostActionView_action_count, -1)
        actionCountTextView.text = actionsCount.toString()
    }

    private fun TypedArray.initIconView() {
        iconView = findViewById<ImageView>(R.id.action_icon)
        icon = getDrawable(R.styleable.PostActionView_action_icon)
        iconView.setImageDrawable(icon)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(iconView, widthMeasureSpec, heightMeasureSpec)
        measureChild(actionCountTextView, widthMeasureSpec, heightMeasureSpec)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + iconView.measuredWidth + actionCountTextView.measuredHeight + SPACING.dp(),
            widthMeasureSpec
        )
        val actualHeight =
            resolveSize(paddingTop + paddingBottom + iconView.measuredHeight, heightMeasureSpec)

        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onLayout(
        p0: Boolean,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int
    ) {
        val iconRight = paddingLeft + iconView.measuredWidth
        val iconBottom = paddingTop + iconView.measuredHeight
        iconView.layout(paddingLeft, paddingTop, iconRight, iconBottom)

        val countTextLeft = iconRight + SPACING
        val countTextRight = countTextLeft + actionCountTextView.measuredWidth
        val countTextTop = (measuredHeight - actionCountTextView.measuredHeight) / 2
        val countTextBottom = countTextTop + actionCountTextView.measuredHeight
        actionCountTextView.layout(countTextLeft, countTextTop, countTextRight, countTextBottom)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        const val SPACING = 7
    }

}