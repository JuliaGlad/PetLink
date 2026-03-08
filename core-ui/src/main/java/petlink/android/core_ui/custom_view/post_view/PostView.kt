package petlink.android.core_ui.custom_view.post_view

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
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
                postImageView.setImageUri(field.toUri())
            }
        }

    var postTitle: String = EMPTY
        set(value) {
            if (value != field) {
                field = value
                postTitleView.text = field
            }
        }
    var postDescription: String = EMPTY
        set(value) {
            if (value != field) {
                field = value
                postDescriptionView.text = field
            }
        }
    var likesCount: Int = 0
        set(value) {
            if (value != field) {
                field = value
                likesActionView.actionsCount = field
                val imageId =
                    if (value > 0) R.drawable.ic_like_fill
                    else R.drawable.ic_like_no_fill
                likesActionView.icon =
                    ResourcesCompat.getDrawable(resources, imageId, context.theme)
            }
        }

    var commentCount: Int = 0
        set(value) {
            if (value != field) {
                field = value
                commentActionView.actionsCount = field
            }
        }
    var replyCount: Int = 0
        set(value) {
            if (value != field) {
                field = value
                replyActionView.actionsCount = field
            }
        }
    var viewsCount: Int = 0
        set(value) {
            if (value != field) {
                field = value
                viewsActionView.actionsCount = field
            }
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

    private fun initViewsActionView() {
        viewsActionView = findViewById<PostActionView>(R.id.view_icon)
        viewsActionView.actionsCount = viewsCount
    }

    private fun initLikeActionView() {
        likesActionView = findViewById<PostActionView>(R.id.likes_icon)
        likesActionView.actionsCount = likesCount
        val imageId =
            if (likesCount > 0) R.drawable.ic_like_fill
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
        showAllTextButton = findViewById<TextView>(R.id.show_all)
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
        if (postUri != EMPTY) {
            postImageView.setImageUri(postUri.toUri())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val actualWidth = resolveSize(
            measuredWidth - paddingRight - paddingLeft,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            postImageView.measuredHeight + postTitleView.measuredHeight + postDescriptionView.measuredHeight
                    + hashtagsFlexbox.measuredHeight + maxOf(
                likesActionView.measuredHeight,
                commentActionView.measuredHeight,
                replyActionView.measuredHeight,
                viewsActionView.measuredHeight
            ) + VERTICAL_SPACING_4.dp() + VERTICAL_SPACING_8.dp() + VERTICAL_SPACING_18.dp()*3,
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
        val (postImageLeft, postImageRight) = horizontalPosition(postImageView, paddingLeft)
        val (postImageTop, postImageBottom) = verticalPosition(postImageView, paddingTop)
        postImageView.layout(postImageLeft, postImageTop, postImageRight, postImageBottom)

        val (titleLeft, titleRight) = horizontalPosition(postTitleView, paddingLeft)
        val (titleTop, titleBottom) = verticalPosition(postTitleView, postImageBottom+VERTICAL_SPACING_18.dp())
        postTitleView.layout(titleLeft, titleTop, titleRight, titleBottom)

        val (descriptionLeft, descriptionRight) = horizontalPosition(postDescriptionView)
        val (descriptionTop, descriptionBottom) = verticalPosition(
            postDescriptionView,
            titleBottom+VERTICAL_SPACING_8.dp()
        )
        postDescriptionView.layout(
            descriptionLeft,
            descriptionTop,
            descriptionRight,
            descriptionBottom
        )

        val (hashtagLeft, hashtagRight) = horizontalPosition(hashtagsFlexbox, paddingLeft)
        val (hashtagTop, hashtagBottom) = verticalPosition(
            hashtagsFlexbox,
            descriptionBottom+VERTICAL_SPACING_4.dp()
        )
        hashtagsFlexbox.layout(hashtagLeft, hashtagTop, hashtagRight, hashtagBottom)

        val (likesLeft, likesRight) = horizontalPosition(likesActionView, paddingLeft)
        val (likesTop, likesBottom) = verticalPosition(
            likesActionView,
            hashtagBottom+VERTICAL_SPACING_18.dp()
        )
        likesActionView.layout(likesLeft, likesTop, likesRight, likesBottom)

        val (commentLeft, commentRight) = horizontalPosition(
            commentActionView,
            likesRight + SPACING.dp()
        )
        val (commentTop, commentBottom) = verticalPosition(
            likesActionView,
            hashtagBottom+VERTICAL_SPACING_18.dp()
        )
        commentActionView.layout(commentLeft, commentTop, commentRight, commentBottom)

        val (replyLeft, replyRight) = horizontalPosition(
            replyActionView,
            commentRight + SPACING.dp()
        )
        val (replyTop, replyBottom) = verticalPosition(replyActionView, hashtagBottom+VERTICAL_SPACING_18.dp())
        replyActionView.layout(replyLeft, replyTop, replyRight, replyBottom)

        val viewsRight = measuredWidth - paddingRight
        val viewsLeft = viewsRight - viewsActionView.measuredWidth
        val (viewsTop, viewsBottom) = verticalPosition(viewsActionView, hashtagBottom+VERTICAL_SPACING_18.dp())

        viewsActionView.layout(viewsLeft, viewsTop, viewsRight, viewsBottom)
    }

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