package petlink.android.core_ui.delegates.items.post

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView
import petlink.android.core_ui.playPressAnimation

class PostPhotosAdapter(
    val photos: List<String>,
    private val onPhotoClick: (String) -> Unit = {}
) : RecyclerView.Adapter<PostPhotosAdapter.PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val image = ShapeableImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            background = ContextCompat.getDrawable(parent.context, R.drawable.bg_round_rectangle)
            clipToOutline = true
        }
        return PhotoHolder(image)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val uri = photos[position]
        holder.image.setImageUri(uri.toUri(), R.drawable.add_cover)
        holder.image.setOnClickListener {
            it.playPressAnimation()
            onPhotoClick(uri)
        }
    }

    override fun getItemCount(): Int = photos.size

    class PhotoHolder(val image: ShapeableImageView) : RecyclerView.ViewHolder(image)
}
