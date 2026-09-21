package petlink.android.core_di.community.modules

import dagger.Binds
import dagger.Module
import petlink.android.core_di.community.component.CommunityScope
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
import petlink.android.feature_community_domain_impl.AddFriendUseCaseImpl
import petlink.android.feature_community_domain_impl.RemoveFriendUseCaseImpl
import petlink.android.feature_community_domain_impl.AddNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.AddPostCommentUseCaseImpl
import petlink.android.feature_community_domain_impl.CreateChatUseCaseImpl
import petlink.android.feature_community_domain_impl.CreatePostUseCaseImpl
import petlink.android.feature_community_domain_impl.DeleteNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.GetChatMessagesUseCaseImpl
import petlink.android.feature_community_domain_impl.GetChatsUseCaseImpl
import petlink.android.feature_community_domain_impl.GetCommunityPostsUseCaseImpl
import petlink.android.feature_community_domain_impl.GetFriendsUseCaseImpl
import petlink.android.feature_community_domain_impl.GetNewsCommunityByIdUseCaseImpl
import petlink.android.feature_community_domain_impl.GetNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.GetOtherUsersUseCaseImpl
import petlink.android.feature_community_domain_impl.GetOwnedCommunitiesUseCaseImpl
import petlink.android.feature_community_domain_impl.GetPostCommentsUseCaseImpl
import petlink.android.feature_community_domain_impl.GetSubscribedCommunitiesUseCaseImpl
import petlink.android.feature_community_domain_impl.GetUserByIdUseCaseImpl
import petlink.android.feature_community_domain_impl.GetUsersByIdsUseCaseImpl
import petlink.android.feature_community_domain_impl.MarkPostViewedUseCaseImpl
import petlink.android.feature_community_domain_impl.SendChatMessageUseCaseImpl
import petlink.android.feature_community_domain_impl.SubscribeToNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.ToggleCommentLikeUseCaseImpl
import petlink.android.feature_community_domain_impl.TogglePostLikeUseCaseImpl
import petlink.android.feature_community_domain_impl.UnsubscribeFromNewsCommunityUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityAvatarUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityBackgroundUseCaseImpl
import petlink.android.feature_community_domain_impl.UpdateNewsCommunityUseCaseImpl

@Module
interface CommunityDomainModule {

    @CommunityScope
    @Binds
    fun bindGetSubscribedCommunitiesUseCase(
        getSubscribedCommunitiesUseCaseImpl: GetSubscribedCommunitiesUseCaseImpl
    ): GetSubscribedCommunitiesUseCase

    @CommunityScope
    @Binds
    fun bindGetOwnedCommunitiesUseCase(
        getOwnedCommunitiesUseCaseImpl: GetOwnedCommunitiesUseCaseImpl
    ): GetOwnedCommunitiesUseCase

    @CommunityScope
    @Binds
    fun bindAddNewsCommunityUseCase(
        addNewsCommunityUseCaseImpl: AddNewsCommunityUseCaseImpl
    ): AddNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindDeleteCommunityUseCase(
        deleteCommunityUseCaseImpl: DeleteNewsCommunityUseCaseImpl
    ): DeleteNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindGetNewsCommunityByIdUseCase(
        getNewsCommunityByIdUseCaseImpl: GetNewsCommunityByIdUseCaseImpl
    ): GetNewsCommunityByIdUseCase

    @CommunityScope
    @Binds
    fun bindGetNewsCommunityUseCase(
        getNewsCommunityUseCaseImpl: GetNewsCommunityUseCaseImpl
    ): GetNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindSubscribeToNewsCommunityUseCase(
        subscribeToNewsCommunityUseCaseImpl: SubscribeToNewsCommunityUseCaseImpl
    ): SubscribeToNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUnsubscribeFromNewsCommunityUseCase(
        unsubscribeFromNewsCommunityUseCaseImpl: UnsubscribeFromNewsCommunityUseCaseImpl
    ): UnsubscribeFromNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityUseCase(
        updateNewsCommunityUseCaseImpl: UpdateNewsCommunityUseCaseImpl
    ): UpdateNewsCommunityUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityAvatarUseCase(
        updateNewsCommunityAvatarUseCaseImpl: UpdateNewsCommunityAvatarUseCaseImpl
    ): UpdateNewsCommunityAvatarUseCase

    @CommunityScope
    @Binds
    fun bindUpdateNewsCommunityBackgroundUseCase(
        updateNewsCommunityBackgroundUseCaseImpl: UpdateNewsCommunityBackgroundUseCaseImpl
    ): UpdateNewsCommunityBackgroundUseCase

    @CommunityScope
    @Binds
    fun bindGetCommunityPostsUseCase(
        getCommunityPostsUseCaseImpl: GetCommunityPostsUseCaseImpl
    ): GetCommunityPostsUseCase

    @CommunityScope
    @Binds
    fun bindCreatePostUseCase(
        createPostUseCaseImpl: CreatePostUseCaseImpl
    ): CreatePostUseCase

    @CommunityScope
    @Binds
    fun bindGetFriendsUseCase(
        getFriendsUseCaseImpl: GetFriendsUseCaseImpl
    ): GetFriendsUseCase

    @CommunityScope
    @Binds
    fun bindGetOtherUsersUseCase(
        getOtherUsersUseCaseImpl: GetOtherUsersUseCaseImpl
    ): GetOtherUsersUseCase

    @CommunityScope
    @Binds
    fun bindGetChatsUseCase(
        getChatsUseCaseImpl: GetChatsUseCaseImpl
    ): GetChatsUseCase

    @CommunityScope
    @Binds
    fun bindGetUserByIdUseCase(
        getUserByIdUseCaseImpl: GetUserByIdUseCaseImpl
    ): GetUserByIdUseCase

    @CommunityScope
    @Binds
    fun bindGetUsersByIdsUseCase(
        getUsersByIdsUseCaseImpl: GetUsersByIdsUseCaseImpl
    ): GetUsersByIdsUseCase

    @CommunityScope
    @Binds
    fun bindAddFriendUseCase(
        addFriendUseCaseImpl: AddFriendUseCaseImpl
    ): AddFriendUseCase

    @CommunityScope
    @Binds
    fun bindRemoveFriendUseCase(
        removeFriendUseCaseImpl: RemoveFriendUseCaseImpl
    ): RemoveFriendUseCase

    @CommunityScope
    @Binds
    fun bindCreateChatUseCase(
        createChatUseCaseImpl: CreateChatUseCaseImpl
    ): CreateChatUseCase

    @CommunityScope
    @Binds
    fun bindGetChatMessagesUseCase(
        getChatMessagesUseCaseImpl: GetChatMessagesUseCaseImpl
    ): GetChatMessagesUseCase

    @CommunityScope
    @Binds
    fun bindSendChatMessageUseCase(
        sendChatMessageUseCaseImpl: SendChatMessageUseCaseImpl
    ): SendChatMessageUseCase

    @CommunityScope
    @Binds
    fun bindTogglePostLikeUseCase(
        togglePostLikeUseCaseImpl: TogglePostLikeUseCaseImpl
    ): TogglePostLikeUseCase

    @CommunityScope
    @Binds
    fun bindMarkPostViewedUseCase(
        markPostViewedUseCaseImpl: MarkPostViewedUseCaseImpl
    ): MarkPostViewedUseCase

    @CommunityScope
    @Binds
    fun bindGetPostCommentsUseCase(
        getPostCommentsUseCaseImpl: GetPostCommentsUseCaseImpl
    ): GetPostCommentsUseCase

    @CommunityScope
    @Binds
    fun bindAddPostCommentUseCase(
        addPostCommentUseCaseImpl: AddPostCommentUseCaseImpl
    ): AddPostCommentUseCase

    @CommunityScope
    @Binds
    fun bindToggleCommentLikeUseCase(
        toggleCommentLikeUseCaseImpl: ToggleCommentLikeUseCaseImpl
    ): ToggleCommentLikeUseCase
}
