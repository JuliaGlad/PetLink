package petlink.android.petlink.ui.cicerone.screen.create_account

import android.util.Log
import com.github.terrakok.cicerone.androidx.FragmentScreen
import petlink.android.petlink.ui.profile.create_account.fragment.CreateAccountFragment
import petlink.android.petlink.ui.profile.create_account.screen.CreateAccountScreenArg

object CreateAccountScreen {
    fun createAccount(arg: CreateAccountScreenArg) = FragmentScreen { CreateAccountFragment.getInstance(arg) }
}