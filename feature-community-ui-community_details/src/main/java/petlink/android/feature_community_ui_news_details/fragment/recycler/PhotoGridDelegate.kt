package petlink.android.feature_community_ui_news_details.fragment.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView
import petlink.android.core_ui.delegates.main.AdapterDelegate
import petlink.android.core_ui.delegates.main.DelegateItem
import petlink.android.core_ui.playPressAnimation
import petlink.android.feature_community_ui_news_details.databinding.DelegatePhotoGridBinding

class PhotoGridDelegate : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            DelegatePhotoGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as PhotoGridModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is PhotoGridDelegateItem

    class ViewHolder(private val binding: DelegatePhotoGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PhotoGridModel) {
            val container = binding.photosContainer
            container.removeAllViews()
            if (model.photos.isEmpty()) return
            val density = itemView.resources.displayMetrics.density
            val gap = (8 * density).toInt()
            model.photos.chunked(2).forEachIndexed { rowIndex, rowPhotos ->
                val height = (ROW_HEIGHTS_DP[rowIndex % ROW_HEIGHTS_DP.size] * density).toInt()
                val row = LinearLayout(itemView.context).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                    ).apply {
                        if (rowIndex > 0) topMargin = gap
                    }
                }
                rowPhotos.forEachIndexed { index, photo ->
                    row.addView(createPhotoView(photo, model, gap, index > 0))
                }
                if (rowPhotos.size == 1) {
                    row.addView(View(itemView.context).apply {
                        layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f).apply {
                            marginStart = gap
                        }
                    })
                }
                container.addView(row)
            }
        }

        private fun createPhotoView(
            photo: PhotoGridItem,
            model: PhotoGridModel,
            gap: Int,
            addStartMargin: Boolean
        ): ShapeableImageView =
            ShapeableImageView(itemView.context).apply {
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f).apply {
                    if (addStartMargin) marginStart = gap
                }
                background = ContextCompat.getDrawable(itemView.context, R.drawable.bg_round_rectangle)
                clipToOutline = true
                setImageUri(photo.uri.toUri(), R.drawable.add_cover)
                setOnClickListener {
                    it.playPressAnimation()
                    model.onPhotoClick(photo)
                }
            }
    }

    companion object {
        private val ROW_HEIGHTS_DP = intArrayOf(200, 132, 176, 120, 160, 148)
    }
}
