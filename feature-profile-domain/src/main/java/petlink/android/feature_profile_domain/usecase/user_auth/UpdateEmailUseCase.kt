package petlink.android.feature_profile_domain.usecase.user_auth

interface UpdateEmailUseCase {
    suspend fun invoke(password: String, newEmail: String)
}