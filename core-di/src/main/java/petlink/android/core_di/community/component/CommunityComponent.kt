package petlink.android.core_di.community.component

import com.github.terrakok.cicerone.Router
import dagger.Component
import petlink.android.core_di.app.AppComponent
import petlink.android.core_di.community.modules.CommunityDataModule
import petlink.android.core_di.community.modules.CommunityDatabaseModule
import petlink.android.core_di.community.modules.CommunityDomainModule
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import petlink.android.feature_community_domain.usecase.AddNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.AddPostCommentUseCase
import petlink.android.feature_community_domain.usecase.CreateChatUseCase
import petlink.android.feature_community_domain.usecase.CreatePostUseCase
import petlink.android.feature_community_domain.usecase.DeleteNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetChatMessagesUseCase
import petlink.android.feature_community_domain.usecase.GetChatsUseCase
import petlink.android.feature_community_domain.usecase.GetCommunityPostsUseCase
import petlink.android.feature_community_domain.usecase.GetFriendsUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.GetNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.GetOtherUsersUseCase
import petlink.android.feature_community_domain.usecase.GetOwnedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetPostCommentsUseCase
import petlink.android.feature_community_domain.usecase.GetSubscribedCommunitiesUseCase
import petlink.android.feature_community_domain.usecase.GetUserByIdUseCase
import petlink.android.feature_community_domain.usecase.GetUsersByIdsUseCase
import petlink.android.feature_community_domain.usecase.MarkPostViewedUseCase
import petlink.android.feature_community_domain.usecase.SendChatMessageUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.ToggleCommentLikeUseCase
import petlink.android.feature_community_domain.usecase.TogglePostLikeUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityUseCase
import javax.inject.Scope

@CommunityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        CommunityDatabaseModule::class,
        CommunityDataModule::class,
        CommunityDomainModule::class
    ]
)
interface CommunityComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CommunityComponent
    }

    fun router(): Router

    fun getOwnedCommunitiesUseCase(): GetOwnedCommunitiesUseCase

    fun getSubscribedCommunitiesUseCase(): GetSubscribedCommunitiesUseCase

    fun addNewsCommunityUseCase(): AddNewsCommunityUseCase

    fun deleteCommunityUseCase(): DeleteNewsCommunityUseCase

    fun getNewsCommunityByIdUseCase(): GetNewsCommunityByIdUseCase

    fun getNewsCommunityUseCase(): GetNewsCommunityUseCase

    fun subscribeToNewsCommunityUseCase(): SubscribeToNewsCommunityUseCase

    fun unsubscribeFromNewsCommunityUseCase(): UnsubscribeFromNewsCommunityUseCase

    fun updateNewsCommunityUseCase(): UpdateNewsCommunityUseCase

    fun updateNewsCommunityAvatarUseCase(): UpdateNewsCommunityAvatarUseCase

    fun updateNewsCommunityBackgroundUseCase(): UpdateNewsCommunityBackgroundUseCase

    fun getCommunityPostsUseCase(): GetCommunityPostsUseCase

    fun createPostUseCase(): CreatePostUseCase

    fun getFriendsUseCase(): GetFriendsUseCase

    fun getOtherUsersUseCase(): GetOtherUsersUseCase

    fun getChatsUseCase(): GetChatsUseCase

    fun getUserByIdUseCase(): GetUserByIdUseCase

    fun getUsersByIdsUseCase(): GetUsersByIdsUseCase

    fun addFriendUseCase(): AddFriendUseCase

    fun removeFriendUseCase(): RemoveFriendUseCase

    fun createChatUseCase(): CreateChatUseCase

    fun getChatMessagesUseCase(): GetChatMessagesUseCase

    fun sendChatMessageUseCase(): SendChatMessageUseCase

    fun togglePostLikeUseCase(): TogglePostLikeUseCase

    fun markPostViewedUseCase(): MarkPostViewedUseCase

    fun getPostCommentsUseCase(): GetPostCommentsUseCase

    fun addPostCommentUseCase(): AddPostCommentUseCase

    fun toggleCommentLikeUseCase(): ToggleCommentLikeUseCase
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CommunityScope
