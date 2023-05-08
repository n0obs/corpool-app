package br.com.noobs.corpool.ui.trip.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewTripViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is view trip Fragment"
    }
    val text: LiveData<String> = _text
}