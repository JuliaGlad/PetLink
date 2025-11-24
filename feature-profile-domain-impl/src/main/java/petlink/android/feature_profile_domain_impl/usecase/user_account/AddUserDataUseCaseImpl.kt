package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.usecase.user_account.AddUserDataUseCase
import javax.inject.Inject


class AddUserDataUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
): AddUserDataUseCase {
    override suspend fun invoke(
        petImageUri: String,
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
    ){
        repository.addUserData(
            imageUri = imageUri,
            name = name,
            birthday = birthday,
            petType = petType,
            gender = gender,
            petName = petName,
            city = city,
            surname = surname,
            petBirthday = petBirthday,
            petGender = petGender,
            petImageUri = petImageUri
        )
    }
}