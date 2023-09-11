package br.com.noobs.corpool.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.adapter.TripAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_home, container, false)

        val tripAdapter = TripAdapter(inflater.context, mutableListOf())


        homeViewModel.getListTrips().observe(viewLifecycleOwner) {
            tripAdapter.setData(it)
        }


        recyclerView = inflate.findViewById(R.id.rv_trips)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        recyclerView.adapter = tripAdapter

        return inflate
    }
}