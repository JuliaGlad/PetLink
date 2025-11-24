package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.usecase.user_account.EditOwnerDataUseCase
import javax.inject.Inject

class EditOwnerDataUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
): EditOwnerDataUseCase {
    override suspend fun invoke(
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