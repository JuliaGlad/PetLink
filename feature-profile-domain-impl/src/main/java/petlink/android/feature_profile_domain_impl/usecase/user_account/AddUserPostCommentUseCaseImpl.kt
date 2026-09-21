package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.mapper.toDomain
import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.model.user_account.UserPostCommentDomain
import petlink.android.feature_profile_domain.usecase.user_account.AddUserPostCommentUseCase
import javax.inject.Inject

class AddUserPostCommentUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
) : AddUserPostCommentUseCase {
    override suspend fun invoke(
        userId: String,
        postId: String,
        text: String,
        parentId: String,
        photos: List<String>
    ): UserPostCommentDomain =
        repository.addPostComment(userId, postId, text, parentId, photos).toDomain()
}
