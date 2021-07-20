package com.example.scheduler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scheduler.databinding.FragmentSecondBinding
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

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

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        (activity as? MainActivity)?.setFabVisible(View.VISIBLE)
        binding.list.layoutManager = LinearLayoutManager(context)
        var dateTime = Calendar.getInstance().apply {
            timeInMillis = binding.calendarView.date
        }
        findSchedule(
            dateTime.get(Calendar.YEAR),
            dateTime.get(Calendar.MONTH),
            dateTime.get(Calendar.DAY_OF_MONTH)
        )
        binding.calendarView.setOnDateChangeListener{ view , year, month, dayOfMonth ->
            findSchedule(year, month, dayOfMonth)
        }
    }

    private fun findSchedule( year: Int, month: Int, dayOfMonth: Int){
        var selectDate = Calendar.getInstance().apply {
            clear()
            set(year, month, dayOfMonth)
        }
        var schedules = realm.where<Schedule>().between("date", selectDate.time,
            selectDate.apply {
                add(Calendar.DAY_OF_MONTH, 1)
                add(Calendar.MILLISECOND, -1)
            }.time).findAll().sort("date")
        val adapter = ScheduleAdapter(schedules)
        binding.list.adapter = adapter

        adapter.setOnItemClickListener { id ->
            id?.let {
                val action = SecondFragmentDirections.actionToScheduleEditFragment(it)
                findNavController().navigate(action)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy(){
        super.onDestroy()
        realm.close()
    }
}