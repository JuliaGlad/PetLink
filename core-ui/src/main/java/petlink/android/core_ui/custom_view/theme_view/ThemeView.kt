package petlink.android.core_ui.custom_view.theme_view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import petlink.android.core_ui.R

class ThemeView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private var themeBackground: View
    private var selectedThemeIcon: ImageView

    var isChosen = false
        set(value) {
            if (field != value) {
                field = value
                selectedThemeIcon.visibility =
                    if (field) VISIBLE
                    else GONE
            }
        }
    
    var backgroundThemeColor: Int = -1
        set(value) {
            if (value != field) {
                field = value
                val bg = themeBackground.background.mutate() as GradientDrawable
                bg.color = ColorStateList.valueOf(ResourcesCompat.getColor(resources, field, context.theme))
            }
        }
    var strokeColor = -1
        set(value) {
            if (value != field) {
                field = value
                val bg = themeBackground.background.mutate() as GradientDrawable
                val colorStateValue = ColorStateList.valueOf(ResourcesCompat.getColor(resources, field, context.theme))
                bg.setStroke(5, colorStateValue)
                selectedThemeIcon.imageTintList = colorStateValue
            }
        }

    fun onThemeViewClicked() {
        setOnClickListener {
            isChosen = !isChosen
            selectedThemeIcon.visibility =
                if (isChosen) VISIBLE
                else GONE
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_event_theme_view, this, true)
        themeBackground = findViewById(R.id.event_theme)
        selectedThemeIcon = findViewById(R.id.selected_theme_icon)
        foreground = ResourcesCompat.getDrawable(resources, R.drawable.ripple_round, context.theme)
        if (!isChosen) selectedThemeIcon.visibility = GONE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(themeBackground, widthMeasureSpec, heightMeasureSpec)
        measureChild(selectedThemeIcon, widthMeasureSpec, heightMeasureSpec)

        val actualWidth = resolveSize(themeBackground.measuredWidth, widthMeasureSpec)
        val actualHeight = resolveSize(themeBackground.measuredHeight, heightMeasureSpec)
        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val backgroundLeft = paddingLeft
        val backgroundRight = backgroundLeft + themeBackground.measuredWidth
        val backgroundTop = paddingTop
        val backgroundBottom = backgroundTop + themeBackground.measuredHeight
        themeBackground.layout(backgroundLeft, backgroundTop, backgroundRight, backgroundBottom)
        val iconLeft = (themeBackground.measuredWidth - selectedThemeIcon.measuredWidth) / 2
        val iconTop = (themeBackground.measuredHeight - selectedThemeIcon.measuredHeight) / 2
        val iconRight = iconLeft + selectedThemeIcon.measuredWidth
        val iconBottom = iconTop + selectedThemeIcon.measuredHeight
        selectedThemeIcon.layout(iconLeft, iconTop, iconRight, iconBottom)
    }
}