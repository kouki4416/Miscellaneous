package com.example.rxretrofitpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var itemListFragment: ItemListFragment
    lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Fragment
        itemListFragment = ItemListFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.listContainer, itemListFragment)
            addToBackStack(null)
            commit()
        }
    }
}

