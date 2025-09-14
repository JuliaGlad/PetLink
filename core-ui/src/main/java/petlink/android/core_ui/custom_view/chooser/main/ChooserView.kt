package petlink.android.core_ui.custom_view.chooser.main

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.LayoutAlignment
import petlink.android.core_ui.custom_view.chooser.item.ChooserItemView

class ChooserView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    var chosenId: Int = -1
    private lateinit var chooser1: ChooserItemView
    private lateinit var chooser2: ChooserItemView
    var selectedIcon1: Drawable? = null
        set(value) {
            if (value != field) {
                field = value
                chooser1.iconSelected = field
            }
        }
    var unselectedIcon1: Drawable? = null
        set(value) {
            if (value != field) {
                field = value
                chooser1.iconUnselected = field
            }
        }
    var selectedIcon2: Drawable? = null
        set(value) {
            if (value != field) {
                field = value
                chooser2.iconSelected = field
            }
        }
    var unselectedIcon2: Drawable? = null
        set(value) {
            if (value != field) {
                field = value
                chooser2.iconUnselected = field
            }
        }
    var text1: String = ""
        set(value) {
            if (value != field) {
                field = value
                chooser1.text = field
            }
        }
    var text2: String = ""
        set(value) {
            if (value != field) {
                field = value
                chooser2.text = field
            }
        }
    private var textSize: Float = 14f

    init {
        LayoutInflater.from(context).inflate(R.layout.chooser_view_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.ChooserView) {
            textSize = getDimension(R.styleable.ChooserView_android_textSize, 14f).toFloat().toSp()
            initChooser1()
            initChooser2()
        }
    }

    fun getChooser1() = chooser1
    fun getChooser2() = chooser2

    fun onViewClicked(chosenId: Int) {
        if (chosenId != this.chosenId) {
            this.chosenId = chosenId
            if (chooser1.viewId != chosenId) chooser1.chosen = false
            else chooser2.chosen = false
        }
    }

    private fun TypedArray.initChooser2() {
        chooser2 = findViewById(R.id.chooser_2)
        selectedIcon2 = getDrawable(R.styleable.ChooserView_chooser_2_selected_icon)
        unselectedIcon2 = getDrawable(R.styleable.ChooserView_chooser_2_unselected_icon)
        text2 = getString(R.styleable.ChooserView_chooser_2_text).toString()
        chooser2.textSize = textSize
        chooser2.apply {
            layoutAlignment = LayoutAlignment.RIGHT
            iconSelected = selectedIcon2
            iconUnselected = unselectedIcon2
            text = text2
        }
    }

    private fun TypedArray.initChooser1() {
        chooser1 = findViewById(R.id.chooser_1)
        selectedIcon1 = getDrawable(R.styleable.ChooserView_chooser_1_selected_icon)
        unselectedIcon1 = getDrawable(R.styleable.ChooserView_chooser_1_unselected_icon)
        text1 = getString(R.styleable.ChooserView_chooser_1_text).toString()
        chooser1.textSize = textSize
        chooser1.apply {
            layoutAlignment = LayoutAlignment.LEFT
            iconSelected = selectedIcon1
            iconUnselected = unselectedIcon1
            text = text1
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(chooser1, widthMeasureSpec, heightMeasureSpec)
        measureChild(chooser2, widthMeasureSpec, heightMeasureSpec)
        val actualWidth = resolveSize(
            paddingLeft + paddingRight + chooser1.measuredWidth + chooser2.measuredWidth + SPACING,
            widthMeasureSpec
        )
        val actualHeight =
            resolveSize(
                paddingTop + paddingBottom + maxOf(chooser1.measuredHeight, chooser2.measuredHeight),
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
        val chooser1Right = paddingLeft + chooser1.measuredWidth
        val chooser1Bottom = paddingTop + chooser1.measuredHeight
        chooser1.layout(paddingLeft, paddingTop, chooser1Right, chooser1Bottom)

        val chooser2Left = chooser1Right + SPACING
        val chooser2Right = chooser2Left + chooser2.measuredWidth
        val chooser2Bottom = paddingTop + chooser2.measuredHeight
        chooser2.layout(chooser2Left, paddingTop, chooser2Right, chooser2Bottom)
    }

    private fun Float.toSp() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX, this, context.resources.displayMetrics
    )

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    companion object {
        const val SPACING = 15
    }
}