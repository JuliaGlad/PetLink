package petlink.android.petlink.ui.profile.settings.dialog.update_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import petlink.android.petlink.domain.usecase.user_auth.UpdateEmailUseCase

import javax.inject.Inject
import javax.inject.Provider

class UpdateEmailViewModel @Inject constructor(
    private val changeEmailUseCase: UpdateEmailUseCase
): ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<UpdateEmailViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    private val _emailUpdated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val emailUpdated : StateFlow<Boolean> = _emailUpdated.asStateFlow()

    fun updateEmail(email: String, password: String){
        viewModelScope.launch {
            runCatching {
                changeEmailUseCase.invoke(
                    newEmail = email,
                    password = password
                )
            }.onSuccess { _emailUpdated.emit(true) }
        }
    }

}