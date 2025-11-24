package petlink.android.feature_profile_domain.usecase.user_account

interface EditPetDataUseCase  {
    suspend fun invoke(
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    )
}