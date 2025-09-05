package petlink.android.petlink.ui.profile.profile.mvi

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.petlink.domain.usecase.user_account.UpdateBackgroundUseCase
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.profile.profile.model.mapper.toProfileMainData

class ProfileActor(
    private val getUserDataUseCase: GetUserMainDataDomainUseCase,
    private val updateBackgroundUseCase: UpdateBackgroundUseCase
) : MviActor<
        ProfilePartialState,
        ProfileIntent,
        ProfileState,
        ProfileEffect>() {
    override fun resolve(
        intent: ProfileIntent,
        state: ProfileState
    ): Flow<ProfilePartialState> =
        when (intent) {
            ProfileIntent.LoadUserData -> getProfileData()
            ProfileIntent.LoadUserPosts -> TODO("Add posts UseCase")
            is ProfileIntent.UpdateBackground -> updateBackground(intent.uri)
        }

    private fun updateBackground(uri: String) =
        flow {
            runCatching {
                updateBackgroundUseCase(uri)
            }.fold(
                onSuccess = { emit(ProfilePartialState.BackgroundUpdated) },
                onFailure = { throwable ->
                    emit(ProfilePartialState.Error(throwable))
                }
            )
        }

    private fun getProfileData() = flow {
        runCatching {
            loadUserData()
        }.fold(
            onSuccess = { data ->
                emit(ProfilePartialState.DataLoaded(data))
            },
            onFailure = { throwable ->
                emit(ProfilePartialState.Error(throwable))
            }
        )
    }

    private suspend fun updateBackgroundUseCase(uri: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { updateBackgroundUseCase.invoke(uri) }
            ) { result -> Log.i("Result", result.toString()) }
        }.getOrThrow()

    private suspend fun loadUserData() =
        runCatchingNonCancellation {
            asyncAwait(
                { getUserDataUseCase.invoke() }
            ) { data ->
                data.toProfileMainData()
            }
        }.getOrThrow()
}