package petlink.android.core_ui.custom_view.comment

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes
import androidx.core.net.toUri
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.comment.marker.CommentMarkerView
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

    private var isLiked: Boolean = false
    private var likesCount = -1
    private var repliesCount = -1
    private var name = NONE
    private var message = NONE

    fun setIsLiked(value: Boolean){
        if (value != isLiked){
            isLiked = value
            checkIsLiked(value)
        }
    }

    fun setLikesCount(value: Int){
        if (value != likesCount){
            likesCount = value
            likesTextView.text = value.toString()
            isUseful(likesCount)
        }
    }

    fun setRepliesCount(value: Int){
        if (value != repliesCount){
            repliesCount = value
            repliesTextView.text = value.toString()
        }
    }

    fun setMessage(value: String){
        if (value != message){
            message = value
            messageTextView.text = value
        }
    }

    fun setName(value: String){
        if (value != name){
            name = value
            nameTextView.text = value
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.comment_layout, this, true)
        context.withStyledAttributes(attributeSet, R.styleable.CommentView) {
            initNameTextView()
            initCommentTextView()
            iniMarkerView()
            initLikesTextView()
            initRepliesTextView()
            initLikesIcon()
            initAvatarView()
            repliesIcon = findViewById(R.id.icon_reply)
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

        val maxTextWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight - avatarView.measuredWidth - SPACING
        val childWidthSpec = MeasureSpec.makeMeasureSpec(maxTextWidth, MeasureSpec.AT_MOST)

        nameTextView.measure(childWidthSpec, heightMeasureSpec)
        messageTextView.measure(childWidthSpec, heightMeasureSpec)

        val actualWidth = resolveSize(
            paddingLeft + paddingRight + avatarView.measuredWidth + maxOf(
                nameTextView.measuredWidth, messageTextView.measuredWidth
            ) + SPACING,
            widthMeasureSpec
        )
        val actualHeight = resolveSize(
            paddingTop + paddingBottom + maxOf(
                avatarView.measuredHeight,
                nameTextView.measuredHeight + messageTextView.measuredHeight
            ) + likesIcon.measuredHeight + LINE_SPACING,
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

        val messageLeft = avatarRight + SPACING
        val messageRight = messageLeft + messageTextView.measuredWidth
        val messageTop = nameBottom + LINE_SPACING
        val messageBottom = messageTop + messageTextView.measuredHeight
        messageTextView.layout(messageLeft, messageTop, messageRight, messageBottom)

        val markerRight = measuredWidth - paddingRight
        val markerLeft = markerRight - markerView.measuredWidth
        val markerBottom = paddingTop + markerView.measuredHeight
        markerView.layout(markerLeft, paddingTop, markerRight, markerBottom)

        val likeIconLeft = avatarRight + SPACING
        val likeIconRight = likeIconLeft + likesIcon.measuredWidth
        val likeIconTop = messageBottom + LINE_SPACING
        val likeIconBottom = likeIconTop + likesIcon.measuredHeight
        likesIcon.layout(likeIconLeft, likeIconTop, likeIconRight, likeIconBottom)

        val likesTextLeft = likeIconRight + ICON_SPACING
        val likesTextRight = likesTextLeft + likesTextView.measuredWidth
        val likesTextTop = messageBottom + LINE_SPACING + (likesIcon.measuredHeight - likesTextView.measuredHeight) / 2
        val likesTextBottom = likesTextTop + likesTextView.measuredHeight
        likesTextView.layout(likesTextLeft, likesTextTop, likesTextRight, likesTextBottom)

        val repliesIconLeft = likesTextRight + COUNTERS_SPACING
        val repliesIconRight = repliesIconLeft + repliesIcon.measuredWidth
        val repliesIconTop = messageBottom + LINE_SPACING
        val repliesIconBottom = repliesIconTop + repliesIcon.measuredHeight
        repliesIcon.layout(repliesIconLeft, repliesIconTop, repliesIconRight, repliesIconBottom)

        val repliesTextLeft = repliesIconRight + ICON_SPACING
        val repliesTextRight = repliesTextLeft + repliesTextView.measuredWidth
        val repliesTextTop = messageBottom + LINE_SPACING + (repliesIcon.measuredHeight - repliesTextView.measuredHeight) / 2
        val repliesTextBottom = repliesTextTop + repliesIcon.measuredHeight
        repliesTextView.layout(repliesTextLeft, repliesTextTop, repliesTextRight, repliesTextBottom)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }


    private fun TypedArray.initRepliesTextView() {
        repliesCount = getInt(R.styleable.CommentView_replies_count, -1)
        repliesTextView = findViewById(R.id.replies_count)
        repliesTextView.text = repliesCount.toString()
    }

    private fun TypedArray.initLikesTextView() {
        likesCount = getInt(R.styleable.CommentView_likes_count, -1)
        likesTextView = findViewById(R.id.likes_count)
        likesTextView.text = likesCount.toString()
    }

    private fun TypedArray.iniMarkerView(){
        markerView = findViewById(R.id.marker)
        isUseful(likesCount)
    }

    private fun isUseful(value: Int) {
        if (value >= USEFUL_LIKES_COUNT) markerView.visibility = VISIBLE
        else markerView.visibility = GONE
    }

    private fun TypedArray.initAvatarView(){
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

    private fun TypedArray.initLikesIcon(){
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

    companion object{
        const val NONE: String = "NONE"
        const val USEFUL_LIKES_COUNT = 15
        const val SPACING = 20
        const val LINE_SPACING = 8
        const val ICON_SPACING = 4
        const val COUNTERS_SPACING = 22
    }

}