package petlink.android.petlink.ui.profile.profile.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.user_account.GetUserMainDataDomainUseCase
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.profile.profile.model.mapper.toProfileMainData

class ProfileActor(
    private val getUserDataUseCase: GetUserMainDataDomainUseCase
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

    private suspend fun loadUserData() =
        runCatchingNonCancellation {
            asyncAwait(
                { getUserDataUseCase.invoke() }
            ) { data ->
                data.toProfileMainData()
            }
        }.getOrThrow()
}