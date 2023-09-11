package br.com.noobs.corpool.model

import com.google.firebase.Timestamp

data class TripItem(
    val id: String? = null,
    val address: String? = null,
    val date: Timestamp? = null,
    val location: Location? = null,
    val price: Double? = null,
    val createdDateTime: Timestamp? = null,
    val userId: String? = null,
    val passengers: List<String> = emptyList()
)