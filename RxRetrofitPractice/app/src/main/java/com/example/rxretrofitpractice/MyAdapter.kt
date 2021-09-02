package com.example.rxretrofitpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rxretrofitpractice.dataClass.Item
import com.example.rxretrofitpractice.dataClass.MyData

class MyAdapter(val context: Context, var userList: List<Item>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // 引っ張ってくる要素を入れる変数
        var itemId: TextView = itemView.findViewById(R.id.itemId)
        var title: TextView = itemView.findViewById(R.id.itemTitle)

    }

    fun setData(dataList: List<Item>){
        this.userList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.raw_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemId.text = userList[position].id
        holder.title.text = userList[position].volumeInfo.title.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}