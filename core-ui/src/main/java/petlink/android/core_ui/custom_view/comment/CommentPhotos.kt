package petlink.android.core_ui.custom_view.comment

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView

fun HorizontalScrollView.bindCommentPhotos(
    photos: List<String>,
    onPhotoClick: ((String) -> Unit)?
) {
    val container = findViewById<LinearLayout>(R.id.comment_photos_container)
    container.removeAllViews()
    if (photos.isEmpty()) {
        visibility = View.GONE
        return
    }
    visibility = View.VISIBLE
    val density = resources.displayMetrics.density
    val width = (PHOTO_WIDTH_DP * density).toInt()
    val height = (PHOTO_HEIGHT_DP * density).toInt()
    val gap = (PHOTO_GAP_DP * density).toInt()
    photos.forEachIndexed { index, uri ->
        val image = ShapeableImageView(context).apply {
            layoutParams = LinearLayout.LayoutParams(width, height).apply {
                if (index > 0) marginStart = gap
            }
            background = ContextCompat.getDrawable(context, R.drawable.bg_round_rectangle)
            clipToOutline = true
            setImageUri(uri.toUri(), R.drawable.add_cover)
            setOnClickListener { onPhotoClick?.invoke(uri) }
        }
        container.addView(image)
    }
}

const val PHOTO_WIDTH_DP = 88
const val PHOTO_HEIGHT_DP = 72
const val PHOTO_GAP_DP = 8
