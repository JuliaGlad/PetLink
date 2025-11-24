package petlink.android.feature_profile_domain.usecase.user_auth

interface DeleteAccountUseCase {
    suspend fun invoke(password: String)
}