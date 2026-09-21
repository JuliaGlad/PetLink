package petlink.android.feature_community_ui_create_post.fragment.mvi

import petlink.android.core_mvi.MviEffect

sealed interface CreatePostEffect : MviEffect {
    data object CloseScreen : CreatePostEffect
}
