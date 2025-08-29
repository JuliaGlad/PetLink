package petlink.android.petlink.ui.profile.dialog.update_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import petlink.android.petlink.domain.usecase.user_auth.UpdatePasswordUseCase
import javax.inject.Inject
import javax.inject.Provider

class UpdatePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase
): ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<UpdatePasswordViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    fun updatePassword(){
        viewModelScope.launch {
            runCatching {
                updatePasswordUseCase.invoke()
            }
        }
    }

}