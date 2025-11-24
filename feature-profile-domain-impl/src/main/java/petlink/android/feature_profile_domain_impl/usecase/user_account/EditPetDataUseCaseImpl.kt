package petlink.android.feature_profile_domain_impl.usecase.user_account

import petlink.android.feature_profile_data.repository.UserAccountRepository
import petlink.android.feature_profile_domain.usecase.user_account.EditPetDataUseCase
import javax.inject.Inject

class EditPetDataUseCaseImpl @Inject constructor(
    private val repository: UserAccountRepository
): EditPetDataUseCase {
    override suspend fun invoke(
        imageUri: String?,
        name: String?,
        birthday: String?,
        petType: String?,
        gender: String?,
        description: String?,
        games: String?,
        places: String?,
        food: String?
    ){
        repository.editPetData(
            imageUri = imageUri,
            name = name,
            birthday = birthday,
            petType = petType,
            gender = gender,
            description = description,
            games = games,
            places = places,
            food = food
        )
    }
}