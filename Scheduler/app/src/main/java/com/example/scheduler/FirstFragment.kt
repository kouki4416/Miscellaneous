package com.example.scheduler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduler.databinding.FragmentFirstBinding
import io.realm.Realm
import io.realm.kotlin.where

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.list.layoutManager = LinearLayoutManager(context)
        val schedules = realm.where<Schedule>().findAll()
        val adapter = ScheduleAdapter(schedules)
        binding.list.adapter = adapter

        adapter.setOnItemClickListener { id ->
            id?.let{
                val action =
                    FirstFragmentDirections.actionToScheduleEditFragment(it)
                findNavController().navigate(action)
            }
        }
        //(activity as? MainActivity)?.setFabVisible(View.VISIBLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}