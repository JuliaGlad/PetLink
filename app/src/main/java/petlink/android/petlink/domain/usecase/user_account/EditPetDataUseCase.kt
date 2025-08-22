package petlink.android.petlink.domain.usecase.user_account

import petlink.android.petlink.data.repository.user.user_account.UserAccountRepository
import javax.inject.Inject

class EditPetDataUseCase@Inject constructor(
    private val repository: UserAccountRepository
) {
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