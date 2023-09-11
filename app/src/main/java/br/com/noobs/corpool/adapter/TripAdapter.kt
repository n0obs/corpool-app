package br.com.noobs.corpool.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.extensions.toZonedDateTime
import br.com.noobs.corpool.model.TripItem
import java.time.format.DateTimeFormatter
import java.util.Locale

class TripAdapter(
    private val context: Context,
    private val trips: MutableList<TripItem>,
    private val clickListener: ((TripItem) -> Unit)? = null
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val address: TextView = itemView.findViewById(R.id.tv_address)
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val price: TextView = itemView.findViewById(R.id.tv_trip_price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val itemList = LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false)
        val vh = TripViewHolder(itemList)

        vh.itemView.setOnClickListener {
            clickListener?.invoke(trips[vh.adapterPosition])
        }
        return vh
    }

    override fun getItemCount(): Int = trips.size

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item = trips[position]
        holder.address.text = item.address
        holder.date.text = item.date?.toZonedDateTime()
            ?.format(DateTimeFormatter.ofPattern("dd MMM',' yyyy"))
        holder.time.text = item.date?.toZonedDateTime()?.format(DateTimeFormatter.ofPattern("HH:mm"))
        holder.price.text = String.format(Locale.getDefault(), "R$ %.2f", item.price)
    }

    fun setData(trips: List<TripItem>) {
        this.trips.clear()
        this.trips.addAll(trips)
        notifyDataSetChanged()
    }

}