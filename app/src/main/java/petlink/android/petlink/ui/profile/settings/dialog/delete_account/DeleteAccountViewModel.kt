package petlink.android.petlink.ui.profile.settings.dialog.delete_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.petlink.domain.usecase.user_auth.DeleteAccountUseCase
import petlink.android.petlink.domain.usecase.user_auth.SignOutUseCase
import petlink.android.petlink.ui.profile.settings.dialog.logout.LogoutViewModel
import javax.inject.Inject
import javax.inject.Provider

class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<DeleteAccountViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    private val _deletedAccount: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteAccount: StateFlow<Boolean> = _deletedAccount.asStateFlow()

    fun deleteAccount(password: String){
        viewModelScope.launch {
            runCatching {
                deleteAccountUseCase.invoke(password)
            }.onSuccess { _deletedAccount.emit(true) }
        }
    }
}