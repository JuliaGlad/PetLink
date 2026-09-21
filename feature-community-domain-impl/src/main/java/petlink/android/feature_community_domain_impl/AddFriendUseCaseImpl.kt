package petlink.android.feature_community_domain_impl

import petlink.android.feature_community_data.repository.ChatRepository
import petlink.android.feature_community_domain.usecase.AddFriendUseCase
import javax.inject.Inject

class AddFriendUseCaseImpl @Inject constructor(
    private val repository: ChatRepository
) : AddFriendUseCase {
    override suspend fun invoke(userId: String) {
        repository.addFriend(userId)
    }
}
