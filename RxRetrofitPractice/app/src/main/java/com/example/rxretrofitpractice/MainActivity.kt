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
    private lateinit var binding:ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModelとbindingとつなげる
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up layout manager
        binding.recyclerviewUsers.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewUsers.layoutManager = linearLayoutManager

        //https://www.youtube.com/watch?v=5gFrXGbQsc8
        getMyData()
    }

    private fun getMyData() {
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
        retrofitData.subscribeOn(Schedulers.io()).subscribeBy(
            onNext = {  myAdapter = MyAdapter(baseContext, it.items)
                        myAdapter.notifyDataSetChanged()
                        binding.recyclerviewUsers.adapter = myAdapter },
            onError = { it.printStackTrace() },
            onComplete = { println("Done!") }
        )


//        retrofitData.enqueue(object : Callback<MyData?> {
//            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
//                val responseBody = response.body()!!
//
//                // responseとlayoutをつなげる
//                myAdapter = MyAdapter(baseContext, responseBody.items)
//                myAdapter.notifyDataSetChanged()
//                binding.recyclerviewUsers.adapter = myAdapter
//            }
//
//            override fun onFailure(call: Call<MyData?>, t: Throwable) {
//                Log.d("MainActivity", "onFailure "+t.message)
//            }
//        })
    }

}

