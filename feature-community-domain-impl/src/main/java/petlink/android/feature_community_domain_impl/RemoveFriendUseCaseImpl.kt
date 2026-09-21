package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.usecase.RemoveFriendUseCase
import javax.inject.Inject

class RemoveFriendUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : RemoveFriendUseCase {
    override suspend fun invoke(userId: String) {
        repository.removeFriend(userId)
    }
}
