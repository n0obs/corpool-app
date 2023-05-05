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
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.model.TripItem
import java.time.LocalDateTime
import java.time.ZonedDateTime

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView = homeViewModel.findViewById<RecyclerView>(R.id.rv_trips)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)


        val tripList = ArrayList<TripItem>()
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))
        tripList.add(TripItem(1, "Rua da Consolação, 930", ZonedDateTime.now(), Location(1.0, 1.0)))

        val tripAdapter = TripAdapter(inflater.context, tripList)

        recyclerView.adapter = tripAdapter





        return homeViewModel


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}