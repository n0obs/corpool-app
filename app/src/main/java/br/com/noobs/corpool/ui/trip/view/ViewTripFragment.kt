package br.com.noobs.corpool.ui.trip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.noobs.corpool.R
import br.com.noobs.corpool.adapter.TripAdapter
import br.com.noobs.corpool.adapter.TripSearchInterface
import br.com.noobs.corpool.model.TripItem
import br.com.noobs.corpool.provider.TripProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth


class ViewTripFragment : Fragment(), TripSearchInterface {


    private val viewModel: ViewTripViewModel by viewModels()
    private val provider = TripProvider()
    private val user = FirebaseAuth.getInstance().currentUser!!

    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_trip, container, false)


        val etSearch = view.findViewById<SearchView>(R.id.et_search_trips)
        etSearch.clearFocus()

        val tripAdapter = TripAdapter(inflater.context, mutableListOf()) {
            onClickTripItem(it)
        }

        viewModel.getAvailableTrips(null).observe(viewLifecycleOwner) {
            tripAdapter.setData(it)
        }


        recyclerView = view.findViewById(R.id.rv_search_trips)
        recyclerView.layoutManager = LinearLayoutManager(inflater.context)
        recyclerView.adapter = tripAdapter


        etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getAvailableTrips(query).observe(viewLifecycleOwner) {
                    tripAdapter.setData(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getAvailableTrips(newText).observe(viewLifecycleOwner) {
                    tripAdapter.setData(it)
                }
                return true
            }

        })

        return view

    }

    private fun onClickTripItem(
        it: TripItem,
    ) {
        MaterialAlertDialogBuilder(this.requireContext())
            .setTitle("Deseja se cadastrar na viagem?")
            .setNeutralButton("Não") { _, _ -> }
            .setPositiveButton("Sim") { _, _ ->
                provider.addPassenger(it.id!!, user.uid, {
                    Toast.makeText(
                        this.requireContext(),
                        "Você foi adicionado na viagem",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_navigation_view_trips_to_navigation_home)
                }, {
                    Toast.makeText(
                        this.requireContext(),
                        "Não foi possível adicionar você na viagem",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }.show()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

}