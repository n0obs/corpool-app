package br.com.noobs.corpool.model

import java.time.ZonedDateTime

data class TripItem(
    val id: Int? = null,
    val address: String,
    val date: ZonedDateTime,
    val location: Location,
    val price: Double,
)