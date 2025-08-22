package petlink.android.petlink.ui.profile.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import petlink.android.petlink.databinding.FragmentEditBinding
import petlink.android.petlink.databinding.FragmentFriendsBinding

class FriendFragment: Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding: FragmentFriendsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}