package petlink.android.core_ui.custom_view.round_avatar

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import petlink.android.core_ui.R

class ShapeableImageView @JvmOverloads constructor(
    context: Context,
    defAttrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, defAttrs, defStyle){

    private var backgroundForm: Drawable? = null
        set(value) {
            field = value
            setBackgroundDrawable(value)
            requestLayout()
        }

    private var borderWidth: Float = 0f
        set(value) {
            if (field != value){
                field = value
                invalidate()
            }
        }

    private var borderColor: Int = ResourcesCompat.getColor(resources, R.color.transparent, context.theme)
        set(value) {
            if (field != value){
                field = value
                invalidate()
            }
        }

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        clipToOutline = true
        context.withStyledAttributes(defAttrs, R.styleable.ShapableImageView){
            backgroundForm = getDrawable(R.styleable.ShapableImageView_android_background)
            borderWidth = getFloat(R.styleable.ShapableImageView_shapeable_border_width, 0f)
            borderColor = getColor(
                R.styleable.ShapableImageView_shapeable_border_color,
                ResourcesCompat.getColor(resources, R.color.transparent, context.theme)
            )
            setBackgroundDrawable(backgroundForm)
        }
        scaleType = ScaleType.CENTER_CROP
    }

    fun setImageUri(uri: Uri?){
        Glide.with(context)
            .load(uri)
            .override(LayoutParams.WRAP_CONTENT)
            .into(this)
    }

    fun setDrawableImage(image: Drawable?){
        setImageDrawable(image)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        borderPaint.apply {
            color = borderColor
            strokeWidth = borderWidth
        }
        val radius = (width.coerceAtMost(height) - borderWidth) / 2f
        val cx = width / 2f
        val cy = height / 2f

        canvas.drawCircle(cx, cy, radius, borderPaint)
    }
}