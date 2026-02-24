package petlink.android.feature_community_ui_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import petlink.android.core_ui.custom_view.comment.reply.CommentReplyView
import petlink.android.feature_community_ui_main.databinding.FragmentCommunityMainBinding

class CommunityMainFragment : Fragment() {

    private var _binding: FragmentCommunityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.input.setMessage("Something Something")

        binding.comment.setLikesCount(20)
        binding.comment.setIsLiked(true)
        binding.comment.addReplies(
            CommentReplyView(requireContext()).apply {
                setLikesCount(20)
                setName("Name2 Test")
                setMessage("Something Something")
                addReplies(CommentReplyView(requireContext()).apply {
                    setLikesCount(20)
                    setName("Name2 Test Something Something")
                    setMessage("Something Something")
                })
            }
        )
        binding.comment.addReplies(
            CommentReplyView(requireContext()).apply {
                setLikesCount(20)
                setName("Name3 Test")
                setMessage("Something2 Something2")
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}