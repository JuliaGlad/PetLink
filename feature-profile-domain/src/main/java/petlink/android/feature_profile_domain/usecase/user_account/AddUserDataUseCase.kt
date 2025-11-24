package petlink.android.feature_profile_domain.usecase.user_account

interface AddUserDataUseCase {
    suspend fun invoke(
        petImageUri: String = "",
        petName: String,
        petBirthday: String,
        petType: String,
        petGender: String,
        imageUri: String,
        name: String,
        surname: String,
        birthday: String,
        gender: String,
        city: String
    )
}