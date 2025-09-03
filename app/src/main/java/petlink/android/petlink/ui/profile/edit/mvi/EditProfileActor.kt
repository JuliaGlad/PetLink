package petlink.android.petlink.ui.profile.edit.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.petlink.domain.usecase.user_account.EditOwnerDataUseCase
import petlink.android.petlink.domain.usecase.user_account.EditPetDataUseCase
import petlink.android.petlink.domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.main.runCatchingNonCancellation
import petlink.android.petlink.ui.profile.mapper.toFull
import petlink.android.petlink.ui.profile.mapper.toFullDataUi
import petlink.android.petlink.ui.profile.model.UserFullModel

class EditProfileActor(
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val editPetDataUseCase: EditPetDataUseCase,
    private val editOwnerDataUseCase: EditOwnerDataUseCase
) : MviActor<
        EditPartialState,
        EditProfileIntent,
        EditMviState,
        EditProfileEffect>() {
    override fun resolve(
        intent: EditProfileIntent,
        state: EditMviState
    ): Flow<EditPartialState> =
        when (intent) {
            EditProfileIntent.LoadUserData -> getUserData()
            is EditProfileIntent.UpdateOwnerData -> updateOwnerData(
                isPetUpdated = state.isPetUpdated,
                ownerBirthday = intent.ownerBirthday,
                ownerCity = intent.ownerCity,
                ownerGender = intent.ownerGender,
                ownerSurname = intent.ownerSurname,
                ownerName = intent.ownerName,
                ownerImageUri = intent.ownerImageUri
            )
            is EditProfileIntent.UpdatePetData -> updatePetData(
                isOwnerUpdated = state.isOwnerUpdated,
                petName = intent.petName,
                petType = intent.petType,
                petBirthday = intent.petBirthday,
                petFood = intent.petFood,
                petGender = intent.petGender,
                petImageUri = intent.petImageUri,
                petGames = intent.petGames,
                petPlaces = intent.petPlaces,
                petDescription = intent.petDescription
            )
        }

    private fun getUserData() = flow {
        kotlin.runCatching {
            loadUserData()
        }.fold(
            onSuccess = { value -> emit(EditPartialState.DataLoaded(value)) },
            onFailure = { emit(EditPartialState.Error(it)) }
        )
    }

    private fun updatePetData(
        isOwnerUpdated: Boolean,
        petImageUri: String?,
        petName: String?,
        petBirthday: String?,
        petType: String?,
        petGender: String?,
        petDescription: String?,
        petGames: String?,
        petPlaces: String?,
        petFood: String?
    ) = flow {
        runCatching {
            editPetData(
                petImageUri,
                petName,
                petBirthday,
                petType,
                petGender,
                petDescription,
                petGames,
                petPlaces,
                petFood
            )
        }.fold(
            onSuccess = {
                if (!isOwnerUpdated) {
                    emit(EditPartialState.PetDataUpdated)
                } else {
                    emit(EditPartialState.DataUpdated)
                }
            },
            onFailure = { throwable -> emit(EditPartialState.Error(throwable)) }
        )
    }

    private fun updateOwnerData(
        isPetUpdated: Boolean,
        ownerImageUri: String?,
        ownerName: String?,
        ownerSurname: String?,
        ownerBirthday: String?,
        ownerGender: String?,
        ownerCity: String?
    ) = flow {
        runCatching {
            editOwnerData(
                ownerImageUri,
                ownerName,
                ownerSurname,
                ownerBirthday,
                ownerGender,
                ownerCity
            )
        }.fold(
            onSuccess = {
                if (!isPetUpdated) {
                    emit(EditPartialState.OwnerDataUpdated)
                } else {
                    emit(EditPartialState.DataUpdated)
                }
            },
            onFailure = { throwable ->
                emit(EditPartialState.Error(throwable))
            }
        )
    }

    private suspend fun editOwnerData(
        ownerImageUri: String?,
        ownerName: String?,
        ownerSurname: String?,
        ownerBirthday: String?,
        ownerGender: String?,
        ownerCity: String?
    ) = runCatchingNonCancellation {
        asyncAwait(
            {
                editOwnerDataUseCase.invoke(
                    imageUri = ownerImageUri,
                    name = ownerName,
                    surname = ownerSurname,
                    birthday = ownerBirthday,
                    gender = ownerGender,
                    city = ownerCity
                )
            }
        ) { data ->
            Log.i("OwnerDataUpdated", data.toString())
        }
    }.getOrThrow()

    private suspend fun editPetData(
        petImageUri: String?,
        petName: String?,
        petBirthday: String?,
        petType: String?,
        petGender: String?,
        petDescription: String?,
        petGames: String?,
        petPlaces: String?,
        petFood: String?
    ) =
        runCatchingNonCancellation {
            asyncAwait({
                editPetDataUseCase.invoke(
                    imageUri = petImageUri,
                    name = petName,
                    birthday = petBirthday,
                    petType = petType,
                    gender = petGender,
                    description = petDescription,
                    games = petGames,
                    places = petPlaces,
                    food = petFood
                )
            }) { data ->
                Log.i("EditPetDataResult", data.toString())
            }
        }.getOrThrow()

    private suspend fun loadUserData() =
        runCatchingNonCancellation {
            asyncAwait(
                { getUserFullDataUseCase.invoke() }
            ) { data ->
                data.toFullDataUi()
            }
        }.getOrThrow()

}