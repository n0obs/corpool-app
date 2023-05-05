package br.com.noobs.corpool.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.model.TripItem
import java.time.format.DateTimeFormatter

class TripAdapter(private val context: Context,
                  private val trips: MutableList<TripItem>): RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.tv_address)
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val time: TextView = itemView.findViewById(R.id.tv_time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val itemList = LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false)
        return TripViewHolder(itemList)
    }

    override fun getItemCount(): Int =  trips.size

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item = trips[position]
        holder.address.text = item.address
        holder.date.text = item.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        holder.time.text = item.date.format(DateTimeFormatter.ofPattern("HH:mm"))
    }

}