package petlink.android.core_ui.custom_view.round_avatar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import petlink.android.core_ui.ImageLoaderDrawable
import petlink.android.core_ui.R

class ShapeableImageView @JvmOverloads constructor(
    context: Context,
    defAttrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, defAttrs, defStyle) {

    private var backgroundForm: Drawable? = null
        set(value) {
            field = value
            background = value
            requestLayout()
        }

    private var borderWidth: Float = 0f
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private var borderColor: Int = ResourcesCompat.getColor(resources, R.color.transparent, context.theme)
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        clipToOutline = true
        outlineProvider = ViewOutlineProvider.BACKGROUND
        context.withStyledAttributes(defAttrs, R.styleable.ShapableImageView) {
            backgroundForm = getDrawable(R.styleable.ShapableImageView_android_background)
            borderWidth = getFloat(R.styleable.ShapableImageView_shapeable_border_width, 0f)
            borderColor = getColor(
                R.styleable.ShapableImageView_shapeable_border_color,
                ResourcesCompat.getColor(resources, R.color.transparent, context.theme)
            )
            background = backgroundForm
        }
        scaleType = ScaleType.CENTER_CROP
    }

    private var lastLoadedUri: String? = null

    fun setImageUri(uri: Uri?, placeholderRes: Int = R.drawable.avatar_owner_no_image) {
        val key = uri?.toString().orEmpty()
        if (key.isNotEmpty() && key == lastLoadedUri && drawable != null && drawable !is ImageLoaderDrawable) {
            return
        }
        lastLoadedUri = key.ifEmpty { null }
        if (uri == null || key.isEmpty()) {
            setImageResource(placeholderRes)
            return
        }
        val loader = ImageLoaderDrawable(
            ContextCompat.getColor(context, R.color.dark_green)
        ).also { it.start() }
        Glide.with(context)
            .load(uri)
            .dontAnimate()
            .placeholder(loader)
            .error(placeholderRes)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    loader.stop()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    loader.stop()
                    return false
                }
            })
            .into(this)
    }

    fun setDrawableImage(image: Drawable?) {
        lastLoadedUri = null
        setImageDrawable(image)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (borderWidth <= 0f || kotlin.math.abs(width - height) > 8) return
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
