package petlink.android.feature_profile_ui_friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import petlink.android.feature_profile_ui_friends.databinding.FragmentFriendBinding

class FriendFragment: Fragment() {

    private var _binding: FragmentFriendBinding? = null
    private val binding: FragmentFriendBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}