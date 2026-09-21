package petlink.android.core_ui.custom_view.comment

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.comment.marker.CommentMarkerView
import petlink.android.core_ui.custom_view.comment.reply.CommentReplyView
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView

class CommentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var nameTextView: TextView
    private lateinit var avatarView: ShapeableImageView
    private lateinit var messageTextView: TextView
    private lateinit var markerView: CommentMarkerView
    private lateinit var likesTextView: TextView
    private lateinit var repliesTextView: TextView
    private lateinit var likesIcon: ImageView
    private lateinit var repliesIcon: ImageView
    private lateinit var photosScroll: HorizontalScrollView

    private var isLiked: Boolean = false
    private var likesCount = 0
    private val replies: MutableList<CommentReplyView> = mutableListOf()
    private var name = NONE
    private var message = NONE

    fun setOnLikeClick(listener: () -> Unit) {
        likesIcon.setOnClickListener { listener() }
    }

    fun setOnReplyClick(listener: () -> Unit) {
        repliesIcon.setOnClickListener { listener() }
        repliesTextView.setOnClickListener { listener() }
    }

    fun clearReplies() {
        replies.forEach { removeView(it) }
        replies.clear()
        repliesTextView.text = "0"
    }

    fun addReplies(replyView: CommentReplyView) {
        replies.add(replyView)

        val verticalPadding = (REPLY_PADDING * resources.displayMetrics.density).toInt()
        replyView.setPadding(
            replyView.paddingLeft,
            maxOf(replyView.paddingTop, verticalPadding),
            replyView.paddingRight,
            replyView.paddingBottom
        )

        addView(replyView)
        repliesTextView.text = replies.size.toString()
        requestLayout()
    }

    fun setIsLiked(value: Boolean) {
        if (value != isLiked) {
            isLiked = value
            checkIsLiked(value)
        }
    }

    fun setLikesCount(value: Int) {
        likesCount = value
        likesTextView.text = value.toString()
        isUseful(likesCount)
    }

    fun setMessage(value: String) {
        message = value
        messageTextView.text = value
        messageTextView.visibility = if (value.isBlank()) GONE else VISIBLE
    }

    fun setName(value: String) {
        if (value != name) {
            name = value
            nameTextView.text = value
        }
    }

    fun setAvatar(uri: String) {
        if (uri.isNotEmpty()) {
            avatarView.setImageUri(android.net.Uri.parse(uri), R.drawable.avatar_owner_no_image)
        }
    }

    fun setPhotos(photos: List<String>, onPhotoClick: ((String) -> Unit)? = null) {
        photosScroll.bindCommentPhotos(photos, onPhotoClick)
        requestLayout()
    }

    fun setHighlighted(highlighted: Boolean) {
        val pad = if (highlighted) {
            (HIGHLIGHT_PADDING * resources.displayMetrics.density).toInt()
        } else {
            0
        }
        setPadding(pad, pad, pad, pad)
        background = if (highlighted) {
            ContextCompat.getDrawable(context, R.drawable.bg_reply_highlight)
        } else {
            null
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.comment_layout, this, true)
        photosScroll = findViewById(R.id.comment_photos)
        context.withStyledAttributes(attributeSet, R.styleable.CommentView) {
            initNameTextView()
            initCommentTextView()
            iniMarkerView()
            initLikesTextView()
            initRepliesTextView()
            initLikesIcon()
            initAvatarView()
            repliesIcon = findViewById(R.id.icon_reply)
            likesIcon.isClickable = true
            likesIcon.isFocusable = true
            repliesIcon.isClickable = true
            repliesIcon.isFocusable = true
            avatarView = findViewById(R.id.avatar_view)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChild(avatarView, widthMeasureSpec, heightMeasureSpec)
        measureChild(likesIcon, widthMeasureSpec, heightMeasureSpec)
        measureChild(repliesIcon, widthMeasureSpec, heightMeasureSpec)
        measureChild(repliesTextView, widthMeasureSpec, heightMeasureSpec)
        measureChild(likesTextView, widthMeasureSpec, heightMeasureSpec)
        measureChild(markerView, widthMeasureSpec, heightMeasureSpec)

        val maxWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - avatarView.measuredWidth - SPACING
        val childMaxWidthSpec = MeasureSpec.makeMeasureSpec(maxWidth.coerceAtLeast(0), MeasureSpec.AT_MOST)

        nameTextView.measure(childMaxWidthSpec, heightMeasureSpec)
        if (messageTextView.visibility != GONE) {
            messageTextView.measure(childMaxWidthSpec, heightMeasureSpec)
        }
        val photosHeight = measurePhotos(maxWidth, heightMeasureSpec)

        var repliesHeight = 0
        var repliesWidth = 0
        replies.forEach {
            it.measure(childMaxWidthSpec, heightMeasureSpec)
            repliesHeight += it.measuredHeight
            repliesWidth = maxOf(repliesWidth, it.measuredWidth)
        }

        val messageHeight = if (messageTextView.visibility != GONE) messageTextView.measuredHeight else 0
        val messageWidth = if (messageTextView.visibility != GONE) messageTextView.measuredWidth else 0
        val photosWidth = if (photosScroll.visibility != GONE) photosScroll.measuredWidth else 0
        var actualWidth = resolveSize(
            paddingLeft + paddingRight + avatarView.measuredWidth + maxOf(
                nameTextView.measuredWidth, messageWidth, photosWidth
            ) + SPACING,
            widthMeasureSpec
        )
        if (actualWidth < repliesWidth) actualWidth = repliesWidth
        val contentHeight = nameTextView.measuredHeight + photosHeight +
            (if (messageTextView.visibility != GONE) LINE_SPACING + messageHeight else 0) +
            LINE_SPACING + likesIcon.measuredHeight
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + maxOf(
                avatarView.measuredHeight,
                contentHeight
            ) + repliesHeight,
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
        val avatarBottom = paddingTop + avatarView.measuredHeight
        val avatarRight = paddingLeft + avatarView.measuredWidth
        avatarView.layout(paddingLeft, paddingTop, avatarRight, avatarBottom)
        val nameLeft = avatarRight + SPACING
        val nameRight = nameLeft + nameTextView.measuredWidth
        val nameBottom = paddingTop + nameTextView.measuredHeight
        nameTextView.layout(nameLeft, paddingTop, nameRight, nameBottom)

        var contentBottom = nameBottom
        if (photosScroll.visibility != GONE) {
            val photosTop = contentBottom + LINE_SPACING
            val photosBottom = photosTop + photosScroll.measuredHeight
            val photosRight = nameLeft + photosScroll.measuredWidth
            photosScroll.layout(nameLeft, photosTop, photosRight, photosBottom)
            contentBottom = photosBottom
        }

        if (messageTextView.visibility != GONE) {
            val messageLeft = avatarRight + SPACING
            val messageRight = messageLeft + messageTextView.measuredWidth
            val messageTop = contentBottom + LINE_SPACING
            val messageBottom = messageTop + messageTextView.measuredHeight
            messageTextView.layout(messageLeft, messageTop, messageRight, messageBottom)
            contentBottom = messageBottom
        }

        val markerRight = measuredWidth - paddingRight
        val markerLeft = markerRight - markerView.measuredWidth
        val markerBottom = paddingTop + markerView.measuredHeight
        markerView.layout(markerLeft, paddingTop, markerRight, markerBottom)

        val likeIconLeft = avatarRight + SPACING
        val likeIconRight = likeIconLeft + likesIcon.measuredWidth
        val likeIconTop = contentBottom + LINE_SPACING
        val likeIconBottom = likeIconTop + likesIcon.measuredHeight
        likesIcon.layout(likeIconLeft, likeIconTop, likeIconRight, likeIconBottom)

        val likesTextLeft = likeIconRight + ICON_SPACING
        val likesTextRight = likesTextLeft + likesTextView.measuredWidth
        val likesTextTop = likeIconTop + (likesIcon.measuredHeight - likesTextView.measuredHeight) / 2
        val likesTextBottom = likesTextTop + likesTextView.measuredHeight
        likesTextView.layout(likesTextLeft, likesTextTop, likesTextRight, likesTextBottom)

        val repliesIconLeft = likesTextRight + COUNTERS_SPACING
        val repliesIconRight = repliesIconLeft + repliesIcon.measuredWidth
        val repliesIconTop = likeIconTop
        val repliesIconBottom = repliesIconTop + repliesIcon.measuredHeight
        repliesIcon.layout(repliesIconLeft, repliesIconTop, repliesIconRight, repliesIconBottom)

        val repliesTextLeft = repliesIconRight + ICON_SPACING
        val repliesTextRight = repliesTextLeft + repliesTextView.measuredWidth
        val repliesTextTop = likeIconTop + (repliesIcon.measuredHeight - repliesTextView.measuredHeight) / 2
        val repliesTextBottom = repliesTextTop + repliesTextView.measuredHeight
        repliesTextView.layout(repliesTextLeft, repliesTextTop, repliesTextRight, repliesTextBottom)

        var prevReplyHeight = likeIconBottom

        replies.forEach {
            val replyBottom = prevReplyHeight + it.measuredHeight
            val replyLeft = avatarRight + SPACING
            val replyRight = replyLeft + it.measuredWidth
            it.layout(replyLeft, prevReplyHeight, replyRight, replyBottom)
            prevReplyHeight += it.measuredHeight
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }

    private fun measurePhotos(maxWidth: Int, heightMeasureSpec: Int): Int {
        if (photosScroll.visibility == GONE) return 0
        val photosWidthSpec = MeasureSpec.makeMeasureSpec(maxWidth.coerceAtLeast(0), MeasureSpec.EXACTLY)
        val photosHeightSpec = MeasureSpec.makeMeasureSpec(
            (PHOTO_HEIGHT_DP * resources.displayMetrics.density).toInt(),
            MeasureSpec.EXACTLY
        )
        photosScroll.measure(photosWidthSpec, photosHeightSpec)
        return photosScroll.measuredHeight + LINE_SPACING
    }

    private fun TypedArray.initRepliesTextView() {
        repliesTextView = findViewById(R.id.replies_count)
        repliesTextView.text = replies.size.toString()
    }

    private fun TypedArray.initLikesTextView() {
        likesCount = getInt(R.styleable.CommentView_likes_count, 0)
        likesTextView = findViewById(R.id.likes_count)
        likesTextView.text = likesCount.toString()
    }

    private fun TypedArray.iniMarkerView() {
        markerView = findViewById(R.id.marker)
        isUseful(likesCount)
    }

    private fun isUseful(value: Int) {
        if (value >= USEFUL_LIKES_COUNT) markerView.visibility = VISIBLE
        else markerView.visibility = GONE
    }

    private fun TypedArray.initAvatarView() {
        avatarView = findViewById(R.id.avatar_view)
        avatarView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.avatar_owner_no_image, context.theme))
    }

    private fun TypedArray.initCommentTextView() {
        message = getString(R.styleable.CommentView_comment) ?: NONE
        messageTextView = findViewById(R.id.comment_message)
        messageTextView.text = message
    }

    private fun TypedArray.initNameTextView() {
        name = getString(R.styleable.CommentView_name) ?: NONE
        nameTextView = findViewById(R.id.user_name)
        nameTextView.text = name
    }

    private fun TypedArray.initLikesIcon() {
        likesIcon = findViewById(R.id.icon_like)
        checkIsLiked(isLiked)
    }

    private fun checkIsLiked(value: Boolean) {
        val likeDrawable = if (value) R.drawable.ic_like_fill
        else R.drawable.ic_like_no_fill
        likesIcon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                likeDrawable,
                context.theme
            )
        )
    }

    companion object {
        const val NONE: String = "NONE"
        const val USEFUL_LIKES_COUNT = 15
        const val SPACING = 20
        const val LINE_SPACING = 8
        const val ICON_SPACING = 4
        const val COUNTERS_SPACING = 22
        const val REPLY_PADDING = 16
        const val HIGHLIGHT_PADDING = 12
    }

}
