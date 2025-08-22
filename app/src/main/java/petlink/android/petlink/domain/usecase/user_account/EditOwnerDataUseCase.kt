package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import javax.inject.Inject

class EditOwnerDataUseCase @Inject constructor(
    private val repository: UserAccountRepository
) {
    suspend fun invoke(
        imageUri: String?,
        name: String?,
        surname: String?,
        birthday: String?,
        gender: String?,
        city: String?
    ){
        repository.editOwnerData(
            imageUri = imageUri,
            name = name,
            surname = surname,
            birthday = birthday,
            gender = gender,
            city = city
        )
    }
}