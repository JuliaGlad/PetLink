package petlink.android.petlink.ui.profile.settings.dialog.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.petlink.domain.usecase.user_auth.SignOutUseCase
import javax.inject.Inject
import javax.inject.Provider

class LogoutViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<LogoutViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    private val _loggedOut: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loggedOut: StateFlow<Boolean> = _loggedOut.asStateFlow()

    fun logout(){
        viewModelScope.launch {
            runCatching {
                signOutUseCase.invoke()
            }.onSuccess { _loggedOut.emit(true) }
        }
    }

}