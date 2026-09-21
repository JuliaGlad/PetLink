package petlink.android.feature_community_ui_create_post.fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import petlink.android.core_di.app.AppComponentHolder.appComponent
import petlink.android.core_di.community.component.DaggerCommunityComponent
import petlink.android.core_di.profile.component.DaggerProfileComponent
import petlink.android.core_mvi.MviBaseFragment
import petlink.android.core_mvi.MviStore
import petlink.android.core_ui.R
import petlink.android.core_ui.custom_view.round_avatar.ShapeableImageView
import petlink.android.core_ui.image_picker.ImagePickerHelper
import petlink.android.core_ui.playPressAnimation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_ui_create_post.CreatePostActivity
import petlink.android.feature_community_ui_create_post.databinding.FragmentCreatePostBinding
import petlink.android.feature_community_ui_create_post.fragment.di.DaggerCreatePostComponent
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostEffect
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostIntent
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostLocalDi
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostMviState
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostPartialState
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostState
import petlink.android.feature_community_ui_create_post.fragment.mvi.CreatePostStoreFactory
import javax.inject.Inject

class CreatePostFragment : MviBaseFragment<
        CreatePostPartialState,
        CreatePostIntent,
        CreatePostMviState,
        CreatePostEffect>(petlink.android.feature_community_ui_create_post.R.layout.fragment_create_post) {

    @Inject
    lateinit var localDi: CreatePostLocalDi

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!
    private val photos = mutableListOf<String>()
    private val imagePicker = ImagePickerHelper(this)

    private val isUserPost: Boolean by lazy {
        activity?.intent?.getBooleanExtra(CreatePostActivity.IS_USER_POST_ARG, false) ?: false
    }

    private val communityId: String by lazy {
        activity?.intent?.getStringExtra(CreatePostActivity.COMMUNITY_ID_ARG).orEmpty()
    }

    private val communityType: CommunitiesTypeTag by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity?.intent?.getParcelableExtra(
                CreatePostActivity.COMMUNITY_TYPE_ARG,
                CommunitiesTypeTag::class.java
            ) ?: CommunitiesTypeTag.NewsTag
        } else {
            @Suppress("DEPRECATION")
            activity?.intent?.getParcelableExtra(CreatePostActivity.COMMUNITY_TYPE_ARG)
                ?: CommunitiesTypeTag.NewsTag
        }
    }

    private val isQuestion: Boolean
        get() = !isUserPost && communityType is CommunitiesTypeTag.QuestionTag

    override val store: MviStore<CreatePostPartialState, CreatePostIntent, CreatePostMviState, CreatePostEffect>
            by viewModels {
                CreatePostStoreFactory(
                    reducer = localDi.reducer,
                    actor = localDi.actor
                )
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val communityComponent = DaggerCommunityComponent.factory().create(appComponent)
        val profileComponent = DaggerProfileComponent.factory().create(appComponent)
        DaggerCreatePostComponent.factory().create(
            communityComponent.createPostUseCase(),
            profileComponent.createUserPostUseCase()
        ).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyScreenType()
        binding.iconBack.setOnClickListener { activity?.finish() }
        binding.addPhoto.setOnClickListener {
            it.playPressAnimation()
            val cropWidth = if (isQuestion) 1f else 380f
            val cropHeight = if (isQuestion) 1f else 210f
            imagePicker.ensurePermissions {
                imagePicker.pick(cropWidth = cropWidth, cropHeight = cropHeight) { uri ->
                    val value = uri.toString()
                    photos.add(value)
                    addPhoto(value)
                }
            }
        }
        binding.buttonSave.setOnClickListener {
            it.playPressAnimation()
            submit()
        }
    }

    override fun render(state: CreatePostMviState) {
        when (val value = state.value) {
            CreatePostState.Init -> {
                binding.loading.root.visibility = GONE
                binding.buttonSave.isEnabled = true
            }
            CreatePostState.Loading -> {
                binding.loading.root.visibility = VISIBLE
                binding.buttonSave.isEnabled = false
            }
            is CreatePostState.PostCreated -> {
                val data = Intent().apply {
                    putExtra(CreatePostActivity.POST_ID_ARG, value.postId)
                    putExtra(CreatePostActivity.POST_TITLE_ARG, value.title)
                    putExtra(CreatePostActivity.POST_DESCRIPTION_ARG, value.description)
                    putStringArrayListExtra(
                        CreatePostActivity.POST_PHOTOS_ARG,
                        ArrayList(value.photos)
                    )
                }
                activity?.setResult(Activity.RESULT_OK, data)
                activity?.finish()
            }
            is CreatePostState.Error -> {
                binding.loading.root.visibility = GONE
                binding.buttonSave.isEnabled = true
                Snackbar.make(binding.root, R.string.error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun resolveEffect(effect: CreatePostEffect) {
        when (effect) {
            CreatePostEffect.CloseScreen -> activity?.finish()
        }
    }

    private fun applyScreenType() {
        when {
            isUserPost || communityType is CommunitiesTypeTag.NewsTag -> {
                binding.screenTitle.text = getString(R.string.post_creation)
                binding.titleBlock.visibility = VISIBLE
                binding.textLabel.text = getString(R.string.post_text)
                binding.editDescription.hint = getString(R.string.description_optional)
                binding.buttonSave.text = getString(R.string.create_post)
            }
            communityType is CommunitiesTypeTag.QuestionTag -> {
                binding.screenTitle.text = getString(R.string.question_creation)
                binding.titleBlock.visibility = GONE
                binding.textLabel.text = getString(R.string.question_text)
                binding.editDescription.hint = getString(R.string.describe_your_question)
                binding.buttonSave.text = getString(R.string.create_question_submit)
            }
            communityType is CommunitiesTypeTag.PhotosTag -> {
                binding.screenTitle.text = getString(R.string.add_photo)
                binding.titleBlock.visibility = GONE
                binding.textLabel.text = getString(R.string.description)
                binding.editDescription.hint = getString(R.string.description_optional)
                binding.buttonSave.text = getString(R.string.add_photo)
            }
        }
    }

    private fun submit() {
        val title = binding.editTitle.text?.toString().orEmpty().trim()
        val description = binding.editDescription.text?.toString().orEmpty().trim()
        val isValid = when {
            isUserPost || communityType is CommunitiesTypeTag.NewsTag -> title.isNotBlank()
            communityType is CommunitiesTypeTag.QuestionTag -> description.isNotBlank()
            communityType is CommunitiesTypeTag.PhotosTag -> photos.isNotEmpty()
            else -> title.isNotBlank()
        }
        if (!isValid) return
        store.sendIntent(
            CreatePostIntent.CreatePost(
                title = title,
                description = description,
                photos = photos.toList(),
                isUserPost = isUserPost,
                communityId = communityId,
                communityType = communityType
            )
        )
    }

    private fun addPhoto(uri: String) {
        val height = (88 * resources.displayMetrics.density).toInt()
        val width = if (isQuestion) height else (height * 380 / 210f).toInt()
        val margin = (8 * resources.displayMetrics.density).toInt()
        val image = ShapeableImageView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(width, height).apply {
                marginStart = margin
            }
            background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_round_rectangle)
            clipToOutline = true
            setImageUri(uri.toUri(), R.drawable.add_cover)
        }
        binding.photosContainer.addView(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
