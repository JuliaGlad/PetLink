package petlink.android.feature_profile_ui_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import petlink.android.feature_profile_ui_main.auth_fragment.AuthFragment
import petlink.android.feature_profile_ui_main.main_fragment.main.ProfileFragment

class  ProfileMainFragment : Fragment(), OnFragmentInteractionListener {

    private var profileId: MainProfileId = MainProfileId.Auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        replaceFragment(profileId)
    }

    override fun onRequestFragmentChange(id: MainProfileId) { replaceFragment(id) }

    private fun replaceFragment(id: MainProfileId){
        val fragment = when (id) {
            MainProfileId.Auth -> AuthFragment()
            MainProfileId.ProfileMain -> ProfileFragment()
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.container_view, fragment, id.hashCode().toString())
            .addToBackStack(null)
            .commit()
    }

    companion object{
        fun newInstance(id: MainProfileId) = ProfileMainFragment().apply { profileId = id }
    }

}