package petlink.android.core_ui.custom_view.post_view

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.flexbox.FlexBoxLayout
import petlink.android.core_ui.custom_view.post_view.post_views.PostActionView
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView

class PostView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defTheme: Int = 0
) : ViewGroup(context, attributeSet, defStyle, defTheme) {

    private lateinit var postImageView: ShapeableImageView
    private lateinit var postTitleView: TextView
    private lateinit var postDescriptionView: TextView
    private lateinit var showAllTextButton: TextView
    private lateinit var hashtagsFlexbox: FlexBoxLayout

    private lateinit var likesActionView: PostActionView
    private lateinit var commentActionView: PostActionView
    private lateinit var replyActionView: PostActionView
    private lateinit var viewsActionView: PostActionView

    var postUri: String = EMPTY
        set(value) {
            if (value != field) {
                field = value
                bindPostImage()
            }
        }

    var postTitle: String = EMPTY
        set(value) {
            if (value != field) {
                field = value
                postTitleView.text = field
            }
            postTitleView.visibility =
                if (value.isBlank() || value == EMPTY) View.GONE else View.VISIBLE
        }
    var postDescription: String = EMPTY
        set(value) {
            if (value != field) {
                field = value
                postDescriptionView.text = field
            }
            val hasText = value.isNotBlank() && value != EMPTY
            postDescriptionView.visibility = if (hasText) View.VISIBLE else View.GONE
            descriptionExpanded = false
            postDescriptionView.maxLines = 3
            showAllTextButton.visibility =
                if (hasText && value.length > 90) View.VISIBLE else View.GONE
        }

    var showComments: Boolean = true
        set(value) {
            if (value != field) {
                field = value
                commentActionView.visibility = if (value) View.VISIBLE else View.GONE
                requestLayout()
            }
        }

    var showReply: Boolean = true
        set(value) {
            if (value != field) {
                field = value
                replyActionView.visibility = if (value) View.VISIBLE else View.GONE
                requestLayout()
            }
        }

    private var descriptionExpanded = false
    var likesCount: Int = 0
        set(value) {
            field = value
            likesActionView.actionsCount = field
        }

    var isLiked: Boolean = false
        set(value) {
            if (value == field) return
            field = value
            val imageId =
                if (value) R.drawable.ic_like_fill
                else R.drawable.ic_like_no_fill
            likesActionView.icon =
                ResourcesCompat.getDrawable(resources, imageId, context.theme)
        }

    var commentCount: Int = 0
        set(value) {
            field = value
            commentActionView.actionsCount = field
        }
    var replyCount: Int = 0
        set(value) {
            field = value
            replyActionView.actionsCount = field
        }
    var viewsCount: Int = 0
        set(value) {
            field = value
            viewsActionView.actionsCount = field
        }

    fun addHashtag(title: String) {
        val params = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val view = TextView(context).apply {
            val tag = "#$title"
            layoutParams = params
            text = tag
            textSize = HASHTAG_TEXT_SIZE
            setTextColor(ResourcesCompat.getColor(resources, R.color.medium_green, context.theme))
            setTypeface(ResourcesCompat.getFont(context, R.font.roboto_semibold))
        }
        hashtagsFlexbox.addView(view)
        requestLayout()
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.post_view_layout, this, true)
        initPostImageView()
        initPostTitleTextView()
        initPostDescriptionTextView()
        initShowAllTextButton()
        initHashtagsFlexbox()
        initCommentActionView()
        initReplyActionView()
        initLikeActionView()
        initViewsActionView()
    }

    fun setOnLikeClick(listener: () -> Unit) {
        likesActionView.setOnClickListener { listener() }
    }

    fun setOnCommentClick(listener: () -> Unit) {
        commentActionView.setOnClickListener { listener() }
    }

    fun setOnReplyClick(listener: () -> Unit) {
        replyActionView.setOnClickListener { listener() }
    }

    private fun initViewsActionView() {
        viewsActionView = findViewById<PostActionView>(R.id.view_icon)
        viewsActionView.actionsCount = viewsCount
    }

    private fun initLikeActionView() {
        likesActionView = findViewById<PostActionView>(R.id.likes_icon)
        likesActionView.actionsCount = likesCount
        val imageId =
            if (isLiked) R.drawable.ic_like_fill
            else R.drawable.ic_like_no_fill
        likesActionView.icon = ResourcesCompat.getDrawable(resources, imageId, context.theme)
    }

    private fun initReplyActionView() {
        replyActionView = findViewById<PostActionView>(R.id.reply_icon)
        replyActionView.actionsCount = replyCount
    }

    private fun initCommentActionView() {
        commentActionView = findViewById<PostActionView>(R.id.comment_icon)
        commentActionView.actionsCount = commentCount
    }

    private fun initHashtagsFlexbox() {
        hashtagsFlexbox = findViewById<FlexBoxLayout>(R.id.hashtags)
    }

    private fun initShowAllTextButton() {
        showAllTextButton = findViewById(R.id.show_all)
        showAllTextButton.visibility = View.GONE
        showAllTextButton.setOnClickListener {
            descriptionExpanded = true
            postDescriptionView.maxLines = Integer.MAX_VALUE
            showAllTextButton.visibility = View.GONE
            requestLayout()
        }
        showAllTextButton.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (showAllTextButton.height > 0) {
                    showAllTextButton.paint.shader = LinearGradient(
                        0f, 0f, 0f, showAllTextButton.height.toFloat(),
                        ContextCompat.getColor(context, R.color.dark_green_variant),
                        ContextCompat.getColor(context, R.color.dark_green),
                        Shader.TileMode.CLAMP
                    )
                    showAllTextButton.invalidate()
                    showAllTextButton.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
    }

    private fun initPostDescriptionTextView() {
        postDescriptionView = findViewById<TextView>(R.id.post_description)
        postDescriptionView.text = postDescription
    }

    private fun initPostTitleTextView() {
        postTitleView = findViewById<TextView>(R.id.post_title)
        postTitleView.text = postTitle
    }

    private fun initPostImageView() {
        postImageView = findViewById<ShapeableImageView>(R.id.post_image)
        bindPostImage()
    }

    private fun bindPostImage() {
        val hasImage = postUri.isNotBlank() && postUri != EMPTY
        postImageView.visibility = if (hasImage) View.VISIBLE else View.GONE
        if (hasImage) {
            postImageView.setImageUri(postUri.toUri())
        }
        requestLayout()
    }

    private fun hasPostImage(): Boolean = postImageView.visibility != View.GONE

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val actualWidth = resolveSize(
            measuredWidth - paddingRight - paddingLeft,
            widthMeasureSpec
        )
        val imageBlock = if (hasPostImage()) {
            postImageView.measuredHeight + VERTICAL_SPACING_18.dp()
        } else {
            0
        }
        val showAllBlock = if (showAllTextButton.visibility != View.GONE) {
            showAllTextButton.measuredHeight + VERTICAL_SPACING_8.dp()
        } else {
            0
        }
        val actualHeight = resolveSize(
            imageBlock + visibleHeight(postTitleView) + visibleHeight(postDescriptionView)
                    + showAllBlock + hashtagsFlexbox.measuredHeight + maxOf(
                likesActionView.measuredHeight,
                replyActionView.measuredHeight,
                viewsActionView.measuredHeight
            ) + VERTICAL_SPACING_4.dp() + VERTICAL_SPACING_8.dp() + VERTICAL_SPACING_18.dp() * 2,
            heightMeasureSpec
        )
        setMeasuredDimension(actualWidth, actualHeight)
    }

    override fun onLayout(
        p0: Boolean,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int
    ) {
        var contentTop = paddingTop
        if (hasPostImage()) {
            val (postImageLeft, postImageRight) = horizontalPosition(postImageView, paddingLeft)
            val (postImageTop, postImageBottom) = verticalPosition(postImageView, paddingTop)
            postImageView.layout(postImageLeft, postImageTop, postImageRight, postImageBottom)
            contentTop = postImageBottom + VERTICAL_SPACING_18.dp()
        }

        var nextTop = contentTop
        if (postTitleView.visibility != View.GONE) {
            val (titleLeft, titleRight) = horizontalPosition(postTitleView, paddingLeft)
            val (titleTop, titleBottom) = verticalPosition(postTitleView, nextTop)
            postTitleView.layout(titleLeft, titleTop, titleRight, titleBottom)
            nextTop = titleBottom + VERTICAL_SPACING_8.dp()
        }

        if (postDescriptionView.visibility != View.GONE) {
            val (descriptionLeft, descriptionRight) = horizontalPosition(postDescriptionView)
            val (descriptionTop, descriptionBottom) = verticalPosition(postDescriptionView, nextTop)
            postDescriptionView.layout(
                descriptionLeft,
                descriptionTop,
                descriptionRight,
                descriptionBottom
            )
            nextTop = descriptionBottom
        }

        if (showAllTextButton.visibility != View.GONE) {
            nextTop += VERTICAL_SPACING_8.dp()
            val (showLeft, showRight) = horizontalPosition(showAllTextButton, paddingLeft)
            val (showTop, showBottom) = verticalPosition(showAllTextButton, nextTop)
            showAllTextButton.layout(showLeft, showTop, showRight, showBottom)
            nextTop = showBottom
        }

        val (hashtagLeft, hashtagRight) = horizontalPosition(hashtagsFlexbox, paddingLeft)
        val (hashtagTop, hashtagBottom) = verticalPosition(
            hashtagsFlexbox,
            nextTop + VERTICAL_SPACING_4.dp()
        )
        hashtagsFlexbox.layout(hashtagLeft, hashtagTop, hashtagRight, hashtagBottom)

        val actionsTop = hashtagBottom + VERTICAL_SPACING_18.dp()
        val (likesLeft, likesRight) = horizontalPosition(likesActionView, paddingLeft)
        val (likesTop, likesBottom) = verticalPosition(likesActionView, actionsTop)
        likesActionView.layout(likesLeft, likesTop, likesRight, likesBottom)

        var afterLikes = likesRight
        if (commentActionView.visibility != View.GONE) {
            val (commentLeft, commentRight) = horizontalPosition(
                commentActionView,
                afterLikes + SPACING.dp()
            )
            val (commentTop, commentBottom) = verticalPosition(commentActionView, actionsTop)
            commentActionView.layout(commentLeft, commentTop, commentRight, commentBottom)
            afterLikes = commentRight
        }

        if (replyActionView.visibility != View.GONE) {
            val (replyLeft, replyRight) = horizontalPosition(
                replyActionView,
                afterLikes + SPACING.dp()
            )
            val (replyTop, replyBottom) = verticalPosition(replyActionView, actionsTop)
            replyActionView.layout(replyLeft, replyTop, replyRight, replyBottom)
        }

        val viewsRight = measuredWidth - paddingRight
        val viewsLeft = viewsRight - viewsActionView.measuredWidth
        val (viewsTop, viewsBottom) = verticalPosition(viewsActionView, actionsTop)
        viewsActionView.layout(viewsLeft, viewsTop, viewsRight, viewsBottom)
    }

    private fun visibleHeight(view: View): Int =
        if (view.visibility == View.GONE) 0 else view.measuredHeight

    private fun horizontalPosition(view: View, prevSize: Int = 0): Pair<Int, Int> {
        val left = prevSize
        return Pair(prevSize, left + view.measuredWidth)
    }

    private fun verticalPosition(view: View, prevSize: Int = 0): Pair<Int, Int> {
        val top = prevSize
        return Pair(top, top + view.measuredHeight)
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        const val EMPTY = "Empty"
        const val HASHTAG_TEXT_SIZE = 12f
        const val SPACING = 14
        const val VERTICAL_SPACING_18 = 18
        const val VERTICAL_SPACING_8 = 8
        const val VERTICAL_SPACING_4 = 4
    }
}