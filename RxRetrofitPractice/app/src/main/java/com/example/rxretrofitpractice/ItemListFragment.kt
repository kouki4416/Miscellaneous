package com.example.rxretrofitpractice

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxretrofitpractice.databinding.FragmentItemListBinding
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemListFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAdapter = MyAdapter((context)!!, listOf())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context: Context? = container?.context

        // Inflate the layout for this fragment
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        // Set up adapter
        _binding?.itemList?.setHasFixedSize(true)
        _binding?.itemList?.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun isListEmpty(): Boolean{
        return myAdapter.isEmpty()
    }

    fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            //　RetrofitにRxを使うと伝える
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(MyApi::class.java)

        // This getData is from api and async using rxjava
        //https://friegen.xyz/android-rxjava-retrofit/
        val retrofitData = retrofitBuilder.getData()
        retrofitData.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = {
                myAdapter.setData(it.items)
                myAdapter.notifyDataSetChanged()
                _binding?.itemList?.adapter = myAdapter
                Log.d("onNext", "onNext")
            },
            onError = { it.printStackTrace()
                        Log.d("onError","onError")
                      },
            onComplete = { (activity as MainActivity).updateItemListFragment()
                Log.d("Done","Done")
            }
        )
    }

}