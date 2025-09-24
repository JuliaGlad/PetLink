package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_ui.custom_view.calendar_event.CalendarEventTheme
import petlink.android.petlink.domain.usecase.calendar.AddCalendarEventUseCase
import petlink.android.petlink.domain.usecase.user_account.AddUserDataUseCase
import petlink.android.petlink.domain.usecase.user_auth.CreateUserUseCase
import petlink.android.petlink.ui.main.runCatchingNonCancellation
import petlink.android.petlink.ui.main.runSequentially
import petlink.android.petlink.ui.profile.create_account.fragment.model.MainAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.OwnerAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.PetAccountCreationData

class CreateAccountActor(
    private val createUserUseCase: CreateUserUseCase,
    private val addUserDataUseCase: AddUserDataUseCase,
    private val addCalendarEventUseCase: AddCalendarEventUseCase
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
                    intent.petData,
                    intent.eventTitle
                )
            }

            CreateAccountIntent.Loading -> flow { emit(CreateAccountPartialState.Loading) }
        }

    private fun createUser(
        mainData: MainAccountCreationData,
        ownerData: OwnerAccountCreationData,
        petData: PetAccountCreationData,
        eventTitle: String
    ) = flow {
        runCatching {
            createUserUseCase(mainData, ownerData, petData, eventTitle)
        }.fold(
            onSuccess = { emit(CreateAccountPartialState.UserAuthenticated) },
            onFailure = { emit(CreateAccountPartialState.Error((it))) }
        )
    }

    private suspend fun createUserUseCase(
        mainData: MainAccountCreationData,
        ownerData: OwnerAccountCreationData,
        petData: PetAccountCreationData,
        eventTitle: String
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
            },
            {
                addCalendarEventUseCase.invoke(
                    title = eventTitle,
                    date = petData.birthday,
                    theme = CalendarEventTheme.GREEN.theme.id.toString(),
                    time = EVENT_TIME,
                    dateForTimestamp = petData.birthday + " " + EVENT_TIME,
                    isNotificationOn = true
                )
            }
        ) { p0, p1, p3 -> Log.i("CreateAccountActor", "$p0 $p1 $p3") }
    }.getOrThrow()

    companion object{
        const val EVENT_TIME = "00:00"
    }

}