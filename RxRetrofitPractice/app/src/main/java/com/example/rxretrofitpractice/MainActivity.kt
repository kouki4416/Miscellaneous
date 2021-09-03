package com.example.rxretrofitpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxretrofitpractice.databinding.ActivityMainBinding
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//const val BASE_URL = "https://jsonplaceholder.typicode.com/"

//https://www.googleapis.com/books/v1/volumes?q=ドラゴンボール&startIndex=0&maxResults=20
const val BASE_URL = "https://www.googleapis.com/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val itemListFragment: ItemListFragment = ItemListFragment()
    lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getDataButton.setOnClickListener {
            showConfirmDialog()
            //createItemListFragment(R.id.listContainer)
        }
    }

    // データリストを表示するフラグメントを生成し表示
    public fun createItemListFragment(containerId: Int){
        itemListFragment.getMyData()
        supportFragmentManager.beginTransaction().apply {
            replace(containerId, itemListFragment)
            addToBackStack(null)
            commit()
        }
    }

    public fun updateItemListFragment(){
        supportFragmentManager.beginTransaction().apply {
            detach(itemListFragment)
            attach(itemListFragment)
            commit()
        }
    }

    // ダイアログを作成
    private fun showConfirmDialog(){
        val dialogFragment: ConfirmDialogFragment = ConfirmDialogFragment()
        dialogFragment.show(supportFragmentManager, "ConfirmDialogFragment")
    }
}

