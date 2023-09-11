package br.com.noobs.corpool.dto

import br.com.noobs.corpool.extensions.toTimestamp
import br.com.noobs.corpool.model.Location
import br.com.noobs.corpool.model.TripItem
import com.google.firebase.Timestamp
import java.time.ZonedDateTime

data class CreateTripDto(
    val userId: String,
    val createdDateTime: Timestamp = Timestamp.now(),
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: ZonedDateTime,
    val price: Double,
) {
    fun toModel(): TripItem {
        return TripItem(
            userId = userId,
            createdDateTime = createdDateTime,
            address = address,
            date = date.toTimestamp(),
            price = price,
            location = Location(
                name = locationName,
                latitude = latitude,
                longitude = longitude
            )
        )
    }
}