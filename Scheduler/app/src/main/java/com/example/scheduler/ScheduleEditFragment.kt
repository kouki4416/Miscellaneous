package com.example.scheduler

import android.graphics.Color
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.text.format.DateFormat.format
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.scheduler.databinding.FragmentScheduleEditBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.lang.IllegalArgumentException
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleEditFragment : Fragment() {
    private var _binding: FragmentScheduleEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var realm: Realm
    private val args: ScheduleEditFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.scheduleId != -1L){
            val schedule = realm.where<Schedule>().equalTo("id", args.scheduleId).findFirst()
            binding.dateEdit.setText(android.text.format.DateFormat.format("yyyy/MM/dd", schedule?.date))
            binding.timeEdit.setText(android.text.format.DateFormat.format("HH:mm", schedule?.date))
            binding.titleEdit.setText(schedule?.title)
            binding.detailEdit.setText(schedule?.detail)
            binding.delete.visibility = View.VISIBLE
        } else{
            binding.delete.visibility = View.INVISIBLE
        }
        (activity as? MainActivity)?.setFabVisible(View.INVISIBLE)
        binding.save.setOnClickListener {
            val dialog = ConfirmDialog("保存しますか?", "保存", {saveSchedule(it)}, "キャンセル",
                {Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT).show()})
            dialog.show(parentFragmentManager, "save_dialog")
        }
        binding.delete.setOnClickListener {
            val dialog = ConfirmDialog("削除しますか?", "削除", {deleteSchedule(it)}, "キャンセル",
                {Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT).show()})
            dialog.show(parentFragmentManager, "delete_dialog")
        }

        binding.dateButton.setOnClickListener {
            DateDialog{ date ->
                binding.dateEdit.setText(date)
            }.show(parentFragmentManager, "data_dialog")
        }

        binding.timeButton.setOnClickListener {
            TimeDialog{ time ->
                binding.timeEdit.setText(time)
            }.show(parentFragmentManager, "time_dialog")
        }
    }

    private fun saveSchedule(view: View){
        when(args.scheduleId){
            -1L -> {// new schedule
                realm.executeTransaction{ db: Realm ->
                    val maxId = db.where<Schedule>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1L
                    val schedule = db.createObject<Schedule>(nextId)
                    val date = ("${binding.dateEdit.text} " + "${binding.timeEdit.text}").toDate()
                    if (date != null) schedule.date = date
                    schedule.title = binding.titleEdit.text.toString()
                    schedule.detail = binding.detailEdit.text.toString()
                }
                Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") {findNavController().popBackStack()}
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }
            else -> {// update schedule
                realm.executeTransaction{ db: Realm ->
                    val schedule = db.where<Schedule>().equalTo("id", args.scheduleId).findFirst()
                    val date = ("${binding.dateEdit.text} " + "${binding.timeEdit.text}").toDate()
                    if (date != null) schedule?.date = date
                    schedule?.title = binding.titleEdit.text.toString()
                    schedule?.detail = binding.detailEdit.text.toString()
                }
                Snackbar.make(view, "修正しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") {findNavController().popBackStack()}
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }
        }

    }

    private fun deleteSchedule(view: View) {
        realm.executeTransaction{ db: Realm ->
            db.where<Schedule>().equalTo("id", args.scheduleId)
                ?.findFirst()
                ?.deleteFromRealm()
        }
        Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.YELLOW)
            .show()

        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date?{
        return try{
            SimpleDateFormat(pattern).parse(this)
        } catch (e: IllegalArgumentException){
            return null
        } catch (e: ParseException){
            return null
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}