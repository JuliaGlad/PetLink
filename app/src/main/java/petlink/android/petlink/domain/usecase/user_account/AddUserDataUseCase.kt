package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import javax.inject.Inject

class AddUserDataUseCase @Inject constructor(
    private val repository: UserAccountRepository
) {
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