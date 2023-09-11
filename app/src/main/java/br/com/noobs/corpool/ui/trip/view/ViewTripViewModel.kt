package br.com.noobs.corpool.ui.trip.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.noobs.corpool.model.TripItem
import br.com.noobs.corpool.provider.TripProvider
import com.google.firebase.auth.FirebaseAuth

class ViewTripViewModel : ViewModel() {

    private var listTrips: MutableLiveData<List<TripItem>> = MutableLiveData()
    private var tripProvider: TripProvider = TripProvider()
    private val user = FirebaseAuth.getInstance().currentUser!!
    fun getAvailableTrips(name: String?): MutableLiveData<List<TripItem>> {
        tripProvider.searchAvailableTrips().addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }

            val trips = mutableListOf<TripItem>()
            snapshot?.documents?.forEach { it ->
                it.toObject(TripItem::class.java)?.copy(id = it.id).takeIf {
                    it?.passengers?.contains(user.uid) == false && it.locationLike(name)
                }?.let {
                    trips.add(it)
                }
            }

            listTrips.value = trips
        }
        return listTrips
    }

    private fun TripItem.locationLike(name: String?): Boolean = name?.let {
        this.location?.name?.contains(name, true) ?: true
    } ?: true
}