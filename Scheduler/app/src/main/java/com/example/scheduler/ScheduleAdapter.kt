package com.example.scheduler


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import android.text.format.DateFormat.format

class ScheduleAdapter(data: OrderedRealmCollection<Schedule>) : RealmRecyclerViewAdapter<Schedule, ScheduleAdapter.ViewHolder>(data, true){

    init {
        setHasStableIds(true)
    }

    private var listener: ((Long?) -> Unit)? = null //　返り値なしの関数型

    fun setOnItemClickListener(listener: (Long?) -> Unit){
        this.listener = listener
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell){
        val date: TextView = cell.findViewById(android.R.id.text1)
        val title: TextView = cell.findViewById(android.R.id.text2)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ScheduleAdapter.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule: Schedule? = getItem(position)
        holder.date.text = android.text.format.DateFormat.format("yyyy/MM/dd HH:mm", schedule?.date)
        holder.title.text = schedule?.title
        holder.itemView.setOnClickListener{
            listener?.invoke(schedule?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?:0
    }
}