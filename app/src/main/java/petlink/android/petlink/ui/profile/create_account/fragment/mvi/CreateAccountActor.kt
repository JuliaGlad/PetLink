package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.petlink.domain.usecase.user_account.AddUserDataUseCase
import petlink.android.petlink.domain.usecase.user_auth.CreateUserUseCase
import petlink.android.petlink.ui.main.runCatchingNonCancellation
import petlink.android.petlink.ui.main.runSequentially
import petlink.android.petlink.ui.profile.create_account.fragment.model.MainAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.OwnerAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.PetAccountCreationData

class CreateAccountActor(
    private val createUserUseCase: CreateUserUseCase,
    private val addUserDataUseCase: AddUserDataUseCase
) : MviActor<CreateAccountPartialState,
        CreateAccountIntent,
        CreateAccountMviState,
        CreateAccountEffect>() {
    override fun resolve(
        intent: CreateAccountIntent,
        state: CreateAccountMviState
    ): Flow<CreateAccountPartialState> =
        when (intent) {
            is CreateAccountIntent.CreateUser -> {
                createUser(
                    intent.mainData,
                    intent.ownerData,
                    intent.petData
                )
            }
            CreateAccountIntent.Loading -> flow { emit(CreateAccountPartialState.Loading) }
        }

    private fun createUser(
        mainData: MainAccountCreationData,
        ownerData: OwnerAccountCreationData,
        petData: PetAccountCreationData,
    ) = flow {
        runCatching {
            createUserUseCase(mainData, ownerData, petData)
        }.fold(
            onSuccess = { emit(CreateAccountPartialState.UserAuthenticated) },
            onFailure = { emit(CreateAccountPartialState.Error((it))) }
        )
    }

    private suspend fun createUserUseCase(
        mainData: MainAccountCreationData,
        ownerData: OwnerAccountCreationData,
        petData: PetAccountCreationData
    ) = runCatchingNonCancellation {
        runSequentially(
            { createUserUseCase.invoke(mainData.email, mainData.password) },
            {
                addUserDataUseCase.invoke(
                    petImageUri = petData.imageUri.toString(),
                    petName = petData.name,
                    petGender = petData.gender,
                    petType = petData.petType,
                    petBirthday = petData.birthday,
                    imageUri = ownerData.imageUri.toString(),
                    name = ownerData.name,
                    surname = ownerData.surname,
                    birthday = ownerData.birthday,
                    city = ownerData.city,
                    gender = ownerData.gender
                )
            }
        ) { p0, p1 -> Log.i("CreateAccountActor", "$p0 $p1") }
    }.getOrThrow()

}