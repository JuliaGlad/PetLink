package petlink.android.petlink.ui.profile.create_account.fragment.mvi

import petlink.android.core_mvi.MviIntent
import petlink.android.petlink.ui.profile.create_account.fragment.model.MainAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.OwnerAccountCreationData
import petlink.android.petlink.ui.profile.create_account.fragment.model.PetAccountCreationData

sealed interface CreateAccountIntent: MviIntent {
    class CreateUser(
        val mainData: MainAccountCreationData,
        val ownerData: OwnerAccountCreationData,
        val petData: PetAccountCreationData
    ): CreateAccountIntent
    data object Loading: CreateAccountIntent
}