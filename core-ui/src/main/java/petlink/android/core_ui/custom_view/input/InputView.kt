package petlink.android.core_ui.custom_view.input

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import petlink.android.core_ui.R
import androidx.core.graphics.withClip
import androidx.core.view.setPadding

class InputView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var messageTextView: EditText
    private var sendIcon: ImageView
    private lateinit var addIcon: ImageView

    private var message: String = NONE
    private var hint: String = NONE

    val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.green_grey)
        style = Paint.Style.FILL
    }

    fun setMessage(newValue: String) {
        if (newValue != message) {
            message = newValue
            messageTextView.setText(newValue)
            requestLayout()
        }
    }

    fun onSendClickListener(listener: () -> Unit) {
        sendIcon.setOnClickListener { listener() }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.input_view_layout, this, true)
        initMessageTextView()
        initAddIcon()
        sendIcon = findViewById(R.id.send_icon)
    }

    private fun initAddIcon() {
        addIcon = findViewById(R.id.add_icon)

    }

    private fun initMessageTextView() {
        messageTextView = findViewById(R.id.message_text_view)
        messageTextView.setPadding(PADDING.dp())
        messageTextView.setText(message)
        messageTextView.hint = hint
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(sendIcon, widthMeasureSpec, heightMeasureSpec)
        measureChild(addIcon, widthMeasureSpec, heightMeasureSpec)

        val maxWidth =
            MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - addIcon.measuredWidth - sendIcon.measuredWidth - SPACING_ICON_TEXT.dp()
        val maxTextWidthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST)

        messageTextView.measure(maxTextWidthMeasureSpec, heightMeasureSpec)

        val actualWidth = resolveSize(
            messageTextView.measuredWidth + SPACING_ICON_TEXT.dp() + sendIcon.measuredWidth + addIcon.measuredWidth + paddingRight + paddingLeft,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            maxOf(
                sendIcon.measuredHeight,
                addIcon.measuredHeight,
                messageTextView.measuredHeight
            ),
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
        val addLeft = paddingLeft+PADDING.dp()
        val addRight = addLeft + addIcon.measuredWidth
        val addTop = (measuredHeight+paddingBottom+paddingTop - addIcon.measuredWidth) / 2 + 5
        val addBottom = addTop + addIcon.measuredHeight
        addIcon.layout(addLeft, addTop, addRight, addBottom)

        val textLeft = addRight + SPACING_ICON_TEXT.dp()
        val textRight = textLeft + messageTextView.measuredWidth
        val textTop = paddingTop
        val textBottom = textTop + messageTextView.measuredHeight
        messageTextView.layout(textLeft, textTop, textRight, textBottom)

        val sendRight = measuredWidth - paddingRight - PADDING.dp()
        val sendTop = (measuredHeight+paddingTop+paddingBottom - sendIcon.measuredHeight) / 2
        val sendLeft = sendRight - sendIcon.measuredWidth
        val sendBottom = sendTop + sendIcon.measuredHeight
        sendIcon.layout(sendLeft, sendTop, sendRight, sendBottom)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val backgroundLeft = addIcon.right.toFloat() + SPACING_ICON_TEXT.dp()
        val backgroundRight = (width - paddingRight).toFloat()
        val backgroundTop = paddingTop.toFloat()
        val backgroundBottom = (height - paddingBottom).toFloat()

        canvas.drawRoundRect(
            backgroundLeft,
            backgroundTop,
            backgroundRight,
            backgroundBottom,
            12.dp().toFloat(),
            12.dp().toFloat(),
            backgroundPaint
        )
        super.dispatchDraw(canvas)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        const val NONE = ""
        const val PADDING = 16
        const val SPACING_ICON_TEXT = 13
        const val SPACING = 8
    }
}