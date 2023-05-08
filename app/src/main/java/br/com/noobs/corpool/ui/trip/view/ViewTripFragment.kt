package br.com.noobs.corpool.ui.trip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.adapter.TripAdapter
import br.com.noobs.corpool.adapter.TripSearchInterface
import br.com.noobs.corpool.database.DatabaseManager
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.model.TripItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.ZonedDateTime


val OPTIONS = ArrayList<TripItem>().apply {
    add(
        TripItem(
            0,
            "Campinas",
            ZonedDateTime.now(),
            Location("B Paulo, SP", 1.0, 1.0),
            400.0
        )
    )
    add(
        TripItem(
            1,
            "Barueri",
            ZonedDateTime.now(),
            Location("B Paulo, SP", 1.0, 1.0),
            500.0
        )
    )
    add(
        TripItem(
            2,
            "Osasco",
            ZonedDateTime.now(),
            Location("B Paulo, SP", 1.0, 1.0),
            500.0
        )
    )
    add(
        TripItem(
            3,
            "Santos",
            ZonedDateTime.now(),
            Location("B Paulo, SP", 1.0, 1.0),
            800.0
        )
    )
}

class ViewTripFragment : Fragment(), TripSearchInterface {


    companion object {
        fun newInstance() = ViewTripFragment()
    }


    private lateinit var viewModel: ViewTripViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_trip, container, false)


        val etSearch = view.findViewById<SearchView>(R.id.et_search_trips)
        etSearch.clearFocus()


        recyclerView = view.findViewById(R.id.rv_search_trips)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)


        recyclerView.adapter =  TripAdapter(inflater.context, OPTIONS) {
            onClickTripItem(it)
        }


        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filteredList = OPTIONS.filter {
                    it.address.contains(query.toString(), true)
                }.toMutableList()
                recyclerView.layoutManager = LinearLayoutManager(inflater.context)
                recyclerView.adapter = TripAdapter(inflater.context, filteredList) {
                    onClickTripItem(it, filteredList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = OPTIONS.filter {
                    it.address.contains(newText.toString(), true)
                }.toMutableList()
                recyclerView.layoutManager = LinearLayoutManager(inflater.context)
                recyclerView.adapter = TripAdapter(inflater.context, filteredList) {
                    onClickTripItem(it, filteredList)
                }
                return true
            }
        })

        return view
    }

    private fun onClickTripItem(
        it: TripItem,
        filteredList: MutableList<TripItem>? = null
    ) {
        val db = DatabaseManager(this.requireContext(), "trips")

        MaterialAlertDialogBuilder(this.requireContext())
            .setTitle("Deseja se cadastrar na viagem?")
            .setNeutralButton("Não") { _, _ -> }
            .setPositiveButton("Sim") { _, _ ->
                db.insertTrip(it)
                OPTIONS.remove(it)
                filteredList?.remove(it)
                recyclerView.adapter?.notifyDataSetChanged()
            }.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewTripViewModel::class.java)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

}