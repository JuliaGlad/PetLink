package petlink.android.petlink.ui.profile.auth.mvi

sealed class AuthError(message: String?): RuntimeException() {

    class NetworkError(message: String? = NETWORK_ERROR): AuthError(message)

    class WrongDataError(message: String? = WRONG_DATA_ERROR): AuthError(message)

    class UnknownError(message: String? = UNKNOWN_ERROR): AuthError(message)

    companion object{
        const val UNKNOWN_ERROR = "Something went wrong, try again later"
        const val NETWORK_ERROR = "No internet connection"
        const val WRONG_DATA_ERROR = "Wrong auth data(email or password)"
    }

}