package br.com.noobs.corpool.ui.trip.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateTripViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is create trip Fragment"
    }
    val text: LiveData<String> = _text
}