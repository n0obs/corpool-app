package br.com.noobs.corpool.ui.trip.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.noobs.corpool.R

class ViewTripFragment : Fragment() {

    companion object {
        fun newInstance() = ViewTripFragment()
    }

    private lateinit var viewModel: ViewTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_trip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewTripViewModel::class.java)
        // TODO: Use the ViewModel
    }

}