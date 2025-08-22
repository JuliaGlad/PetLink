package petlink.android.petlink.ui.main.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petlink.android.petlink.domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val isAuthenticatedUseCase: CheckIsAuthenticatedUseCase
) : ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<MainViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    fun isAuthenticated(): Boolean = isAuthenticatedUseCase.invoke()

}