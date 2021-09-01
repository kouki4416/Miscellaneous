package com.example.rxretrofitpractice

import com.example.rxretrofitpractice.dataClass.MyData
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
//    @GET("posts")
//    fun getData(): Call<List<TestDataItem>>

    @GET("books/v1/volumes?q=ドラゴンボール&startIndex=0&maxResults=20")
    fun getData(): Observable<MyData>

}