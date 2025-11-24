package petlink.android.feature_profile_domain.usecase.user_auth

interface CreateUserUseCase  {
    suspend fun invoke(email: String, password: String)
}