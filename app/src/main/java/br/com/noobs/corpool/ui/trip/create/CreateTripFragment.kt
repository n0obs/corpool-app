package br.com.noobs.corpool.ui.trip.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.noobs.corpool.R

class CreateTripFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTripFragment()
    }

    private lateinit var viewModel: CreateTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_trip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTripViewModel::class.java)
        // TODO: Use the ViewModel
    }

}