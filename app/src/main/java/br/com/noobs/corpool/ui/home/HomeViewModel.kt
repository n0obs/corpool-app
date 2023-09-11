package br.com.noobs.corpool.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.noobs.corpool.model.TripItem
import br.com.noobs.corpool.provider.TripProvider

class HomeViewModel : ViewModel() {

    private var listTrips: MutableLiveData<List<TripItem>> = MutableLiveData()
    private var tripProvider: TripProvider = TripProvider()

    fun getListTrips(): MutableLiveData<List<TripItem>> {
        tripProvider.searchTrips().addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            val trips = mutableListOf<TripItem>()
            snapshot?.documents?.forEach {
                val trip = it.toObject(TripItem::class.java)
                trip?.let { it1 -> trips.add(it1) }
            }

            listTrips.value = trips
        }
        return listTrips
    }
}