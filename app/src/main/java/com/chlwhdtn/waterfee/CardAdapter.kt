package com.chlwhdtn.waterfee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(val list: ArrayList<House>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val HEADER = -10000;
    val DEFAULT = -10001;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            HEADER -> CardAdapter.HeaderViewHolder(inflater.inflate(R.layout.add_card, parent, false))
            DEFAULT -> CardAdapter.ViewHolder(inflater.inflate(R.layout.floor_card, parent, false))
            else -> CardAdapter.ViewHolder(inflater.inflate(R.layout.floor_card, parent, false))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CardAdapter.HeaderViewHolder) {
            holder.itemView.setOnClickListener {
                list.add(House("${list.size+1}층", 0, 0))
                MainActivity.prefs.list = list
                notifyDataSetChanged()
            }
        } else if(holder is CardAdapter.ViewHolder) {
            holder.house_name.text = list[position-1].house_name
            holder.people_count.setText(list[position-1].people_count.toString())
            holder.house_price.text = String.format("%,d 원", list[position-1].house_price)

            holder.people_count.addTextChangedListener {
                if(holder.people_count.text.isNotEmpty()) {
                    list[position - 1].people_count = holder.people_count.text.toString().toInt()
                    refreshPrice()
                    MainActivity.prefs.list = list // 저장
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int = if(position == 0) HEADER else DEFAULT


    override fun getItemCount(): Int = list.size+1


    fun refreshPrice() {
        var total = 0 // 총 인원
        for(item in list) {
            total += item.people_count
        }
        var PricePerPeople = MainActivity.prefs.price.toFloat() / total.toFloat() // 인원당 가격
        for(item in list) {
            item.house_price = Math.round(PricePerPeople * item.people_count)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var people_count: EditText
        lateinit var house_price: TextView
        lateinit var house_name: TextView

        constructor(view: View) : super(view) {
            people_count = view.findViewById(R.id.edit_count)
            house_price = view.findViewById(R.id.house_price)
            house_name = view.findViewById(R.id.house_name)
        }
    }

    class HeaderViewHolder : RecyclerView.ViewHolder {
        constructor(view: View) : super(view)
    }

}