package com.example.touristsights

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristsights.databinding.FragmentListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.apply {
            binding.list.layoutManager =
                when {
                    resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT ->
                        LinearLayoutManager(context)
                    else -> GridLayoutManager(context, 2)
                }
            binding.list.adapter = SightAdapter(context, getSights(resources)).apply {
                setOnItemClickListener { position: Int ->
                    fragmentManager?.let { manager: FragmentManager ->
                        val tag = "DetailFragment"
                        var fragment = manager.findFragmentByTag(tag)
                        if(fragment == null) {
                            fragment = DetailFragment()
                            fragment.arguments = Bundle().apply {
                                putInt("ROW_POSITION", position)
                            }
                            manager.beginTransaction().apply {
                                setCustomAnimations(
                                    android.R.anim.slide_in_left,
                                    android.R.anim.slide_out_right,
                                    android.R.anim.fade_in,
                                    android.R.anim.fade_out
                                )
                                replace(R.id.content, fragment, tag)
                                addToBackStack(null)
                            }.commit()

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}