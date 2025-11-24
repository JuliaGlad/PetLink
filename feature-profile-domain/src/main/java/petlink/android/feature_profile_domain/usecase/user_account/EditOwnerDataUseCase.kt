package petlink.android.feature_profile_domain.usecase.user_account

interface EditOwnerDataUseCase {
    suspend fun invoke(
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    )
}