package petlink.android.petlink.ui.profile.my_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import petlink.android.petlink.databinding.FragmentEditBinding
import petlink.android.petlink.databinding.FragmentMyDataBinding

class MyDataFragment: Fragment() {

    private var _binding: FragmentMyDataBinding? = null
    private val binding: FragmentMyDataBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}