package petlink.android.petlink.ui.profile.auth.dialog.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.petlink.domain.usecase.user_auth.UpdatePasswordUseCase
import javax.inject.Inject
import javax.inject.Provider

class ForgotPasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase
): ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<ForgotPasswordViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    private val _passwordUpdated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val passwordUpdated : StateFlow<Boolean> = _passwordUpdated.asStateFlow()

    fun updatePassword(email: String){
        viewModelScope.launch {
            runCatching {
                updatePasswordUseCase.invoke(email)
            }.onSuccess { _passwordUpdated.emit(true) }
        }
    }

}