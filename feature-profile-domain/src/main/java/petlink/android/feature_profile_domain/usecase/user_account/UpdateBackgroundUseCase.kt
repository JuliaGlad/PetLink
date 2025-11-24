package petlink.android.feature_profile_domain.usecase.user_account

interface UpdateBackgroundUseCase {
    suspend fun invoke(uri: String)
}