package br.com.noobs.corpool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.adapter.TripAdapter
import br.com.noobs.corpool.database.DatabaseManager
import br.com.noobs.corpool.model.TripItem

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = homeViewModel.findViewById<RecyclerView>(R.id.rv_trips)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)


        val tripAdapter = TripAdapter(inflater.context, getTripItems().toMutableList())

        recyclerView.adapter = tripAdapter
        return homeViewModel
    }

    private fun getTripItems(): List<TripItem> {
        val db = DatabaseManager(this.requireContext(), "trips")
        return db.getAll()
    }
}