package petlink.android.petlink.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import petlink.android.feature_profile_domain.usecase.user_auth.CheckIsAuthenticatedUseCase
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    class Factory @Inject constructor(
        private val viewModel: Provider<MainViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }

    fun isAuthenticated(): Boolean = auth.currentUser != null

}