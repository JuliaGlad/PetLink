package petlink.android.feature_profile_domain.usecase.user_auth

interface SignInUseCase  {
    suspend fun invoke(email: String, password: String)
}