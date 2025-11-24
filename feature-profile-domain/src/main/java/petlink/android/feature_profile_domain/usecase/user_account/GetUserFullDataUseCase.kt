package petlink.android.feature_profile_domain.usecase.user_account

import petlink.android.feature_profile_domain.model.user_account.UserDomain

interface GetUserFullDataUseCase {
    suspend fun invoke(): UserDomain
}