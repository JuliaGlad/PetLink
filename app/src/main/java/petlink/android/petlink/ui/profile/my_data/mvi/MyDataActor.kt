package petlink.android.petlink.ui.profile.my_data.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.petlink.domain.usecase.user_account.GetUserFullDataUseCase
import petlink.android.petlink.ui.main.asyncAwait
import petlink.android.petlink.ui.profile.mapper.toFullDataUi

class MyDataActor(
    private val getUserFullDataUseCase: GetUserFullDataUseCase
): MviActor<
        MyDataPartialState,
        MyDataIntent,
        MyDataState,
        MyDataEffect>() {
    override fun resolve(
        intent: MyDataIntent,
        state: MyDataState
    ): Flow<MyDataPartialState> =
        when(intent){
            MyDataIntent.LoadUserData -> getUserData()
        }

    private fun getUserData() =
        flow {
            runCatching {
                userDataUseCase()
            }.fold(
                onSuccess = { data ->
                    emit(MyDataPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(MyDataPartialState.Error(throwable))
                }
            )
        }

    private suspend fun userDataUseCase() =
        runCatchingNonCancellation {
            asyncAwait(
                { getUserFullDataUseCase.invoke() }
            ) { data ->
                data.toFullDataUi()
            }
        }.getOrThrow()
}