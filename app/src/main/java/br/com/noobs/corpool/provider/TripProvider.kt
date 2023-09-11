package br.com.noobs.corpool.provider

import br.com.noobs.corpool.dto.CreateTripDto
import br.com.noobs.corpool.model.Entity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TripProvider(
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!,
) {


    fun saveTrip(
        createOperationDto: CreateTripDto,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        fireStore.collection(Entity.TRIP.name)
            .add(createOperationDto.toModel())
            .addOnSuccessListener {
                successListener()
            }
            .addOnFailureListener {
                failureListener()
            }
    }

    fun addPassenger(
        tripId: String,
        userId: String,
        successListener: () -> Unit,
        failureListener: () -> Unit
    ) {
        fireStore.collection(Entity.TRIP.name)
            .document(tripId)
            .update("passengers", FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                successListener()
            }
            .addOnFailureListener {
                failureListener()
            }
    }

    fun searchTrips(): Query {
        return fireStore.collection(Entity.TRIP.name)
            .where(
                Filter.or(
                    Filter.arrayContains("passengers", user.uid),
                    Filter.equalTo("userId", user.uid)
                )
            )
    }

    fun searchAvailableTrips(): Query = fireStore.collection(Entity.TRIP.name)
        .whereNotEqualTo("userId", user.uid)


}