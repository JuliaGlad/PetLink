package petlink.android.feature_profile_ui_auth.mvi

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import petlink.android.feature_profile_domain.usecase.user_auth.SignInUseCase
import java.io.IOException

class AuthActor(
    private val signInUseCase: SignInUseCase,
    private val checkAuthUseCase: CheckIsAuthenticatedUseCase
) : MviActor<
        AuthPartialState,
        AuthIntent,
        AuthMviState,
        AuthEffect>() {
    override fun resolve(
        intent: AuthIntent,
        state: AuthMviState
    ): Flow<AuthPartialState> =
        when (intent) {
            is AuthIntent.SignIn -> signIn(
                email = intent.email,
                password = intent.password
            )

            AuthIntent.Loading -> updateLoading()
            AuthIntent.CheckAuth -> checkAuth()
        }

    private fun checkAuth() = flow<AuthPartialState> {
        runCatching {
            checkAuthUseCase.invoke()
        }.fold(
            onSuccess ={ value ->
                if (value){
                    emit(AuthPartialState.Authenticated)
                }
            },
            onFailure = { throwable -> emit(checkAuthError(throwable)) }
        )
    }

    private fun updateLoading() = flow<AuthPartialState> { emit(AuthPartialState.Loading) }

    private fun signIn(
        email: String,
        password: String
    ) = flow<AuthPartialState> {
        kotlin.runCatching {
            signInUseCase(email, password)
        }.fold(
            onSuccess = {
                emit(AuthPartialState.SignedIn)
            },
            onFailure = { throwable ->
                emit(checkAuthError(throwable))
            }
        )
    }

    private suspend fun signInUseCase(email: String, password: String) {
        runCatchingNonCancellation {
            signInUseCase.invoke(email = email, password = password)
        }.getOrThrow()
    }

    private fun checkAuthError(throwable: Throwable): AuthPartialState.Error =
        if (isNetworkError(throwable)) AuthPartialState.Error(AuthError.NetworkError(throwable.message))
        else if (throwable is FirebaseAuthException) AuthPartialState.Error(
            AuthError.WrongDataError(
                throwable.message
            )
        )
        else AuthPartialState.Error(AuthError.UnknownError(throwable.message))

    private fun isNetworkError(e: Throwable?): Boolean {
        return when (e) {
            is FirebaseNetworkException -> true
            is IOException -> true
            else -> e?.cause?.let { isNetworkError(it) } == true
        }
    }
}