package petlink.android.petlink.ui.profile.create_account.screen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface CreateAccountScreenArg: Parcelable {

    @Parcelize
    data object AuthDataArg: CreateAccountScreenArg

    @Parcelize
    data object OwnerDataArg: CreateAccountScreenArg

    @Parcelize
    data object PetDataArg: CreateAccountScreenArg
}