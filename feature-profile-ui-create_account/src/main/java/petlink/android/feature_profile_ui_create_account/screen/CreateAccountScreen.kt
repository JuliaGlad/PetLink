package petlink.android.feature_profile_ui_create_account.screen

import android.os.Parcelable
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.parcelize.Parcelize
import petlink.android.feature_profile_ui_create_account.fragment.CreateAccountFragment

@Parcelize
sealed interface CreateAccountScreenArg: Parcelable {

    @Parcelize
    data object AuthDataArg: CreateAccountScreenArg

    @Parcelize
    data object OwnerDataArg: CreateAccountScreenArg

    @Parcelize
    data object PetDataArg: CreateAccountScreenArg
}

object CreateAccountScreen{
    fun createAccountFragment(arg: CreateAccountScreenArg) = FragmentScreen {
        CreateAccountFragment.getInstance(arg)
    }
}